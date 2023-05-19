package com.daily.quote.controller;

import com.daily.quote.entity.Quote;
import com.daily.quote.service.QuoteService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Validated
@RestController
@RequestMapping("/quote")
public class UserQuoteController {

    public static final int LIMIT = 5;
    Logger logger = LoggerFactory.getLogger(UserQuoteController.class);
    @Autowired
    private QuoteService quoteService;

    /**
     * Retrieves a random quote from the database, prioritizing quotes with more likes.
     *
     * @return A random quote object, with higher priority given to quotes with more likes.
     */
    @GetMapping("/daily")
    public ResponseEntity<Quote> getDailyQuote() {
        Quote quote = this.quoteService.fetchRandomQuote();
        return ResponseEntity.ok().body(quote);
    }


    /**
     * Increments the number of likes for a specific quote.
     *
     * @param quoteId The ID of the quote to be liked.
     * @return The updated Quote object that has been liked.
     * @throws ResponseStatusException if the quote with the specified ID is not found.
     */
    @PatchMapping("/like/{quote_id}")
    public ResponseEntity<Quote> likeQuote(@PathVariable("quote_id") UUID quoteId) throws ResponseStatusException {

        logger.info("Liked quote with ID: {}", quoteId);

        quoteService.likeQuote(quoteId);

        Optional<Quote> quote = quoteService.findById(quoteId);

        if (quote.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Quote not found");
        }

        return ResponseEntity.ok().body(quote.get());
    }


    /**
     * Retrieves a list of quotes that are related to a specific quote based on shared tags.
     *
     * @param quoteId The ID of the quote to find related quotes.
     * @return A list of quote objects that are related to the specified quote based on shared tags.
     */
    @GetMapping("/comparable/{quote_id}")
    public ResponseEntity<List<Quote>> comparableQuote(@PathVariable("quote_id") UUID quoteId) {
        List<Quote> quoteList = this.quoteService.fetchComparableQuote(quoteId, LIMIT);
        logger.info("Fetched comparable quotes for quote with ID: {}", quoteId);
        return ResponseEntity.ok().body(quoteList);
    }

}
