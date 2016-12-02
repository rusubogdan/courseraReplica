package com.courserareplica.service;

import com.courserareplica.model.Chapter;

import java.util.List;

public interface ChapterService {

    Chapter save(Chapter chapter);

    Chapter findBy(Long id);

    List<Chapter> findAll();

    List<Chapter> findBy(String name);
}
