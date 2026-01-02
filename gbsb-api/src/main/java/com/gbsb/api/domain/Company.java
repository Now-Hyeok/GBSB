package com.gbsb.api.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@NoArgsConstructor
@Entity
public class Company {

    @Id
    private Long id;

    private String name;
    private String nameKo;
    private String slug;
    private String logoUrl;
    private String blogUrl;

}
