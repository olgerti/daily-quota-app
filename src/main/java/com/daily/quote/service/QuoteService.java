package com.daily.quote.service;

import com.daily.quote.entity.Quote;
import com.daily.quote.repository.QuoteRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class QuoteService {
    Logger logger = LoggerFactory.getLogger(QuoteService.class);
    private RestTemplate restTemplate;
    private QuoteRepository quoteRepository;
    @Value("${quote.api.url}")
    private String quoteApiUrl;

    @Autowired
    public QuoteService(RestTemplateBuilder builder, QuoteRepository quoteRepository) {
        this.restTemplate = builder.build();
        this.quoteRepository = quoteRepository;
    }

    /**
     * Saves a quote object to the database.
     *
     * @param quote The quote object to be saved in the database.
     * @return The quote object that has been saved in the database.
     */
    public Quote save(Quote quote) {
        return quoteRepository.save(quote);
    }

    /**
     * Fetches a random quote from the database, prioritizing quotes with higher likes.
     *
     * @return A random Quote object from the database, prioritizing quotes with higher likes.
     */
    public Quote fetchRandomQuote() {
        return quoteRepository.findRandomQuote();
    }

    /**
     * Increments the number of likes for a specific quote identified by its ID.
     *
     * @param quote object for which the likes should be incremented.
     */

    public void likeQuote(UUID quoteId) {
        quoteRepository.incrementLikes(quoteId);
    }

    /**
     * Retrieves a list of quotes related to a specified quote based on shared tags.
     *
     * @param quoteId The ID of the quote to find related quotes.
     * @param limit   The maximum number of quotes to fetch from the database.
     * @return A list of quote objects that are related to the specified quote based on shared tags.
     */
    public List<Quote> fetchComparableQuote(UUID quoteId, int limit) {
        return quoteRepository.findComparableQuote(quoteId, limit);
    }

    /**
     * Deletes all quotes from the database.
     */
    public void deleteAll() {
        quoteRepository.deleteAll();
    }

    /**
     * Finds a quote in the database by its ID.
     *
     * @param id The ID of the quote to find.
     * @return An Optional containing the quote found in the database, or an empty Optional if no quote with the specified ID exists.
     */
    public Optional<Quote> findById(UUID id) {
        return quoteRepository.findById(id);
    }
}
