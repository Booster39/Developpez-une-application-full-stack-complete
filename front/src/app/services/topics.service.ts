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

  constructor(private httpClient: HttpClient) { }

  public all(): Observable<TopicsResponse> {
    return this.httpClient.get<TopicsResponse>(this.pathService);
  }

  public detail(id: string): Observable<Topic> {
    return this.httpClient.get<Topic>(`${this.pathService}/${id}`);
  }
}
