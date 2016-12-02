package com.courserareplica.service;

import com.courserareplica.DAO.ChapterRepository;
import com.courserareplica.model.Chapter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
* The service handles all the user requests that are supported by the application
*/
@Service
public class ChapterServiceImpl implements ChapterService {

    @Autowired
    private ChapterRepository chapterRepository;

    @Override
    public Chapter save(Chapter chapter) {
        return chapterRepository.save(chapter);
    }

    @Override
    public Chapter findBy(Long id) {
        return chapterRepository.findOne(id);
    }

    @Override
    public List<Chapter> findAll() {
        return chapterRepository.findAll();
    }

    @Override
    public List<Chapter> findBy(String name) {
        return chapterRepository.findBy(name);
    }
}
