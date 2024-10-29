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


  public articles$: Observable<any>;
  public sortCriteria: string = 'date';
  public sortDirection: string = 'desc';
  public isSortVisible: boolean = false; 
  public isSideMenuOpen: boolean = false;
  private followedTopicIds: number[] = []; // Ajout pour stocker les topics suivis de l'utilisateur

  constructor(
    private sessionService: SessionService,
    private articlesService: ArticlesService
  ) { 
    this.loadFollowedTopics();
    this.articles$ = this.getFollowedTopicsArticles();
  }

  get user(): User | undefined {
    return this.sessionService.user;
  }

// Nouvelle méthode pour charger les topics suivis
private loadFollowedTopics(): void {
  if (this.user) {
    this.followedTopicIds = this.user.followedTopics.map(topic => topic.id);
  }
}

// Filtrer les articles par les topics suivis
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




  sortArticles() {
    this.articles$ = this.getFollowedTopicsArticles();
  }

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

  toggleSortDirection() {
    this.sortDirection = this.sortDirection === 'asc' ? 'desc' : 'asc';
    this.sortArticles();
  }
  toggleSortVisibility() {
    this.isSortVisible = !this.isSortVisible;
  }

  toggleSideMenu() {
    this.isSideMenuOpen = !this.isSideMenuOpen;
  }

}
