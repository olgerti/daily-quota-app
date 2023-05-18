package com.daily.quote.service;

import com.daily.quote.entity.Tag;
import com.daily.quote.repository.TagRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TagService {
    @Autowired
    private TagRepository tagRepository;

    public Tag findByName(String tagName) {
        return tagRepository.findByName(tagName);
    }
}
