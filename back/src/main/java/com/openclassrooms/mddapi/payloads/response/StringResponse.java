package com.openclassrooms.mddapi.payloads.response;

import lombok.Data;

@Data
public class StringResponse {
  private String message;

  public StringResponse(String message)
  {
    this.message = message;
  }
}
