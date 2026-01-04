package com.gbsb.api.entity;

public enum PostCategory {

    DEVELOPMENT(1, "개발", "development");

    Long id;
    String name;
    String slug;


    PostCategory(long id, String name, String slug) {
        this.id = id;
        this.name = name;
        this.slug = slug;
    }
}
