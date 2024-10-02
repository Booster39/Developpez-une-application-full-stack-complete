import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Topic } from 'src/app/features/topics/interfaces/topic.interface';
import { TopicResponse } from '../interfaces/api/topicResponse.interface';
import { TopicsResponse } from '../interfaces/api/topicsResponse.interface';


@Injectable({
  providedIn: 'root'
})
export class TopicsService {

  private pathService = 'api/topics';

  constructor(private httpClient: HttpClient) { }

  public all(): Observable<TopicsResponse> {
    return this.httpClient.get<TopicsResponse>(this.pathService);
  }

  public detail(id: string): Observable<Topic> {
    return this.httpClient.get<Topic>(`${this.pathService}/${id}`);
  }

  public create(form: FormData): Observable<TopicResponse> {
    return this.httpClient.post<TopicResponse>(this.pathService, form);
  }

  public update(id: string, form: FormData): Observable<TopicResponse> {
    return this.httpClient.put<TopicResponse>(`${this.pathService}/${id}`, form);
  }
}
