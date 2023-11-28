package project.practice.examease.service;

import org.springframework.http.ResponseEntity;
import project.practice.examease.entity.Subcategory;

import java.util.List;

public interface SubcategoryService {
    ResponseEntity<List<Subcategory>> getSubcategories();
}
