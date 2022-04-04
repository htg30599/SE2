package SE2.controller;

import SE2.model.Product;
import SE2.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@Controller

public class MainPageController {
    @Autowired
    ProductRepository productRepository;

    @GetMapping (value = "/shop.html")
    public String getAllProduct(Model model) {
        List<Product> products = productRepository.findAll();
        model.addAttribute("shop", products);
        return "shop";
    }
}
