package com.daily.quote.service;

import com.daily.quote.converter.QuoteConverter;
import com.daily.quote.entity.Quote;
import com.daily.quote.entity.Tag;
import com.daily.quote.model.QuoteResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;

@Service
public class QuoteProviderService {

    Logger logger = LoggerFactory.getLogger(QuoteService.class);
    private RestTemplate restTemplate;
    private QuoteService quoteService;
    private TagService tagService;
    @Value("${quote.api.url}")
    private String quoteApiUrl;

    @Autowired
    public QuoteProviderService(RestTemplateBuilder builder, QuoteService quoteService,
                                TagService tagService) {
        this.restTemplate = builder.build();
        this.quoteService = quoteService;
        this.tagService = tagService;
    }

    /**
     * Generates and stores random quotes in the database by hitting an external API.
     *
     * @param count The total number of quotes to fetch and store in the database.
     * @return A list of quotes that have been fetched and stored in the database.
     */
    public List<Quote> generate(int count) {
        logger.info("Initiating the process to fetch " + count + " quotes");
        List<Quote> quoteList = new ArrayList<>();

//        for (int i = 0; i < count; i++) {
//            QuoteResponse quoteResponse = this.fetchQuoteFromApi();
//            Quote quote = QuoteConverter.convertApiResponseToQuote(quoteResponse);
//
//            for (String tagName : quoteResponse.getTags()) {
//                Tag tag = tagService.findByName(tagName);
//                if (tag != null) {
//                    quote.getTags().add(tag);
//                } else {
//                    quote.getTags().add(new Tag(tagName));
//                }
//            }
//            quoteList.add(quoteService.save(quote));
//        }

        IntStream.range(0, count).forEach(
                i -> {
                    QuoteResponse quoteResponse = this.fetchQuoteFromApi();
                    Quote quote = QuoteConverter.convertApiResponseToQuote(quoteResponse);
                    quoteResponse.getTags().forEach(
                            tagName -> {
                                Tag tag  = Optional.ofNullable(tagService.findByName(tagName))
                                        .orElse(new Tag(tagName));
                                quote.getTags().add(tag);
                            }
                    );

                    quoteList.add(quoteService.save(quote));
                }

        );

        logger.info("Completed fetching and storing quotes");

        return quoteList;
    }

    public QuoteResponse fetchQuoteFromApi() {
        return restTemplate.getForEntity(quoteApiUrl, QuoteResponse.class).getBody();
    }
}
