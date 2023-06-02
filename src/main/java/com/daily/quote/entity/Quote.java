package com.daily.quote.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@NoArgsConstructor
@Entity
@Data
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
}
