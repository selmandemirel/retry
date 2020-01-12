package com.turkcell.radar.retry.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RetryConfig {
  private int delay;
  private int timeout;
}
