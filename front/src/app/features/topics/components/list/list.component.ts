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
  /**
   * Topic subject to be displayed in the component.
   */
  @Input() subject!: Topic;

  /**
   * Event emitter to notify the parent component when a subscription event occurs.
   */
  @Output() subscribeEvent = new EventEmitter<void>();

  /**
   * Observable of all topics retrieved from the TopicService.
   */
  public topics$ = this.topicsService.all();

  /**
   * Array of followed topic IDs for the current user.
   */
  public followedTopicIds: number[] = [];

  /**
   * State variable to determine if the side menu is open.
   */
  public isSideMenuOpen: boolean = false;

  /**
   * Constructor to initialize services used in this component.
   * @param sessionService - Service to manage session data.
   * @param topicsService - Service to manage topics.
   * @param matSnackBar - Angular Material service to show snackbar messages.
   */
  constructor(
    private sessionService: SessionService,
    private topicsService: TopicService,
    private matSnackBar: MatSnackBar,
  ) { }

  /**
   * Initializes component data when component is loaded.
   * Loads the followed topics and subscribes to updates on followed topics.
   */
  ngOnInit(): void {
    this.loadFollowedTopics();
    
    // Subscribe to followed topics changes in session
    this.sessionService.$followedTopics().subscribe((topics: Topic[]) => {
      this.followedTopicIds = topics.map(topic => topic.id);
    });
  }

  /**
   * Toggles the visibility of the side menu.
   */
  toggleSideMenu(): void {
    this.isSideMenuOpen = !this.isSideMenuOpen;
  }

  /**
   * Loads the followed topics for the current user and sets them to `followedTopicIds`.
   * This is done only if the user is logged in.
   */
  private loadFollowedTopics(): void {
    if (this.user) {
      this.followedTopicIds = this.user.followedTopics.map(topic => topic.id);
    }
  }

  /**
   * Subscribes the current user to a specific topic by its ID.
   * Shows a success or error message based on the subscription result.
   * @param topicId - ID of the topic to subscribe to.
   */
  public subscribe(topicId: number): void {
    this.topicsService.subscribeToTopic(topicId).subscribe(() => {
      this.matSnackBar.open('Vous êtes abonné à ce sujet.', 'Close', { duration: 3000 });
    }, error => {
      console.error('Erreur lors de l\'abonnement:', error);
      this.matSnackBar.open('Erreur lors de l\'abonnement.', 'Close', { duration: 3000 });
    });
  }

  /**
   * Getter for the current user from the session service.
   * @returns The current user or undefined if no user is logged in.
   */
  get user(): User | undefined {
    return this.sessionService.user;
  }

  /**
   * Checks if the current user is following a specific topic by its ID.
   * @param topicId - ID of the topic to check.
   * @returns True if the topic is followed by the user, otherwise false.
   */
  public isFollowing(topicId: number): boolean {
    return this.followedTopicIds.includes(topicId);
  }
}
