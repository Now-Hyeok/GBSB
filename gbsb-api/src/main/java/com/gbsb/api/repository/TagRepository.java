package com.gbsb.api.repository;

import com.gbsb.api.entity.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface TagRepository extends JpaRepository<Tag, Long> {

    Optional<Tag> findBySlug(String slug);

    @Query("SELECT t, COUNT(p) as cnt FROM Tag t JOIN t.id tid " +
           "JOIN Post p JOIN p.tags pt WHERE pt.id = t.id " +
           "GROUP BY t ORDER BY cnt DESC")
    List<Object[]> findPopularTagsRaw();

    @Query(value = "SELECT t.id, t.name, t.slug, COUNT(pt.post_id) as cnt " +
           "FROM tags t JOIN post_tags pt ON t.id = pt.tag_id " +
           "GROUP BY t.id, t.name, t.slug " +
           "ORDER BY cnt DESC FETCH FIRST ?1 ROWS ONLY", nativeQuery = true)
    List<Object[]> findPopularTagsNative(int limit);
}
