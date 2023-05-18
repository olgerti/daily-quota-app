package com.daily.quote.converter;

import com.daily.quote.entity.Quote;
import com.daily.quote.model.QuoteResponse;


public class QuoteConverter {
    /**
     * Converts the response from an external API, which provides a quote, to a Quote entity.
     *
     * @param responseThe response from the external API containing the quote information.
     * @return A Quote entity representing the converted quote from the API response.
     */
    public static Quote convertApiResponseToQuote(QuoteResponse response) {
        Quote quote = new Quote();
        quote.setQuoteLength(response.getLength());
        quote.setQuote(response.getContent());
        quote.setAuthorSlug(response.getAuthorSlug());
        quote.setCreatedAt(response.getDateAdded());
        quote.setUpdatedAt(response.getDateModified());
        quote.setAuthor(response.getAuthor());
        return quote;
    }

}
