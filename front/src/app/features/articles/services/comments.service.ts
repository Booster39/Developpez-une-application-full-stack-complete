import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { CommentRequest } from '../interfaces/api/commentRequest.interface';
import { CommentResponse } from '../interfaces/api/commentResponse.interface';

@Injectable({
  providedIn: 'root'
})
export class CommentsService {

  private pathService = 'api/comments';

  constructor(private httpClient: HttpClient) { }

  public send(commentRequest: CommentRequest): Observable<CommentResponse> {
    return this.httpClient.post<CommentResponse>(this.pathService, commentRequest);
  } 
  }