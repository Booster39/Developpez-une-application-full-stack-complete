/*import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { MatSnackBar } from '@angular/material/snack-bar';
import { ActivatedRoute } from '@angular/router';
import { Theme } from 'src/app/features/themes/interfaces/theme.interface';
import { SessionService } from 'src/app/services/session.service';
import { MessageRequest } from '../../interfaces/api/messageRequest.interface';
import { MessageResponse } from '../../interfaces/api/messageResponse.interface';
import { MessagesService } from '../../services/messages.service';
import { ThemesService } from '../../services/themes.service';

@Component({
  selector: 'app-detail',
  templateUrl: './detail.component.html',
  styleUrls: ['./detail.component.scss']
})
export class DetailComponent implements OnInit {

  public messageForm!: FormGroup;
  public theme: Theme | undefined;

  constructor(
    private route: ActivatedRoute,
    private fb: FormBuilder,
    private messagesService: MessagesService,
    private themesService: ThemesService,
    private sessionService: SessionService,
    private matSnackBar: MatSnackBar) {
    this.initMessageForm();
  }

  public ngOnInit(): void {
    const id = this.route.snapshot.paramMap.get('id')!

    this.themesService
      .detail(id)
      .subscribe((theme: Theme) => this.theme = theme);
  }

  public back() {
    window.history.back();
  }

  public sendMessage(): void {
    /*const message = {
      theme_id: this.theme!.id,
      user_id: this.sessionService.user?.id,
      //message: this.messageForm.value.message
    } as MessageRequest;

    this.messagesService.send(message).subscribe(
      (messageResponse: MessageResponse) => {
        this.initMessageForm();
        this.matSnackBar.open(messageResponse.messages, "Close", { duration: 3000 });
      });
  }

  private initMessageForm() {
    this.messageForm = this.fb.group({
      message: ['', [Validators.required, Validators.min(10)]],
    });
  }

}
*/