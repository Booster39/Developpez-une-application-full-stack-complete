package com.openclassrooms.mddapi.payloads.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;


@Schema(description = "Requête pour créer un message")
@Data
public class CommentResponse {

  private Long authorId;
  private Long articleId;
  private String content;
  public CommentResponse(Long authorId, Long articleId, String content)
  {
    this.authorId = authorId;
    this.articleId = articleId;
    this.content = content;
  }
}
