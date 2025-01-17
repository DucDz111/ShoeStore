package app.controller;

import app.entity.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import app.service.ProductService;

@Controller
public class ProductController {
    @Autowired
    private ProductService productService;
    @GetMapping("/product")
    public String product(@RequestParam(defaultValue = "0") int page,
                          @RequestParam(defaultValue = "12") int size, Model model ) {
    Page<Product> productPage = productService.getProduct(page, size);
    model.addAttribute("product", productPage.getContent());
    model.addAttribute("currentPage", page);
    model.addAttribute("totalPages", productPage.getTotalPages());

        return "product";
    }

}
