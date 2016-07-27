package net.evenh.bysykkel.domain;

/**
 * Covers lock status.
 */
public class Lock {
  private Long id;
  private LockDetails availability;

  public Long getId() {
    return id;
  }

  public Long getAvailableBikes() {
    return availability.bikes;
  }

  public Long getUsedLocks() {
    return availability.locks;
  }

  private class LockDetails {
    private Long bikes;
    private Long locks;
  }
}
