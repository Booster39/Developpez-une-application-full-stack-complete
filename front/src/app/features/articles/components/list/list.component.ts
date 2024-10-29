import { Component } from '@angular/core';
import { User } from 'src/app/interfaces/user.interface';
import { ArticlesService } from '../../services/articles.service';
import { SessionService } from 'src/app/services/session.service';
import { map, Observable } from 'rxjs';

@Component({
  selector: 'app-list',
  templateUrl: './list.component.html',
  styleUrls: ['./list.component.scss']
})
export class ListComponent {

  /**
   * Observable containing the list of articles, filtered and sorted according to user preferences.
   */
  public articles$: Observable<any>;

  /**
   * Criteria to sort articles, either 'date' or 'title'.
   */
  public sortCriteria: string = 'date';

  /**
   * Direction of the sorting order, either 'asc' for ascending or 'desc' for descending.
   */
  public sortDirection: string = 'desc';

  /**
   * Visibility state of the sorting options menu.
   */
  public isSortVisible: boolean = false; 

  /**
   * State to determine if the side menu is open or closed.
   */
  public isSideMenuOpen: boolean = false;

  /**
   * Array storing IDs of topics followed by the user.
   * Used to filter articles by followed topics.
   */
  private followedTopicIds: number[] = [];

  /**
   * Constructor initializes session and article services, loads followed topics, and sets up the filtered articles observable.
   * @param sessionService - Service to manage session data and user information.
   * @param articlesService - Service to fetch articles from the API.
   */
  constructor(
    private sessionService: SessionService,
    private articlesService: ArticlesService
  ) { 
    this.loadFollowedTopics();
    this.articles$ = this.getFollowedTopicsArticles();
  }

  /**
   * Getter for the current user from the session service.
   * @returns The current user or undefined if no user is logged in.
   */
  get user(): User | undefined {
    return this.sessionService.user;
  }

  /**
   * Loads the IDs of the topics followed by the current user.
   * Sets the `followedTopicIds` array if the user is logged in.
   */
  private loadFollowedTopics(): void {
    if (this.user) {
      this.followedTopicIds = this.user.followedTopics.map(topic => topic.id);
    }
  }

  /**
   * Filters articles to only include those belonging to topics followed by the user.
   * Also sorts the articles according to the specified criteria.
   * @returns An observable containing the filtered and sorted list of articles.
   */
  private getFollowedTopicsArticles(): Observable<any> {
    return this.articlesService.all().pipe(
      map(data => ({
        ...data,
        articles: this.sortByCriteria(
          data.articles.filter((article: any) =>
            this.followedTopicIds.includes(article.topic_id)
          )
        )
      }))
    );
  }

  /**
   * Triggers the sorting of articles based on the selected criteria and direction.
   */
  sortArticles(): void {
    this.articles$ = this.getFollowedTopicsArticles();
  }

  /**
   * Sorts an array of articles based on the specified criteria (`date` or `title`) and direction (`asc` or `desc`).
   * @param articles - Array of articles to be sorted.
   * @returns The sorted array of articles.
   */
  sortByCriteria(articles: any[]): any[] {
    return articles.sort((a, b) => {
      const valueA = this.sortCriteria === 'date' ? new Date(a.created_at).getTime() : a.title.toLowerCase();
      const valueB = this.sortCriteria === 'date' ? new Date(b.created_at).getTime() : b.title.toLowerCase();

      if (this.sortDirection === 'asc') {
        return valueA > valueB ? 1 : -1;
      } else {
        return valueA < valueB ? 1 : -1;
      }
    });
  }

  /**
   * Toggles the sorting direction between ascending (`asc`) and descending (`desc`),
   * and re-sorts the articles based on the new direction.
   */
  toggleSortDirection(): void {
    this.sortDirection = this.sortDirection === 'asc' ? 'desc' : 'asc';
    this.sortArticles();
  }

  /**
   * Toggles the visibility of the sorting options menu.
   */
  toggleSortVisibility(): void {
    this.isSortVisible = !this.isSortVisible;
  }

  /**
   * Toggles the visibility of the side menu.
   */
  toggleSideMenu(): void {
    this.isSideMenuOpen = !this.isSideMenuOpen;
  }
}
