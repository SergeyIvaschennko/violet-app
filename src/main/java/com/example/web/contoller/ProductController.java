package com.example.web.contoller;
import com.example.web.dto.ProductDto;
import com.example.web.models.Product;
import com.example.web.models.UserEntity;
import com.example.web.security.SecurityUtil;
import com.example.web.service.ProductService;
import com.example.web.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;
import java.util.List;

@Controller
public class ProductController {
    private ProductService productService;
    private UserService userService;

    @Autowired
    public ProductController(ProductService productService, UserService userService) {
        this.productService = productService;
        this.userService = userService;
    }

    @GetMapping("/mc")
    public String foradmin(Model model) {
        return "foradmin";
    }

    @GetMapping("/products")
    public String listProducts(Model model) {
        UserEntity user = new UserEntity();
        List<ProductDto> products = productService.findAllProducts();
        String username = SecurityUtil.getSessionUser();
        if(username != null) {
            user = userService.findByUsername(username);
            model.addAttribute("user", user);
        }
        model.addAttribute("user", user);
        model.addAttribute("products", products);
        return "products-list";
    }

    @GetMapping("/products/new")
    public String createProductForm(Model model) {
        Product product = new Product();
        model.addAttribute("product", product);
        return "products-create";
    }

    @PostMapping("/products/new")
    public String saveProduct(@Valid @ModelAttribute("product") ProductDto productDto, BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("product", productDto);
            return "products-create";
        }
        productService.saveProduct(productDto);
        return "redirect:/products";
    }

    @GetMapping("/products/{productId}/delete")
    public String deleteClub(@PathVariable("productId")Long productId) {
        productService.delete(productId);
        return "redirect:/products";
    }

    @GetMapping("/products/{productId}")
    public String productDetail(@PathVariable("productId") long productId, Model model) {
        UserEntity user = new UserEntity();
        ProductDto productDto = productService.findProductById(productId);
        String username = SecurityUtil.getSessionUser();
        if(username != null) {
            user = userService.findByUsername(username);
            model.addAttribute("user", user);
        }

        if (!user.getId().equals(productDto.getCreatedBy().getId())) {
            // Обработка случая, когда пользователь не является владельцем продукта
            model.addAttribute("error", "You don't have permission to view this product.");
            return "redirect:/products";
        }
        model.addAttribute("user", user);
        model.addAttribute("product", productDto);
        return "products-detail";
    }

    @GetMapping("/products/{productId}/edit")
    public String editProductForm(@PathVariable("productId") long productId, Model model) {
        ProductDto product = productService.findProductById(productId);
        model.addAttribute("product", product);
        return "products-edit";
    }

    @PostMapping("/products/{productId}/edit")
    public String updateProduct(@PathVariable("productId") Long productId,
                                @Valid @ModelAttribute("product") ProductDto product,
                                BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("product", product);
            return "products-edit";
        }
        product.setId(productId);
        productService.updateProducts(product);
        return "redirect:/products";
    }
}
