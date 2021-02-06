package com.diagnostic.model;


import static javax.persistence.TemporalType.TIMESTAMP;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import javax.persistence.Temporal;

import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class Auditable<U> {


    @CreatedDate
    @Temporal(TIMESTAMP)
   // @Column(name = "created_date", nullable = false)
    protected Date created_date;



    @LastModifiedDate
    @Temporal(TIMESTAMP)
    protected Date update_date;


    public Date getCreatedDate() throws ParseException {
        return update_date;
    }

    public void setCreatedDate(Date createdDate) throws ParseException {    	
        this.created_date = createdDate;
    }


    public Date getLastModifiedDate() {
        return update_date;
    }

    public void setLastModifiedDate(Date update_date) {
        this.update_date = update_date;
    }
}