package com.turkcell.radar.retry.service;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import org.springframework.stereotype.Service;

@Service
public class RetryExecutorService {
  public static final ScheduledExecutorService scheduledExecutor = Executors.newScheduledThreadPool(1);
  public static final ExecutorService executor = Executors.newCachedThreadPool();

  void executeWithDelay(){

  }

  void executeWithTimeout(){

  }

}
