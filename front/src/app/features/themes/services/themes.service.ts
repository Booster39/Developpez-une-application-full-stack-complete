import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Theme } from 'src/app/features/themes/interfaces/theme.interface';
import { ThemeResponse } from '../interfaces/api/themeResponse.interface';
import { ThemesResponse } from '../interfaces/api/themesResponse.interface';


@Injectable({
  providedIn: 'root'
})
export class ThemesService {

  private pathService = 'api/themes';

  constructor(private httpClient: HttpClient) { }

  public all(): Observable<ThemesResponse> {
    return this.httpClient.get<ThemesResponse>(this.pathService);
  }

  public detail(id: string): Observable<Theme> {
    return this.httpClient.get<Theme>(`${this.pathService}/${id}`);
  }

  public create(form: FormData): Observable<ThemeResponse> {
    return this.httpClient.post<ThemeResponse>(this.pathService, form);
  }

  public update(id: string, form: FormData): Observable<ThemeResponse> {
    return this.httpClient.put<ThemeResponse>(`${this.pathService}/${id}`, form);
  }
}
