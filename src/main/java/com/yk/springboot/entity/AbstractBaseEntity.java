package com.yk.springboot.entity;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.hibernate.annotations.GenericGenerator;
import org.joda.time.DateTime;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * Created by yk on 16/6/7.
 */
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public class AbstractBaseEntity implements Serializable {

    private static final long serialVersion = 1L;

    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
    @Column(name = "id", length = 32, nullable = false, unique = true)
    private String id;

    @Temporal(TemporalType.TIMESTAMP)
    @CreatedDate
    @Column(updatable = false)
    private Date createdDate;

    @Temporal(TemporalType.TIMESTAMP)
    @LastModifiedDate
    private Date lastModifiedDate;

/*    public AbstractBaseEntity() {
        this.id = UUID.randomUUID().toString();
    }*/

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {//优化语句
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (!obj.getClass().getName().equals(this.getClass().getName())) {
            return false;
        }
        AbstractBaseEntity abe = (AbstractBaseEntity) obj;
        return new EqualsBuilder().append(this.id, abe.getId()).isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(this.id)
                .hashCode();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public DateTime getCreatedDate() {
        return null == createdDate ? null : new DateTime(createdDate);
    }

    public void setCreatedDate(DateTime createdDate) {
        this.createdDate = null == createdDate ? null : createdDate.toDate();
    }

    public DateTime getLastModifiedDate() {
        return null == lastModifiedDate ? null : new DateTime(lastModifiedDate);
    }

    public void setLastModifiedDate(DateTime lastModifiedDate) {
        this.lastModifiedDate = null == lastModifiedDate ? null : lastModifiedDate.toDate();
    }

}
