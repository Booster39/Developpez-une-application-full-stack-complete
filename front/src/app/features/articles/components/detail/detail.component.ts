import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { MatSnackBar } from '@angular/material/snack-bar';
import { ActivatedRoute } from '@angular/router';
import { Article } from 'src/app/features/articles/interfaces/article.interface';
import { CommentRequest } from '../../interfaces/api/commentRequest.interface';
import { CommentResponse } from '../../interfaces/api/commentResponse.interface';
import { CommentsService } from '../../services/comments.service';
import { ArticlesService } from '../../services/articles.service';
import { SessionService } from 'src/app/services/session.service';
import { Topic } from 'src/app/features/topics/interfaces/topic.interface';
import { User } from 'src/app/interfaces/user.interface';
import { UserService } from 'src/app/services/user.service';
import { Comment } from '../../interfaces/comment.interface';
import { forkJoin, interval, Subscription, switchMap } from 'rxjs';
import { TopicService } from 'src/app/services/topics.service';

@Component({
  selector: 'app-detail',
  templateUrl: './detail.component.html',
  styleUrls: ['./detail.component.scss']
})
export class DetailComponent implements OnInit {

  /**
   * Form group for managing the comment message input.
   */
  public messageForm!: FormGroup;

  /**
   * Current article being displayed.
   */
  public article: Article | undefined;

  /**
   * Name of the article's author.
   */
  public authorName: string | undefined;

  /**
   * Name of the topic associated with the article.
   */
  public topicName: string | undefined;

  /**
   * Subscription to handle polling for new comments.
   */
  private pollingSubscription!: Subscription;

  /**
   * Tracks the visibility state of the side menu.
   */
  public isSideMenuOpen: boolean = false;

  /**
   * List of comments and corresponding author names for the article.
   */
  public comments: Array<{ comment: Comment, authorName: string }> = [];

  /**
   * Observable for all comments, used for initial comment load and polling.
   */
  public comments$ = this.commentsService.all();

  /**
   * Initializes services and form builder, sets up route handling.
   * @param route - ActivatedRoute instance for accessing route parameters.
   * @param fb - FormBuilder instance for creating the comment form.
   * @param commentsService - Service for managing comments.
   * @param articlesService - Service for retrieving article data.
   * @param sessionService - Manages user session data and authentication.
   * @param topicsService - Service for retrieving topics.
   * @param userService - Service for managing user data.
   * @param matSnackBar - Material Snackbar for displaying messages.
   */
  constructor(
    private route: ActivatedRoute,
    private fb: FormBuilder,
    private commentsService: CommentsService,
    private articlesService: ArticlesService,
    private sessionService: SessionService,
    private topicsService: TopicService,
    private userService: UserService,
    private matSnackBar: MatSnackBar
  ) {
    this.initMessageForm();
  }

  /**
   * Lifecycle hook to initialize the component.
   * Loads the article, retrieves author and topic names, and initializes comment polling.
   */
  public ngOnInit(): void {
    const id = this.route.snapshot.paramMap.get('id')!;

    this.articlesService
      .detail(id)
      .subscribe((article: Article) => {
        this.article = article;
        this.userService.getUserById(article.author_id).subscribe((user: User) => {
          this.authorName = user.name;
        });

        this.topicsService.detail(article.topic_id.toString()).subscribe(topic => {
          this.topicName = topic.name;
        });
        this.loadCommentsWithAuthors();
      });

    // Start polling for comments every 5 seconds
    this.pollingSubscription = interval(5000)
      .pipe(
        switchMap(() => this.commentsService.all())
      )
      .subscribe(commentsResponse => {
        if (this.article) {
          const commentList = commentsResponse.comments.filter(comment => comment.article_id === this.article!.id);
          // Check for new comments
          if (commentList.length !== this.comments.length) {
            this.fetchAuthorsForComments(commentList);
          }
        }
      });
  }

  /**
   * Retrieves author names for a list of comments and stores them in the comments array.
   * @param commentList - List of comments to retrieve authors for.
   */
  private fetchAuthorsForComments(commentList: Comment[]): void {
    const userObservables = commentList.map(comment => 
      this.userService.getUserById(comment.author_id)
    );

    forkJoin(userObservables).subscribe(users => {
      this.comments = commentList.map((comment, index) => ({
        comment,
        authorName: users[index].name
      }));
    });
  }

  /**
   * Loads comments for the article and retrieves author names for each comment.
   */
  private loadCommentsWithAuthors(): void {
    this.commentsService.all().subscribe(commentsResponse => {
      const commentList = commentsResponse.comments.filter(comment => comment.article_id === this.article?.id);
      
      const userObservables = commentList.map(comment => 
        this.userService.getUserById(comment.author_id)
      );

      forkJoin(userObservables).subscribe(users => {
        this.comments = commentList.map((comment, index) => ({
          comment,
          authorName: users[index].name
        }));
      });
    });
  }

  /**
   * Unsubscribes from the polling subscription when the component is destroyed.
   */
  public ngOnDestroy(): void {
    if (this.pollingSubscription) {
      this.pollingSubscription.unsubscribe();
    }
  }

  /**
   * Navigates back to the previous page.
   */
  public back(): void {
    window.history.back();
  }

  /**
   * Toggles the visibility of the side menu.
   */
  toggleSideMenu(): void {
    this.isSideMenuOpen = !this.isSideMenuOpen;
  }

  /**
   * Sends a comment message, then reinitializes the form and shows a success message.
   */
  public sendMessage(): void {
    const message = {
      article_id: this.article!.id,
      author_id: this.user!.id,
      content: this.messageForm.value.message
    } as CommentRequest;

    this.commentsService.send(message).subscribe(
      (messageResponse: CommentResponse) => {
        this.initMessageForm();
        this.matSnackBar.open(messageResponse.message, "Close", { duration: 3000 });
      });
  }

  /**
   * Initializes the comment message form with validation rules.
   */
  private initMessageForm(): void {
    this.messageForm = this.fb.group({
      message: ['', [Validators.required, Validators.min(10)]],
    });
  }

  /**
   * Retrieves the current user from the session service.
   */
  get user(): User | undefined {
    return this.sessionService.user;
  }

}
