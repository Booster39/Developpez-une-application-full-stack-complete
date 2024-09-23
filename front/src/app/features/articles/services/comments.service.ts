import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { CommentRequest } from '../interfaces/api/commentRequest.interface';
import { CommentResponse } from '../interfaces/api/commentResponse.interface';
import { CommentsResponse } from '../interfaces/api/commentsResponse.interface';
import { Comment } from '../interfaces/comment.interface';

@Injectable({
  providedIn: 'root'
})
export class CommentsService {

  private pathService = 'api/comments';

  constructor(private httpClient: HttpClient) { }

  public send(commentRequest: CommentRequest): Observable<CommentResponse> {
    return this.httpClient.post<CommentResponse>(this.pathService, commentRequest);
  } 

  public all(): Observable<CommentsResponse> {
    return this.httpClient.get<CommentsResponse>(this.pathService);
  }

  public detail(id: string): Observable<Comment> {
    return this.httpClient.get<Comment>(`${this.pathService}/${id}`);
  }
  }