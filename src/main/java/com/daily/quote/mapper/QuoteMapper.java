package com.daily.quote.mapper;

import com.daily.quote.entity.Quote;
import com.daily.quote.model.QuoteResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;


@Mapper
public interface QuoteMapper {
    /**
     * Converts the response from an external API, which provides a quote, to a Quote entity.
     *
     * @param responseThe response from the external API containing the quote information.
     * @return A Quote entity representing the converted quote from the API response.
     */

    @Mapping(target = "quote", source = "response.content")
    @Mapping(target = "quoteLength", source = "response.length")
    @Mapping(target = "createdAt", source = "response.dateAdded")
    @Mapping(target = "updatedAt", source = "response.dateModified")
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "tags", ignore = true)
    @Mapping(target = "likes", ignore = true)
    Quote convertApiResponseToQuote(QuoteResponse response);
}
