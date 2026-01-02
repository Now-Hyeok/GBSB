package com.gbsb.api.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class Tags {

    @Id
    private Long id;
    private String name;
    private String slug;
    private int count;
}
