package com.daily.quote.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public List<String> getTags() {
        return tags;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }

    public String getAuthorSlug() {
        return authorSlug;
    }

    public void setAuthorSlug(String authorSlug) {
        this.authorSlug = authorSlug;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public LocalDate getDateAdded() {
        return dateAdded;
    }

    public void setDateAdded(LocalDate dateAdded) {
        this.dateAdded = dateAdded;
    }

    public LocalDate getDateModified() {
        return dateModified;
    }

    public void setDateModified(LocalDate dateModified) {
        this.dateModified = dateModified;
    }

    @Override
    public String toString() {
        return "QuoteResponse{" +
                "id='" + id + '\'' +
                ", content='" + content + '\'' +
                ", author='" + author + '\'' +
                ", tags=" + tags +
                ", authorSlug='" + authorSlug + '\'' +
                ", length=" + length +
                ", dateAdded=" + dateAdded +
                ", dateModified=" + dateModified +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        QuoteResponse that = (QuoteResponse) o;
        return length == that.length && Objects.equals(id, that.id) && Objects.equals(content, that.content) && Objects.equals(author, that.author) && Objects.equals(tags, that.tags) && Objects.equals(authorSlug, that.authorSlug) && Objects.equals(dateAdded, that.dateAdded) && Objects.equals(dateModified, that.dateModified);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, content, author, tags, authorSlug, length, dateAdded, dateModified);
    }
}
