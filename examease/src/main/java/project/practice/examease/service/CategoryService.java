package project.practice.examease.service;

import org.springframework.http.ResponseEntity;
import project.practice.examease.entity.Category;

import java.util.List;

public interface CategoryService {
    Category findById(Integer id);

    ResponseEntity<List<Category>> getCategories();
}
