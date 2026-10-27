package com.inventory.controller;
import com.inventory.model.Product;
import com.inventory.repository.ProductRepository;
import com.inventory.repository.CategoryRepository;
import com.inventory.repository.SupplierRepository;
import com.inventory.exception.ResourceNotFoundException;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;
import java.util.List;
@RestController @RequestMapping("/api/products")
public class ProductController {
 private final ProductRepository products; private final CategoryRepository categories; private final SupplierRepository suppliers;
 public ProductController(ProductRepository products,CategoryRepository categories,SupplierRepository suppliers){this.products=products;this.categories=categories;this.suppliers=suppliers;}
 @GetMapping public List<Product> all(@RequestParam(required=false) String search){return (search==null||search.isBlank())?products.findAll():products.findByNameContainingIgnoreCase(search);}
 @GetMapping("/{id}") public Product get(@PathVariable Long id){return products.findById(id).orElseThrow(()->new ResourceNotFoundException("Product not found: "+id));}
 @PostMapping public Product create(@Valid @RequestBody Product p){p.setId(null);attachReferences(p);return products.save(p);}
 @PutMapping("/{id}") public Product update(@PathVariable Long id,@Valid @RequestBody Product p){Product x=get(id);x.setSku(p.getSku());x.setName(p.getName());x.setDescription(p.getDescription());x.setUnitPrice(p.getUnitPrice());x.setQuantity(p.getQuantity());x.setReorderLevel(p.getReorderLevel());x.setCategory(p.getCategory());x.setSupplier(p.getSupplier());attachReferences(x);return products.save(x);}
 @DeleteMapping("/{id}") public void delete(@PathVariable Long id){products.delete(get(id));}
 @GetMapping("/low-stock") public List<Product> lowStock(){return products.findAll().stream().filter(p->p.getQuantity()<=p.getReorderLevel()).toList();}
 private void attachReferences(Product p){
   if(p.getCategory()!=null&&p.getCategory().getId()!=null)p.setCategory(categories.findById(p.getCategory().getId()).orElseThrow(()->new ResourceNotFoundException("Category not found")));
   if(p.getSupplier()!=null&&p.getSupplier().getId()!=null)p.setSupplier(suppliers.findById(p.getSupplier().getId()).orElseThrow(()->new ResourceNotFoundException("Supplier not found")));
 }
}
