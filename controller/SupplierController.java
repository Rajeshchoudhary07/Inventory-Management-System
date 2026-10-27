package com.inventory.controller;
import com.inventory.model.Supplier;
import com.inventory.repository.SupplierRepository;
import com.inventory.exception.ResourceNotFoundException;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;
import java.util.List;
@RestController @RequestMapping("/api/suppliers")
public class SupplierController {
 private final SupplierRepository repo; public SupplierController(SupplierRepository repo){this.repo=repo;}
 @GetMapping public List<Supplier> all(){return repo.findAll();}
 @GetMapping("/{id}") public Supplier get(@PathVariable Long id){return repo.findById(id).orElseThrow(()->new ResourceNotFoundException("Supplier not found: "+id));}
 @PostMapping public Supplier create(@Valid @RequestBody Supplier s){return repo.save(s);}
 @PutMapping("/{id}") public Supplier update(@PathVariable Long id,@Valid @RequestBody Supplier s){Supplier x=get(id);x.setName(s.getName());x.setEmail(s.getEmail());x.setPhone(s.getPhone());x.setAddress(s.getAddress());return repo.save(x);}
 @DeleteMapping("/{id}") public void delete(@PathVariable Long id){repo.delete(get(id));}
}
