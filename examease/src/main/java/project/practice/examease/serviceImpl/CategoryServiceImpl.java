package project.practice.examease.serviceImpl;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import project.practice.examease.entity.Category;
import project.practice.examease.entity.Subcategory;
import project.practice.examease.repo.CategoryRepository;
import project.practice.examease.service.CategoryService;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {
    @Autowired
    CategoryRepository categoryRepository;

    @Override
    public Category findById(Integer id) {
        try {
            if (id != null && categoryRepository.findById(id).isPresent()) {
                Category category = categoryRepository.findById(id).get();
                System.out.println(category);
                List<Subcategory> subcategories=category.getSubcategories();
                for(Subcategory s:subcategories){
                    System.out.println(s);
                }
                return category;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
//        return new ResponseEntity<>(new Category(), HttpStatus.INTERNAL_SERVER_ERROR);
        return new Category();
    }

    @Override
    public ResponseEntity<List<Category>> getCategories() {
        try {
            if (categoryRepository.findAll() != null) {
                List<Category> categories = categoryRepository.findAll();
                return new ResponseEntity<>(categories, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(new ArrayList<>(), HttpStatus.NOT_FOUND);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return new ResponseEntity<>(new ArrayList<>(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
