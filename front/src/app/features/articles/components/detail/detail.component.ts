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
import { Theme } from 'src/app/features/themes/interfaces/theme.interface';
import { ThemesService } from 'src/app/features/themes/services/themes.service';
import { User } from 'src/app/interfaces/user.interface';
import { UserService } from 'src/app/services/user.service';

@Component({
  selector: 'app-detail',
  templateUrl: './detail.component.html',
  styleUrls: ['./detail.component.scss']
})
export class DetailComponent implements OnInit {

  public messageForm!: FormGroup;
  post: any = {};
  comments: { commentAuthorUsername: string; content: string }[] = [];
  newCommentText: string = '';
  public article: Article | undefined;
  public theme: Theme | undefined;

  constructor(
    private route: ActivatedRoute,
    private fb: FormBuilder,
    private commentsService: CommentsService,
    private articlesService: ArticlesService,
    private sessionService: SessionService,
    private themesService: ThemesService,
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
      });

      this.themesService.detail(id).subscribe(theme => {
        this.theme = theme;
      })
  }

  public back() {
    window.history.back();
  }

  public sendMessage(): void {
    const message = {
      article_id: this.article!.id,
      author_id: this.article!.author_id,
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


  createComment(): void {
    if (this.newCommentText.trim() !== '') {
      const postId: number = this.post.id; // Get the ID of the current post
      const content: string = this.newCommentText; // Get the content of the new comment

      // Call the createComment method from CommentsService
      this.commentsService.send({article_id: postId, author_id: this.user!.id, content: content, created_at: new Date()}).subscribe(
        (newComment: any) => {
          // Get authenticated user data from AuthSessionService
          const authSession = this.sessionService.user;

          // Ensure authSession is not null before accessing properties
          if (authSession) {
            // Update the comments array with the newly created comment and author information
            this.comments.push({
              commentAuthorUsername: authSession.name,
              content: newComment.content
            });
          } else {
            console.error('Error: Authenticated user data is null.');
          }

          // Clear input field
          this.newCommentText = '';
        },
        (error: any) => {
          console.error('Error creating comment:', error);
        }
      );
    }
  }

}
