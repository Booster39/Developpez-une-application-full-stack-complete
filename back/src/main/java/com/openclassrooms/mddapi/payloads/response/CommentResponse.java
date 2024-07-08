package com.openclassrooms.mddapi.payloads.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;


@Schema(description = "Requête pour créer un message")
@Data
public class CommentResponse {

  private Long author_id;
  private Long article_id;
  private String content;
  public CommentResponse(Long author_id, Long article_id, String content)
  {
    this.author_id = author_id;
    this.article_id = article_id;
    this.content = content;
  }
}
