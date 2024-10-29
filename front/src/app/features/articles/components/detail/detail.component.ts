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

  public messageForm!: FormGroup;
  public article: Article | undefined;
  public authorName: string | undefined;
  public topicName: string | undefined;
  private pollingSubscription!: Subscription;
  public isSideMenuOpen: boolean = false;
  public comments: Array<{comment: Comment, authorName: string}> = [];

  public comments$ = this.commentsService.all();

  constructor(
    private route: ActivatedRoute,
    private fb: FormBuilder,
    private commentsService: CommentsService,
    private articlesService: ArticlesService,
    private sessionService: SessionService,
    private topicsService: TopicService,
    private userService: UserService,
    private matSnackBar: MatSnackBar) {
    this.initMessageForm();
  }

  public ngOnInit(): void {
    const id = this.route.snapshot.paramMap.get('id')!

    this.articlesService
      .detail(id)
      .subscribe((article: Article) => {
        this.article = article
        this.userService.getUserById(article.author_id).subscribe((user: User) => {
          this.authorName = user.name;
        });

        this.topicsService.detail(article.topic_id.toString()).subscribe(topic => {
          this.topicName = topic.name;
        });
        this.loadCommentsWithAuthors();
      });
    // Initialiser le polling après avoir chargé l'article
    this.pollingSubscription = interval(5000) // Toutes les 5 secondes
      .pipe(
        switchMap(() => this.commentsService.all())
      )
      .subscribe(commentsResponse => {
        if (this.article) {
          const commentList = commentsResponse.comments.filter(comment => comment.article_id === this.article!.id);
          // Vérifier si de nouveaux commentaires existent
          if (commentList.length !== this.comments.length) {
            this.fetchAuthorsForComments(commentList);
          }
        }
      });
  }

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

  private loadCommentsWithAuthors(): void {
    this.commentsService.all().subscribe(commentsResponse => {
      const commentList = commentsResponse.comments.filter(comment => comment.article_id === this.article?.id);
      
      // Fetch all authors at once using forkJoin
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

  public ngOnDestroy(): void {
    if (this.pollingSubscription) {
      this.pollingSubscription.unsubscribe();
    }
  }

  public back() {
    window.history.back();
  }
  toggleSideMenu() {
    this.isSideMenuOpen = !this.isSideMenuOpen;
  }
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


  private initMessageForm() {
    this.messageForm = this.fb.group({
      message: ['', [Validators.required, Validators.min(10)]],
    });
  }
  get user(): User | undefined {
    return this.sessionService.user;
  }

}
