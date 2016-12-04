package com.courserareplica.DAO;

import com.courserareplica.model.Paragraph;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ParagraphRepository extends CrudRepository<Paragraph, Long> {

    // overridden just to return a java.util.List object
    List<Paragraph> findAll();

    // TODO create to search in text
    List<Paragraph> findBy(String text);


}
