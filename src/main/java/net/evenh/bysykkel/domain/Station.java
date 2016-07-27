package net.evenh.bysykkel.domain;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Describes a bike rack where a user can pick up a bike.
 */
public class Station {
  private Long id;
  private String title;
  private String subtitle;
  @SerializedName("number_of_locks")
  private Long numberOfLocks;
  @Expose(deserialize = false)
  private Lock lockStatus;
  private Coordinate center;
  private List<Coordinate> bounds;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getSubtitle() {
    return subtitle;
  }

  public void setSubtitle(String subtitle) {
    this.subtitle = subtitle;
  }

  public Long getNumberOfLocks() {
    return numberOfLocks;
  }

  public void setNumberOfLocks(Long numberOfLocks) {
    this.numberOfLocks = numberOfLocks;
  }

  public Lock getLockStatus() {
    return lockStatus;
  }

  public void setLockStatus(Lock lockStatus) {
    this.lockStatus = lockStatus;
  }

  public Coordinate getCenter() {
    return center;
  }

  public void setCenter(Coordinate center) {
    this.center = center;
  }

  public List<Coordinate> getBounds() {
    return bounds;
  }

  public void setBounds(List<Coordinate> bounds) {
    this.bounds = bounds;
  }

  @Override
  public String toString() {
    return "Station{"
      + "id=" + id
      + ", title='" + title + '\''
      + ", numberOfLocks=" + numberOfLocks
      + '}';
  }
}
