import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { MatSnackBar } from '@angular/material/snack-bar';
import { ActivatedRoute, Router } from '@angular/router';
import { SessionService } from 'src/app/services/session.service';
import { ArticleResponse } from '../../interfaces/api/articleResponse.interface';
import { Article } from '../../interfaces/article.interface';
import { ArticlesService } from '../../services/articles.service';
import { TopicService } from 'src/app/services/topics.service';
import { map } from 'rxjs';

@Component({
  selector: 'app-form',
  templateUrl: './form.component.html',
  styleUrls: ['./form.component.scss'],
})
export class FormComponent implements OnInit {
  
  /**
   * Determines if the form is in update mode.
   */
  public onUpdate: boolean = false;

  /**
   * Form group for managing article form fields and validation.
   */
  public articleForm: FormGroup | undefined;

  /**
   * Tracks the visibility state of the side menu.
   */
  public isSideMenuOpen: boolean = false;

  /**
   * Observable for retrieving and displaying available topics.
   * Maps the API response to extract topic data.
   */
  public topics$ = this.topicService.all().pipe(
    map((response) => response.topics)
  );

  /**
   * Holds the article ID if the component is in update mode.
   */
  private id: string | undefined;

  /**
   * Initializes services and form builder, sets up routing, and initializes topics observable.
   * @param route - Provides route information for retrieving article ID.
   * @param fb - FormBuilder instance for creating the article form.
   * @param matSnackBar - Material Snackbar for displaying messages.
   * @param articlesService - Service for managing article-related API requests.
   * @param topicService - Service for retrieving topics.
   * @param router - Router instance for navigating between views.
   * @param sessionService - Manages user session data and authentication.
   */
  constructor(
    private route: ActivatedRoute,
    private fb: FormBuilder,
    private matSnackBar: MatSnackBar,
    private articlesService: ArticlesService,
    private topicService: TopicService,
    private router: Router,
    private sessionService: SessionService
  ) {}

  /**
   * Lifecycle hook to initialize the component.
   * Checks if the component is in update mode by inspecting the route, and loads article data if necessary.
   */
  public ngOnInit(): void {
    const url = this.router.url;
    if (url.includes('update')) {
      this.onUpdate = true;
      this.id = this.route.snapshot.paramMap.get('id')!;
      this.articlesService
        .detail(this.id)
        .subscribe((article: Article) => this.initForm(article));
    } else {
      this.initForm();
    }
  }

  /**
   * Toggles the visibility of the side menu.
   */
  toggleSideMenu(): void {
    this.isSideMenuOpen = !this.isSideMenuOpen;
  }

  /**
   * Handles form submission. If in create mode, submits the form data to create a new article.
   */
  public submit(): void {
    const formData = new FormData();
    formData.append('title', this.articleForm!.get('title')?.value);
    formData.append('content', this.articleForm!.get('content')?.value);
    formData.append('topic_id', this.articleForm!.get('topic_id')?.value);
    if (!this.onUpdate) {
      this.articlesService
        .create(formData)
        .subscribe((articleResponse: ArticleResponse) =>
          this.exitPage(articleResponse)
        );
    }
  }

  /**
   * Initializes the form with default values or with data from the provided article if in update mode.
   * Ensures only the author can update the article.
   * @param article - Optional article data used to populate the form fields when in update mode.
   */
  private initForm(article?: Article): void {
    console.log(article);
    console.log(this.sessionService.user!.id);
    if (
      article !== undefined &&
      article?.author_id !== this.sessionService.user!.id
    ) {
      this.router.navigate(['/articles']);
    }
    this.articleForm = this.fb.group({
      title: [article ? article.title : '', [Validators.required]],
      content: [article ? article.content : '', [Validators.required]],
      topic_id: [article ? article.topic_id : '', [Validators.required]],
    });
  }

  /**
   * Exits the form page after successfully creating or updating an article.
   * Displays a snackbar message and redirects to the articles list page.
   * @param articleResponse - Response from the API containing a success message.
   */
  private exitPage(articleResponse: ArticleResponse): void {
    this.matSnackBar.open(articleResponse.message, 'Close', { duration: 3000 });
    this.router.navigate(['/articles']);
  }
}
