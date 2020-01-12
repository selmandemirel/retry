package com.turkcell.radar.retry.model;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RetryState {

  private LocalDateTime startTime;
  private LocalDateTime endTime;

  public RetryState() {
    this.startTime = LocalDateTime.now();
  }

  public long getExecutionTimeInSeconds(){
    return startTime.until(endTime, ChronoUnit.SECONDS);
  };

  public long getExecutionTime(ChronoUnit duration){
    return startTime.until(endTime, duration);
  };
}
