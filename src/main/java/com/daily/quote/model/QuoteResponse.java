package com.daily.quote.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@EqualsAndHashCode
@ToString
public class QuoteResponse {
    @JsonProperty(value = "_id")
    private String id;
    private String content;
    private String author;

    private List<String> tags;
    private String authorSlug;
    private int length;
    private LocalDate dateAdded;

    private LocalDate dateModified;

}
