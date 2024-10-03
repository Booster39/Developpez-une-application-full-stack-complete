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
import { TopicsService } from 'src/app/features/topics/services/topics.service';
import { User } from 'src/app/interfaces/user.interface';
import { UserService } from 'src/app/services/user.service';
import { Comment } from '../../interfaces/comment.interface';

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

  public comments$ = this.commentsService.all();

  constructor(
    private route: ActivatedRoute,
    private fb: FormBuilder,
    private commentsService: CommentsService,
    private articlesService: ArticlesService,
    private sessionService: SessionService,
    private topicsService: TopicsService,
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
        })
        
      });
  }

  public back() {
    window.history.back();
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
