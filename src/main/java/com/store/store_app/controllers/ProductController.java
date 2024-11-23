package com.store.store_app.controllers;

import com.store.store_app.models.Category;
import com.store.store_app.specifications.ProductSpecifications;
import com.store.store_app.services.CategoryService;
import com.store.store_app.services.ProductService;
import com.store.store_app.models.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/products")
public class ProductController {

    private  final CategoryService cService;

    private final ProductService pService;

    @Autowired
    public ProductController(CategoryService cService, ProductService pService) {
        this.cService = cService;
        this.pService = pService;
    }

    @GetMapping
    public String listAll(@RequestParam(defaultValue = "0") int page,
                          @RequestParam(defaultValue = "10") int size,
                          @RequestParam(required = false) String name,
                          @RequestParam(required = false) String category,
                          @RequestParam(required = false) Float maxPrice,
                          Model model) {

        final Pageable pageable = PageRequest.of(page, size);

        Specification<Product> spec = Specification.where(null);

        if (name != null && !name.isEmpty()) {
            spec = spec.and(ProductSpecifications.matchesName(name));
        }
        if (category != null && !category.isEmpty()) {
            spec = spec.and(ProductSpecifications.hasCategory(category));
        }
        if (maxPrice != null && maxPrice > 0) {
            spec = spec.and(ProductSpecifications.lessThanOrEqualTo(maxPrice));
        }

        Page<Product> productsPage = pService.listBySpecification(spec, pageable);

        model.addAttribute("products", productsPage.getContent());
        model.addAttribute("currentPage", productsPage.getNumber());
        model.addAttribute("totalPages", productsPage.getTotalPages());
        model.addAttribute("pageSize", size);
        model.addAttribute("categories", cService.listAll());
        model.addAttribute("name", name);
        model.addAttribute("category", category);
        model.addAttribute("maxPrice", maxPrice);

        return "productsList";
    }


    @GetMapping("/form")
    public String editProduct(@RequestParam(value = "id", required = false) Long id, Model model){
        List<Category> categories = cService.listAll();

        if(id == null){
            model.addAttribute("categories", categories);

            return "productsForm";

        }

       Product product =  pService.getById(id);


       model.addAttribute("product", product);

       model.addAttribute("categories", categories);

       return "productsForm";

    }

    @PostMapping("/save-product")
    public String saveProduct(@ModelAttribute Product product){

        pService.save(product);

        return "redirect:/products";

    }

    @GetMapping("/delete")
    public String deleteProduct(@RequestParam(value = "id", required = true) Long id){

        pService.delete(id);

        return "redirect:/products";
    }

}
