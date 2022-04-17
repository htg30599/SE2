package SE2.admin.controller;

import SE2.admin.model.Product;
import SE2.admin.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;


@Controller
public class MainPageController {
    @Autowired (required = false)
    ProductRepository productRepository;

    @RequestMapping(value = "/")
    public String getAllProduct(Model model) {
        List<Product> product = productRepository.findAll();
        model.addAttribute("products", product);
        return "shop";

    }
    @RequestMapping(value = "/{id}")
    public String getProductById(
            @PathVariable(value = "id") Long id, Model model) {
        Product product = productRepository.getById(id);
        model.addAttribute("product", product);
        return "shop-detail";
    }
}
