import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { MatSnackBar } from '@angular/material/snack-bar';
import { ActivatedRoute, Router } from '@angular/router';
import { SessionService } from 'src/app/services/session.service';
import { TopicResponse } from '../../interfaces/api/topicResponse.interface';
import { Topic } from '../../interfaces/topic.interface';
import { TopicsService } from '../../services/topics.service';
import { TopicService } from 'src/app/services/topics.service';

@Component({
  selector: 'app-form',
  templateUrl: './form.component.html',
  styleUrls: ['./form.component.scss'],
})
export class FormComponent implements OnInit {
  public onUpdate: boolean = false;
  public topicForm: FormGroup | undefined;
  public topics$ = this.topicService.all();

  private id: string | undefined;

  constructor(
    private route: ActivatedRoute,
    private fb: FormBuilder,
    private matSnackBar: MatSnackBar,
    private topicsService: TopicsService,
    private topicService: TopicService,
    private router: Router,
    private sessionService: SessionService
  ) {}

  public ngOnInit(): void {
    const url = this.router.url;
    if (url.includes('update')) {
      this.onUpdate = true;
      this.id = this.route.snapshot.paramMap.get('id')!;
    }
  }

  public submit(): void {
    const topic = this.topicForm?.value as FormData;
    /* this.topicsService
    .create(topic)
    .subscribe((_: Topic) => this.exitPage('Topic created !'));*/
    const formData = new FormData();
    formData.append('title', this.topicForm!.get('title')?.value);
    formData.append('content', this.topicForm!.get('content')?.value);
    formData.append('topic_id', this.topicForm!.get('topic_id')?.value);
    if (!this.onUpdate) {
      this.topicsService
        .create(topic)
        .subscribe((topicResponse: TopicResponse) =>
          this.exitPage(topicResponse)
        );
    }
  }


  private exitPage(topicResponse: TopicResponse): void {
    this.matSnackBar.open(topicResponse.message, 'Close', { duration: 3000 });
    this.router.navigate(['/topics']);
  }
}