package com.gbsb.api.common;

public enum PostTag {

    BACKEND(1, "Backend", "backend");

    Long id;
    String name;
    String slug;

    PostTag(long id, String name, String slug) {
        this.id = id;
        this.name = name;
        this.slug = slug;

    }
}
