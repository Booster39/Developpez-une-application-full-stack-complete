import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { MatSnackBar } from '@angular/material/snack-bar';
import { ActivatedRoute } from '@angular/router';
import { Theme } from 'src/app/features/themes/interfaces/theme.interface';
import { CommentRequest } from '../../interfaces/api/commentRequest.interface';
import { MessageResponse } from '../../interfaces/api/messageResponse.interface';
import { MessagesService } from '../../services/messages.service';
import { ThemesService } from '../../services/themes.service';
import { SessionService } from 'src/app/services/session.service';
import { User } from 'src/app/interfaces/user.interface';
import { UserService } from 'src/app/services/user.service';

@Component({
  selector: 'app-detail',
  templateUrl: './detail.component.html',
  styleUrls: ['./detail.component.scss']
})
export class DetailComponent implements OnInit {

  public messageForm!: FormGroup;
  public theme: Theme | undefined;
  public user: User | undefined;

  constructor(
    private route: ActivatedRoute,
    private fb: FormBuilder,
    private messagesService: MessagesService,
    private sessionService: SessionService,
    private themesService: ThemesService,
    private userService: UserService,
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

  private initMessageForm() {
    this.messageForm = this.fb.group({
      message: ['', [Validators.required, Validators.min(10)]],
    });
  }

}
