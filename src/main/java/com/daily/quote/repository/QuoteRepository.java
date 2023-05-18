package com.daily.quote.repository;

import com.daily.quote.entity.Quote;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface QuoteRepository extends JpaRepository<Quote, UUID> {
    /**
     * Retrieves a random quote from the database, prioritizing quotes with more likes.
     *
     * @param ratio The ratio used to calculate the probability of higher liked quotes.
     * @return A random quote object, with higher priority given to quotes with more likes.
     */
    @Query("SELECT q FROM Quote q JOIN FETCH q.tags t " +
            "WHERE (SELECT (SUM(q.likes) + COUNT(q)) / (SUM(q.likes) + COUNT(q)) FROM Quote q) > CAST(:ratio AS float) " +
            "ORDER BY q.likes DESC LIMIT 1")
    Quote findRandomQuote(float ratio);


    /**
     * Increments the number of likes for a specific quote identified by its ID.
     *
     * @param quoteId The ID of the quote for which the likes should be incremented.
     */
    @Transactional
    @Modifying
    @Query("UPDATE Quote q SET q.likes = q.likes + 1 WHERE q.id = :quoteId")
    void incrementLikes(UUID quoteId);


    /**
     * Retrieves a list of quotes related to a specified quote based on shared tags.
     *
     * @param quoteId The ID of the quote to find related quotes.
     * @param limit   The maximum number of quotes to fetch from the database.
     * @return A list of quote objects that are related to the specified quote based on shared tags.
     */
    @Query("SELECT q FROM Quote q " +
            "JOIN q.tags t " +
            "WHERE t IN " +
            "(SELECT qt FROM Quote q2 " +
            "JOIN q2.tags qt " +
            "WHERE q2.id = :quoteId) " +
            "AND q.id != :quoteId " +
            "ORDER BY Rand " +
            "LIMIT :limit")
    List<Quote> findComparableQuote(UUID quoteId, int limit);

}
