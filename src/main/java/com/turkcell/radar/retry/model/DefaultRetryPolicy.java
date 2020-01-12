package com.turkcell.radar.retry.model;

import java.util.concurrent.Callable;

public class DefaultRetryPolicy extends RetryPolicy {

  private int maxRetryCount;

  public DefaultRetryPolicy(String retryConfigStr) {
    super(retryConfigStr);
  }


  @Override
  protected void parseRetry() {
      this.maxRetryCount = Integer.parseInt(this.retryConfigStr);
  }


  @Override
  protected void setNextRetryConfig() {

  }
}
