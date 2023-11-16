package project.practice.examease.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.web.bind.annotation.*;
import project.practice.examease.dto.AppUserDto;
import project.practice.examease.entity.AppUser;
import project.practice.examease.entity.Category;
import project.practice.examease.service.AppUserService;
import project.practice.examease.service.CategoryService;
import project.practice.examease.util.ResponseUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@CrossOrigin
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/examease")
public class CategoryController {

    @Autowired
    private final CategoryService categoryService;

    @GetMapping("/categories")
    public ResponseEntity<List<Category>> getCategories() {
        try{
            return categoryService.getCategories();
        }
        catch (Exception ex){
            ex.printStackTrace();
        }
        return new ResponseEntity<>(new ArrayList<>(),HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @GetMapping("/categories/{id}")
    public ResponseEntity<Category> findById(@PathVariable("id") Integer id) {
        try {
            Category result = categoryService.findById(id);
            return new ResponseEntity<>(result, HttpStatus.OK);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return new ResponseEntity<>(new Category(), HttpStatus.INTERNAL_SERVER_ERROR);

    }
}
