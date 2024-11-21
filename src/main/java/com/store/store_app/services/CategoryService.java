package com.store.store_app.services;

import com.store.store_app.models.Category;
import com.store.store_app.repositories.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryService {

    private final CategoryRepository repo;

    @Autowired
    public CategoryService(CategoryRepository repo) {
        this.repo = repo;
    }
    public List<Category> listAll(){
        return repo.findAll();
    }

    public Optional<Category> getById(Long categoryId){

        return repo.findById(categoryId);
    }

    public void save(Category category){
        repo.save(category);
    }

    public void delete(Long categoryId){
        repo.deleteById(categoryId);
    }
}
