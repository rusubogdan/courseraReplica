package com.courserareplica.service;

import com.courserareplica.DAO.ChapterRepository;
import com.courserareplica.DAO.UserActivityRepository;
import com.courserareplica.model.Chapter;
import com.courserareplica.model.UserActivity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class UserActivityServiceImpl implements UserActivityService {

    @Autowired
    private UserActivityRepository userActivityRepository;

    @Autowired
    private ChapterRepository chapterRepository;

    @Override
    public UserActivity save(UserActivity userActivity) {
        UserActivity savedUserActivity = null;

        try {
            List l = userActivityRepository.findByUserIdAndChapterIdAndParagraphId
                    (userActivity.getUserId(), userActivity.getChapterId(), userActivity.getParagraphId());
            if (l != null && l.isEmpty()) {
                savedUserActivity = userActivityRepository.save(userActivity);
            }
        } catch (Exception e) {
            log.debug(e.getMessage());

            return null;
        }

        List<UserActivity> chapterParagraphs = userActivityRepository
                .findByUserIdAndChapterId(userActivity.getUserId(), userActivity.getChapterId());

        Chapter chapter = chapterRepository.findOne(userActivity.getChapterId());

        if (chapter.getParagraphs().size() == chapterParagraphs.size()) {

        }

        return savedUserActivity;
    }

    @Override
    public List<UserActivity> findAll() {
        return userActivityRepository.findAll();
    }

    @Override
    public List<UserActivity> findByUserId(String userId) {
        return userActivityRepository.findByUserId(userId);
    }

    @Override
    public List<UserActivity> findByUserIdAndChapterId(String userId, Long chapterId) {
        return userActivityRepository.findByUserIdAndChapterId(userId, chapterId);
    }

    @Override
    public List<UserActivity> findByUserIdAndChapterIdAndParagraphId(String userId, Long chapterId, Long paragraphId) {
        return userActivityRepository.findByUserIdAndChapterIdAndParagraphId(userId, chapterId, paragraphId);
    }
}
