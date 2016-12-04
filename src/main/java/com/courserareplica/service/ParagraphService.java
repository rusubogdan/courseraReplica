package com.courserareplica.service;

import com.courserareplica.model.Paragraph;

import java.util.List;

public interface ParagraphService {

    Paragraph save(Paragraph paragraph);

    Paragraph findBy(Long id);

    List<Paragraph> find(String text);

    void delete(Paragraph paragraph);
}
