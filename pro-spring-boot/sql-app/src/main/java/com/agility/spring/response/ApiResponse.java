package com.agility.spring.response;

public class ApiResponse {
  String status;
  Object data;

  public ApiResponse(String status, Object data) {
    this.status = status;
    this.data = data;
  }

  @Override
  public String toString() {
    return "status: " + status + ", data: " + data;
  }

  public String getStatus() {
    return status;
  }

  public void setStatus(String status) {
    this.status = status;
  }

  public Object getData() {
    return data;
  }

  public void setData(Object data) {
    this.data = data;
  }
}
