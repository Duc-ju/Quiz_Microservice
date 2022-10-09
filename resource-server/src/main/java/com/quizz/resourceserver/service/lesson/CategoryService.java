package com.quizz.resourceserver.service.lesson;

import com.quizz.resourceserver.model.lesson.Category;
import com.quizz.resourceserver.repository.lesson.CategoryRepository;
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
