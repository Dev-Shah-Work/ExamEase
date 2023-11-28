package project.practice.examease.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import project.practice.examease.entity.Subcategory;
import project.practice.examease.service.SubcategoryService;

import java.util.ArrayList;
import java.util.List;

@CrossOrigin
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/examease")
public class SubcategoryController {
    @Autowired
    private final SubcategoryService subcategoryService;

    @GetMapping("/subcategories")
    public ResponseEntity<List<Subcategory>> getSubcategories(){
        try{
           return subcategoryService.getSubcategories();
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return new ResponseEntity<>(new ArrayList<>(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
