package com.daily.quote.entity;

import jakarta.persistence.*;
import org.hibernate.annotations.GenericGenerator;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "quotes")
public class Quote {
    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "org.hibernate.id.UUIDGenerator")
    private UUID id;
    @Column(length = 1000)
    private String quote;
    private String author;
    @Column(name = "author_slug")
    private String authorSlug;
    @Column(name = "quote_length")
    private int quoteLength;
    @ManyToMany(cascade = CascadeType.ALL)
    private Set<Tag> tags = new HashSet<>();
    @Column(name = "likes", columnDefinition = "int default 0")
    private int likes;
    @Column(name = "created_at")
    private LocalDate createdAt;
    @Column(name = "updated_at")
    private LocalDate updatedAt;

    public Quote() {
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getQuote() {
        return quote;
    }

    public void setQuote(String quote) {
        this.quote = quote;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getAuthorSlug() {
        return authorSlug;
    }

    public void setAuthorSlug(String authorSlug) {
        this.authorSlug = authorSlug;
    }

    public int getQuoteLength() {
        return quoteLength;
    }

    public void setQuoteLength(int quoteLength) {
        this.quoteLength = quoteLength;
    }

    public Set<Tag> getTags() {
        return tags;
    }

    public void setTags(Set<Tag> tags) {
        this.tags = tags;
    }

    public int getLikes() {
        return likes;
    }

    public void setLikes(int likes) {
        this.likes = likes;
    }

    public LocalDate getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDate createdAt) {
        this.createdAt = createdAt;
    }


    public LocalDate getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDate updatedAt) {
        this.updatedAt = updatedAt;
    }

    @Override
    public String toString() {
        return "Quote{" +
                "id=" + id +
                ", quote='" + quote + '\'' +
                ", author='" + author + '\'' +
                ", authorSlug='" + authorSlug + '\'' +
                ", quoteLength=" + quoteLength +
                ", tags=" + tags +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Quote quote1 = (Quote) o;
        return quoteLength == quote1.quoteLength && Objects.equals(id, quote1.id) && Objects.equals(quote, quote1.quote) && Objects.equals(author, quote1.author) && Objects.equals(authorSlug, quote1.authorSlug) && Objects.equals(tags, quote1.tags) && Objects.equals(createdAt, quote1.createdAt) && Objects.equals(updatedAt, quote1.updatedAt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, quote, author, authorSlug, quoteLength, tags, createdAt, updatedAt);
    }
}
