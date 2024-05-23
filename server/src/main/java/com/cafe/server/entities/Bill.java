package com.cafe.server.entities;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

@Entity
@Data
@DynamicInsert
@DynamicUpdate
public class Bill {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String uuid;

    private String name;

    private String email;

    private String contactnumber;

    private String paymentmethod;

    private int total;

    // private Product productdetails;
    private int productId;

    private String productdetails;
    private String createdBy;
}
