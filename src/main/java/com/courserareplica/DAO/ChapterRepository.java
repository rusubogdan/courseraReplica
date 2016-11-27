package com.courserareplica.DAO;

import com.courserareplica.model.Chapter;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ChapterRepository extends CrudRepository<Chapter, Long> {

    // overridden just to return a java.util.List object
    List<Chapter> findAll();

    List<Chapter> findBy(String name);
}
