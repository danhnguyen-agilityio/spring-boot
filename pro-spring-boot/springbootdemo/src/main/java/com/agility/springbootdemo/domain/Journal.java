package com.agility.springbootdemo.domain;

import javax.persistence.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Entity
public class Journal {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;
  private String title;
  private Date created;
  private String summary;

  @Transient
  private SimpleDateFormat format = new SimpleDateFormat("MM/dd/yyyy");

  public Journal(String title, String summary, String date) throws ParseException {
    this.title = title;
    this.created = format.parse(date);
    this.summary = summary;
  }

  public Journal() {
  }

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

  public Date getCreated() {
    return created;
  }

  public void setCreated(Date created) {
    this.created = created;
  }

  public String getSummary() {
    return summary;
  }

  public void setSummary(String summary) {
    this.summary = summary;
  }

  public String getCreatedAsShort() {
    return format.format(created);
  }

  @Override
  public String toString() {
    StringBuilder value = new StringBuilder();
    value.append("Id: ");
    value.append(id);
    value.append(",Title: ");
    value.append(title);
    value.append(",Summary: ");
    value.append(summary);
    value.append(",Created: ");
    value.append(getCreatedAsShort());
    return value.toString();
  }
}
