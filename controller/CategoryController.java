package com.inventory.controller;
import com.inventory.model.Category;
import com.inventory.repository.CategoryRepository;
import com.inventory.exception.ResourceNotFoundException;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;
import java.util.List;
@RestController @RequestMapping("/api/categories")
public class CategoryController {
 private final CategoryRepository repo; public CategoryController(CategoryRepository repo){this.repo=repo;}
 @GetMapping public List<Category> all(){return repo.findAll();}
 @GetMapping("/{id}") public Category get(@PathVariable Long id){return repo.findById(id).orElseThrow(()->new ResourceNotFoundException("Category not found: "+id));}
 @PostMapping public Category create(@Valid @RequestBody Category c){return repo.save(c);}
 @PutMapping("/{id}") public Category update(@PathVariable Long id,@Valid @RequestBody Category c){Category x=get(id);x.setName(c.getName());x.setDescription(c.getDescription());return repo.save(x);}
 @DeleteMapping("/{id}") public void delete(@PathVariable Long id){repo.delete(get(id));}
}
