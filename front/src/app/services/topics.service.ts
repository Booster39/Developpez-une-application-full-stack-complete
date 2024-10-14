import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Topic } from '../interfaces/topic.interface';
import { TopicsResponse } from '../features/topics/interfaces/api/topicsResponse.interface';
import { TopicResponse } from '../features/topics/interfaces/api/topicResponse.interface';

@Injectable({
  providedIn: 'root'
})
export class TopicService {

  private pathService = 'api/topics';
  private userTopicsApiUrl = '/api/user/me/topics';

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

  // S'abonner à un sujet
  public subscribeToTopic(id: number): Observable<void> {
    return this.httpClient.put<void>(`${this.userTopicsApiUrl}/${id}`, {});
  }

  // Se désabonner d'un sujet
  public unsubscribeFromTopic(id: number): Observable<void> {
    return this.httpClient.delete<void>(`${this.userTopicsApiUrl}/${id}`);
  }
}
