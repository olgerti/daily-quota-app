package com.daily.quote.controller;

import com.daily.quote.entity.Quote;
import com.daily.quote.service.QuoteProviderService;
import com.daily.quote.service.QuoteService;
import jakarta.validation.constraints.Max;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin")
public class AdminQuoteController {

    //Set a limit on the number of quotes to store with a single request
    public static final int MAX_QUOTES_PER_REQUEST = 100;
    Logger logger = LoggerFactory.getLogger(AdminQuoteController.class);
    @Autowired
    private QuoteService quoteService;
    @Autowired
    private QuoteProviderService quoteProviderService;

    /**
     * Generates and stores random quotes in the database by hitting an external API.
     *
     * @param count The total number of quotes to fetch and store in the database.
     * @return A list of quotes that have been fetched and stored in the database.
     */
    @PostMapping("/quotes/generate/{count}")
    public List<Quote> generateQuotes(@PathVariable("count") @Max(MAX_QUOTES_PER_REQUEST) int count) {
        return this.quoteProviderService.generate(count);
    }

    /**
     * Resets the quote database by deleting all existing quotes.
     *
     * @return The HTTP status indicating the success or failure of the reset operation.
     */
    @DeleteMapping("/quotes/reset")
    public ResponseEntity<Void> reset() {
        try {
            this.quoteService.deleteAll();
            return ResponseEntity.status(HttpStatus.OK).build();
        } catch (Exception e) {
            logger.error("An error occurred while resetting the quote database", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
