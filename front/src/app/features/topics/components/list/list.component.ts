import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { User } from 'src/app/interfaces/user.interface';
import { SessionService } from 'src/app/services/session.service';
import { TopicService } from 'src/app/services/topics.service';
import { MatSnackBar } from '@angular/material/snack-bar';
import { Topic } from '../../interfaces/topic.interface';

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
    private matSnackBar: MatSnackBar,
  ) { }

  ngOnInit(): void {
    this.loadFollowedTopics();
    
    // S'abonner aux changements des sujets suivis
    this.sessionService.$followedTopics().subscribe((topics: Topic[]) => {
      this.followedTopicIds = topics.map(topic => topic.id);
    });
  }
  
  private loadFollowedTopics(): void {
    if (this.user) {
      this.followedTopicIds = this.user.followedTopics.map(topic => topic.id);
    }
  }
  
  public subscribe(topicId: number): void {
    this.topicsService.subscribeToTopic(topicId).subscribe(() => {
      this.matSnackBar.open('Vous êtes abonné à ce sujet.', 'Close', { duration: 3000 });
    }, error => {
      console.error('Erreur lors de l\'abonnement:', error);
      this.matSnackBar.open('Erreur lors de l\'abonnement.', 'Close', { duration: 3000 });
    });
  }
  

  get user(): User | undefined {
    return this.sessionService.user;
  }

  public isFollowing(topicId: number): boolean {
    return this.followedTopicIds.includes(topicId);
  }
}
