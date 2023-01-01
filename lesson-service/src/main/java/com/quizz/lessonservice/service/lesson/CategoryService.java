package com.quizz.lessonservice.service.lesson;

import com.quizz.lessonservice.model.lesson.Category;
import com.quizz.lessonservice.model.lesson.Lesson;
import com.quizz.lessonservice.repository.lesson.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;
    private final LessonService lessonService;

    /**
     * This method to get belong to category
     *
     * @param
     * @return list category
     * @throws Exception
     */
    public List<Category> getCategoryList() {
        List<Category> categories = categoryRepository.findAll();
        for (Category category : categories) {
            for (Lesson lesson : category.getLessons()) {
                lesson.setNumberOfPlayed(lessonService.getLessonPlayedCount(lesson.getId()));
            }
        }
        return categories;
    }

    public Category addCategory(Category category) {
        return categoryRepository.save(category);
    }

    public List<Category> addCategories(List<Category> categories) {
        return categoryRepository.saveAll(categories);
    }
}
