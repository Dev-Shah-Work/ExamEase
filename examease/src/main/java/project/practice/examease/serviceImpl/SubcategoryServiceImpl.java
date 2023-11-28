package project.practice.examease.serviceImpl;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import project.practice.examease.entity.Subcategory;
import project.practice.examease.repo.SubcategoryRepository;
import project.practice.examease.service.SubcategoryService;

import java.util.ArrayList;
import java.util.List;
@Service
@RequiredArgsConstructor
public class SubcategoryServiceImpl implements SubcategoryService {
    @Autowired
    private final SubcategoryRepository subcategoryRepository;
    @Override
    public ResponseEntity<List<Subcategory>> getSubcategories() {
        try{
            List<Subcategory> subcategories=subcategoryRepository.findAll();
            if(subcategories.isEmpty()){
                return new ResponseEntity<>(new ArrayList<>(),HttpStatus.NOT_FOUND);
            }
            return new ResponseEntity<>(subcategories,HttpStatus.OK);
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return new ResponseEntity<>(new ArrayList<>(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
