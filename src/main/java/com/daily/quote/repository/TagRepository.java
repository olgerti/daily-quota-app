package com.daily.quote.repository;

import com.daily.quote.entity.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface TagRepository extends JpaRepository<Tag, UUID> {
    /**
     * Finds a single tag in the database by its name.
     *
     * @param tagName The name of the tag to find.
     * @return The Tag entity representing the found tag.
     */
    Tag findByName(String tagName);

}
