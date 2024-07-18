import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { MatSnackBar } from '@angular/material/snack-bar';
import { ActivatedRoute } from '@angular/router';
import { Article } from 'src/app/features/articles/interfaces/article.interface';
import { CommentRequest } from '../../interfaces/api/commentRequest.interface';
import { MessageResponse } from '../../interfaces/api/messageResponse.interface';
import { MessagesService } from '../../services/messages.service';
import { ArticlesService } from '../../services/articles.service';
import { SessionService } from 'src/app/services/session.service';

@Component({
  selector: 'app-detail',
  templateUrl: './detail.component.html',
  styleUrls: ['./detail.component.scss']
})
export class DetailComponent implements OnInit {

  public messageForm!: FormGroup;
  public article: Article | undefined;

  constructor(
    private route: ActivatedRoute,
    private fb: FormBuilder,
    private messagesService: MessagesService,
    private articlesService: ArticlesService,
    private sessionService: SessionService,
    private matSnackBar: MatSnackBar) {
    this.initMessageForm();
  }

  public ngOnInit(): void {
    const id = this.route.snapshot.paramMap.get('id')!

    this.articlesService
      .detail(id)
      .subscribe((article: Article) => this.article = article);
  }

  public back() {
    window.history.back();
  }

  public sendMessage(): void {
    const message = {
      article_id: this.article!.id,
      author_id: this.sessionService.user?.id,//
      content: this.messageForm.value.message
    } as CommentRequest;

    this.messagesService.send(message).subscribe(
      (messageResponse: MessageResponse) => {
        this.initMessageForm();
        this.matSnackBar.open(messageResponse.message, "Close", { duration: 3000 });
      });
  }

  private initMessageForm() {
    this.messageForm = this.fb.group({
      message: ['', [Validators.required, Validators.min(10)]],
    });
  }

}
