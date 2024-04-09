package com.demo.bookstore.utils;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

@NoArgsConstructor
@MappedSuperclass
public class BaseEntity implements Serializable {

  @JsonIgnore
  @Id
  @GeneratedValue(strategy= GenerationType.IDENTITY, generator="native")
  @GenericGenerator(name = "native", strategy = "native")
  protected Long id;

  @Column(name = "date_created")
  @Temporal(TemporalType.TIMESTAMP)
  @DateTimeFormat(pattern = "dd/MM/yyyy")
  @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="dd/MM/yyyy")
  protected Date dateCreated = new Date();

  @Column(name = "last_modified")
  @Temporal(TemporalType.TIMESTAMP)
  @DateTimeFormat(pattern = "dd/MM/yyyy")
  @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="dd/MM/yyyy")
  protected Date lastModified;

  public BaseEntity(Long id) {
    this.id = id;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public Date getDateCreated() {
    return dateCreated;
  }

  public void setDateCreated(Date dateCreated) {
    this.dateCreated = dateCreated;
  }

  public Date getLastModified() {
    return lastModified;
  }

  public void setLastModified(Date lastModified) {
    this.lastModified = lastModified;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof BaseEntity that)) return false;
    return Objects.equals(getId(), that.getId());
  }

  @Override
  public int hashCode() {
    return Objects.hash(getId());
  }
}
