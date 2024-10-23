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
  constructor(
    private sessionService: SessionService,
    private articlesService: ArticlesService
  ) { 
    this.articles$ = this.articlesService.all();
  }

  get user(): User | undefined {
    return this.sessionService.user;
  }
  sortArticles() {
    this.articles$ = this.articlesService.all().pipe(
      map(data => ({
        ...data,
        articles: this.sortByCriteria(data.articles)
      }))
    );
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
}
