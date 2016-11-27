package com.courserareplica.service;

import com.courserareplica.model.Chapter;

import java.util.List;

public interface ChapterService {

    List<Chapter> findAll();

    List<Chapter> findBy(String name);
}
