package com.quizz.resourceservice.service.lesson;

import com.quizz.resourceservice.model.lesson.Category;
import com.quizz.resourceservice.repository.lesson.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;

    /**
     * This method to get belong to category
     *
     * @param
     * @return list category
     * @throws Exception
     */
    public List<Category> getCategoryList() {
        return categoryRepository.findAll();
    }
}
