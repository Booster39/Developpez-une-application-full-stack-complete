import { Component } from '@angular/core';
import { User } from 'src/app/interfaces/user.interface';
import { TopicsService } from '../../services/topics.service';
import { SessionService } from 'src/app/services/session.service';

@Component({
  selector: 'app-list',
  templateUrl: './list.component.html',
  styleUrls: ['./list.component.scss']
})
export class ListComponent {

  public topics$ = this.topicsService.all();

  constructor(
    private sessionService: SessionService,
    private topicsService: TopicsService
  ) { }

  get user(): User | undefined {
    return this.sessionService.user;
  }
}
