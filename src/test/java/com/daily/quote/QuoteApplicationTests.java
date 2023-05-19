package com.daily.quote;

import com.daily.quote.entity.Quote;
import com.daily.quote.entity.Tag;
import com.daily.quote.repository.QuoteRepository;
import com.daily.quote.repository.TagRepository;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.github.javafaker.Faker;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
class QuoteApplicationTests {

    @Test
    void contextLoads() {
    }

    @Autowired
    private QuoteRepository quoteRepository;

    @Autowired
    private TagRepository tagRepository;

    @Autowired
    private WebApplicationContext webApplicationContext;

    private MockMvc mockMvc;

    @BeforeEach
    public void setup() throws Exception {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.webApplicationContext).build();
        quoteRepository.deleteAll();
    }

    @Test
    void shouldGenerateQuoteFromApiAndFetchOneRandom() throws Exception {

        //Populate only one quote so that we know which quote we will get when we fetch a random one
        MvcResult mvcResult = this.mockMvc.perform(post("/admin/quotes/generate/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        String response = mvcResult.getResponse().getContentAsString();

        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        List<Quote> quotes = mapper.readValue(response, new TypeReference<>(){});

        //fetch random quote
        MvcResult mvcResultRandomQuote = this.mockMvc.perform(get("/quote/daily"))
                .andExpect(status().isOk())
                .andReturn();

        String responseRandomQuote = mvcResultRandomQuote.getResponse().getContentAsString();
        Quote quote = mapper.readValue(responseRandomQuote, Quote.class);

        // The 'quotes' list is returned from the generate method,
        // but we know that it contains only one record because we have stored only one quote.
        // The 'Quote' object obtained from the random endpoint should be the same as the one stored
        // since we have only one quote.
        assert quotes.size() == 1;
        assert quotes.get(0).getQuote().equals(quote.getQuote());
        assert quotes.get(0).getAuthor().equals(quote.getAuthor());
        assert quotes.get(0).getAuthorSlug().equals(quote.getAuthorSlug());
        assert quotes.get(0).getQuoteLength() == quote.getQuoteLength();
        assert quotes.get(0).getLikes() == quote.getLikes();
    }

    @Test
    void shouldIncrementLikesForQuote() throws Exception {
        //set tag
        Set<Tag> tags = new HashSet<>();
        tags.add(generateAndSaveTagEntity());

        // save quote in the database
        Quote quote = this.generateAndSaveQuotaEntity(tags);

        this.mockMvc.perform(patch("/quote/like/"+quote.getId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        // Retrieve the quote after incrementing the like count
        Quote quoteAfterLike = quoteRepository.findById(quote.getId()).orElse(null);

        //check if quote now has one more like
        assert (quote.getLikes() + 1) == quoteAfterLike.getLikes();
    }

    @Test
    void shouldReturn404ForNonExistingQuoteLike() throws Exception {
        this.mockMvc.perform(patch("/quote/like/"+UUID.randomUUID())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }
    @Test
    void shouldGetComparableQuotes() throws Exception {

        //set tag
        Set<Tag> tags = new HashSet<>();
        tags.add(generateAndSaveTagEntity());

        /// Save quotes in the database with the same tag for easy retrieval
        // Retrieve the response from the first quote to obtain its ID for future use
        Quote quote = this.generateAndSaveQuotaEntity(tags);
        this.generateAndSaveQuotaEntity(tags);
        this.generateAndSaveQuotaEntity(tags);

        //fetch quotes with same tag
        MvcResult mvcResult = this.mockMvc.perform(get("/quote/comparable/"+quote.getId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        String response = mvcResult.getResponse().getContentAsString();

        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());

        //map response to list quote
        List<Quote> quotes = mapper.readValue(response, new TypeReference<>(){});

        // We have stored 3 quotes with the same tag, so here we expect to retrieve two quotes
        // since we are excluding the one we are specifically looking for
        assert quotes.size() == 2;
    }

    public Tag generateAndSaveTagEntity() {
        Faker faker = new Faker();
        String tagname = faker.lorem().word();
        return tagRepository.save(new Tag(tagname));

    }

    public Quote generateAndSaveQuotaEntity(Set<Tag> tags) {
        Faker faker = new Faker();
        Quote quote = new Quote();
        quote.setQuote(faker.lorem().sentence());
        quote.setQuoteLength(faker.random().nextInt(1, 101));
        quote.setAuthor(faker.name().fullName());
        quote.setLikes(faker.random().nextInt(1, 101));
        quote.setAuthorSlug(faker.name().fullName());
        quote.setId(UUID.randomUUID());
        quote.setTags(tags);
        quote.setCreatedAt(LocalDate.now());
        quote.setUpdatedAt(LocalDate.now());
        Quote response = quoteRepository.save(quote);
        return response;
    }
}
