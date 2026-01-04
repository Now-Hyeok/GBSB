package com.gbsb.api.repository;

import com.gbsb.api.entity.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface PostRepository extends JpaRepository<Post, Long> {

    Page<Post> findByCompanyId(Long companyId, Pageable pageable);

    Page<Post> findByCategoryId(Long categoryId, Pageable pageable);

    @Query("SELECT p FROM Post p JOIN p.tags t WHERE t.id = :tagId")
    Page<Post> findByTagsId(@Param("tagId") Long tagId, Pageable pageable);

    @Query("SELECT p FROM Post p JOIN p.tags t WHERE t.slug = :tagSlug")
    Page<Post> findByTagsSlug(@Param("tagSlug") String tagSlug, Pageable pageable);

    Page<Post> findByTitleContainingOrSummaryContaining(
        String title,
        String summary,
        Pageable pageable
    );
}
