import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Theme } from '../interfaces/theme.interface';

@Injectable({
  providedIn: 'root'
})
export class ThemeService {

  private pathService = 'api/themes';

  constructor(private httpClient: HttpClient) { }

  public all(): Observable<Theme[]> {
    return this.httpClient.get<Theme[]>(this.pathService);
  }

  public detail(id: string): Observable<Theme> {
    return this.httpClient.get<Theme>(`${this.pathService}/${id}`);
  }
}
