package com.courserareplica.service;

import com.courserareplica.DAO.ParagraphRepository;
import com.courserareplica.model.Paragraph;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ParagraphServiceImpl implements ParagraphService {

    @Autowired
    private ParagraphRepository paragraphRepository;

    @Override
    public Paragraph save(Paragraph paragraph) {
        return paragraphRepository.save(paragraph);
    }

    @Override
    public Paragraph findBy(Long id) {
        return paragraphRepository.findOne(id);
    }

    @Override
    public List<Paragraph> find(String text) {
        return paragraphRepository.findBy(text);
    }

    @Override
    public void delete(Paragraph paragraph) {
        paragraphRepository.delete(paragraph);
    }
}
