package SE2.user.controller;

import SE2.user.model.Product;
import SE2.user.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping(value = "/product")
public class ProductController {
    @Autowired
    ProductRepository productRepository;

    @RequestMapping("/search_result")
    public String showSearchResult(Model model,
                                   @RequestParam(value = "name") String name) {
        List<Product> products = productRepository.findByNameContainingIgnoreCase(name);
        model.addAttribute("products", products);
        return "shop";
    }

    @RequestMapping("/detail/{id}")
    public String productDetail(Model model,
                                @PathVariable(value = "id") Long id) {
        Product product = productRepository.getById(id);
        model.addAttribute("product", product);
        return "shop-detail";
    }
}
