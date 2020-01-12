package com.turkcell.radar.retry.model;

import java.util.concurrent.Callable;

public interface IRetryPolicy {

  void doWithRetry(Callable<Object> callable);

}
