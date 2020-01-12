package com.turkcell.radar.retry.model;

import com.turkcell.radar.retry.service.RetryExecutorService;
import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;

@Getter
@Setter
public abstract class RetryPolicy implements IRetryPolicy {

  @Autowired
  RetryExecutorService retryExecutorService;


  protected String retryConfigStr;
  private int retryCount;
  protected List<RetryState> retryStates;
  protected RetryState curretRetRyState;
  protected RetryConfig retryConfig;

  public RetryPolicy(String retryConfigStr) {
    this.retryConfigStr=retryConfigStr;
    parseRetry();
  }

  protected abstract void parseRetry();

  //Set retry parameters in this step(delay,timeout,exit)
  @Override
  public void doWithRetry(Callable<Object> callable){
    setNextRetryConfig();
    retry(callable);
  };

  protected abstract void setNextRetryConfig();

  protected Object retry(Callable<Object> callable) {
    Object result = null;

    onStart();
    try {

      result = execute(callable);

    } catch (Exception e) {

      e.printStackTrace();
      onEnd();
      doWithRetry(callable);

    }

    return result;
  }

  protected Object execute(Callable<Object> callable) throws ExecutionException, InterruptedException, TimeoutException {
    Object result = null;

    Future<Object> future;
    Future<Object> executorFuture = retryExecutorService.executor.submit(callable);

    if (retryConfig.getDelay() >= 0) {

      future = retryExecutorService.scheduledExecutor.schedule(new Callable<Object>() {
        @Override
        public Object call() throws Exception {

          return executorFuture;

        }
      },retryConfig.getDelay(), TimeUnit.SECONDS);

    } else {

      future = executorFuture;

    }

    if (retryConfig.getTimeout()>0){
      return future.get(retryConfig.getTimeout(),TimeUnit.SECONDS);
    }else{
      return future.get();
    }
  }

  protected void onStart() {
    this.curretRetRyState = new RetryState();
    this.curretRetRyState.setStartTime(LocalDateTime.now());
  }

  protected void onEnd() {
    this.curretRetRyState.setEndTime(LocalDateTime.now());
  }

  public int getRetryCount(){
    return retryStates.size();
  }


}
