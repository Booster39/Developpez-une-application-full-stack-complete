import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { User } from 'src/app/interfaces/user.interface';
import { SessionService } from 'src/app/services/session.service';
import { Topic } from 'src/app/interfaces/topic.interface';
import { TopicService } from 'src/app/services/topics.service';

@Component({
  selector: 'app-list',
  templateUrl: './list.component.html',
  styleUrls: ['./list.component.scss']
})
export class ListComponent implements OnInit {
  @Input() subject!: Topic;
  @Output() subscribeEvent = new EventEmitter<void>();
  public topics$ = this.topicsService.all();
  public followedTopicIds: number[] = [];
  constructor(
    private sessionService: SessionService,
    private topicsService: TopicService,
  ) { }

  ngOnInit(): void {
    this.loadFollowedTopics();
  }

  get user(): User | undefined {
    return this.sessionService.user;
  }

  private loadFollowedTopics(): void {
    if (this.user) {
      this.followedTopicIds = this.user.followedTopics.map(topic => topic.id);
    }
  }

  public isFollowing(topicId: number): boolean {
    return this.followedTopicIds.includes(topicId);
  }

  public subscribe(topicId: number): void {
    this.topicsService.subscribeToTopic(topicId).subscribe(() => {
      this.followedTopicIds.push(topicId);
      this.subscribeEvent.emit();
      alert('Vous êtes maintenant abonné à ce sujet.');
    }, error => {
      alert('Erreur lors de l\'abonnement: ' + error.message);
    });
  }
}
