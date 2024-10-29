import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { MatSnackBar } from '@angular/material/snack-bar';
import { ActivatedRoute, Router } from '@angular/router';
import { AuthService } from 'src/app/features/auth/services/auth.service';
import { UserResponse } from 'src/app/features/topics/interfaces/api/userResponse.interface';
import { Topic } from 'src/app/features/topics/interfaces/topic.interface';
import { User } from 'src/app/interfaces/user.interface';
import { SessionService } from 'src/app/services/session.service';
import { TopicService } from 'src/app/services/topics.service';
import { UserService } from 'src/app/services/user.service';

@Component({
  selector: 'app-me',
  templateUrl: './me.component.html',
  styleUrls: ['./me.component.scss']
})
export class MeComponent implements OnInit {

  /**
   * Current user details.
   */
  public user: User | undefined;

  /**
   * Form group for managing user profile data.
   */
  public meForm: FormGroup | undefined;

  /**
   * User ID of the current logged-in user.
   */
  public id: Number | undefined;

  /**
   * Tracks the visibility state of the side menu.
   */
  public isSideMenuOpen: boolean = false;

  /**
   * Initializes services and dependencies for the component.
   * @param authService - Service for authentication and retrieving user details.
   * @param fb - FormBuilder instance for creating the form.
   * @param matSnackBar - Service for displaying notifications.
   * @param userService - Service for managing user data and updates.
   * @param topicService - Service for managing topics.
   * @param sessionService - Manages user session and topic subscriptions.
   * @param router - Angular Router for navigation.
   */
  constructor(
    private authService: AuthService,
    private fb: FormBuilder,
    private matSnackBar: MatSnackBar,
    private userService: UserService,
    private topicService: TopicService,
    public sessionService: SessionService,
    private router: Router,
  ) { }

  /**
   * Lifecycle hook for initializing the component.
   * Loads user data and subscribes to topic changes.
   */
  public ngOnInit(): void {
    this.authService.me().subscribe(
      (user: User) => this.user = user
    );
    this.initForm();

    // Subscribe to followed topics changes
    this.sessionService.$followedTopics().subscribe((topics: Topic[]) => {
      if (this.user) {
        this.user.followedTopics = topics;
      }
    });
  }

  /**
   * Toggles the visibility of the side menu.
   */
  toggleSideMenu(): void {
    this.isSideMenuOpen = !this.isSideMenuOpen;
  }

  /**
   * Navigates back to the previous page.
   */
  public back(): void {
    window.history.back();
  }

  /**
   * Handles form submission to update user profile.
   */
  public submit(): void {
    const formData = new FormData();
    formData.append('name', this.meForm!.get('name')?.value);
    formData.append('email', this.meForm!.get('email')?.value);

    this.id = this.user?.id;
    this.userService
      .update(this.id!.toString(), formData)
      .subscribe((userResponse: UserResponse) => this.exitPage(userResponse));
  }

  /**
   * Initializes the form with user data or empty values if not provided.
   * @param user - Optional User object to populate the form with existing data.
   */
  private initForm(user?: User): void {
    if (user !== undefined) {
      this.router.navigate(['/articles']);
    }
    this.meForm = this.fb.group({
      name: [user ? user.name : '', [Validators.required]],
      email: [user ? user.email : '', [Validators.required, Validators.email]],
    });
  }

  /**
   * Displays a notification and navigates to the articles page upon successful update.
   * @param userResponse - The response containing the message after updating the user profile.
   */
  private exitPage(userResponse: UserResponse): void {
    this.matSnackBar.open(userResponse.message, 'Close', { duration: 3000 });
    this.router.navigate(['/articles']);
  }

  /**
   * Unsubscribes the user from a topic and displays a confirmation message.
   * @param topicId - ID of the topic to unsubscribe from.
   */
  public unsubscribe(topicId: number): void {
    this.topicService.unsubscribeFromTopic(topicId).subscribe(() => {
      this.matSnackBar.open('Vous êtes désabonné de ce sujet.', 'Close', { duration: 3000 });
    }, error => {
      console.error('Erreur lors du désabonnement:', error);
      this.matSnackBar.open('Erreur lors du désabonnement.', 'Close', { duration: 3000 });
    });
  }

  /**
   * Logs out the user and navigates to the home page.
   */
  onLogoutClick(): void {
    this.sessionService.logOut();
    this.router.navigateByUrl('/');
  }
}
