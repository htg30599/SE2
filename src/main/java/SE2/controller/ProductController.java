package SE2.controller;

import SE2.repository.ProductRepository;
import SE2.model.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;
import java.util.List;

@Controller
public class ProductController {
    @Autowired
    ProductRepository productRepository;

    @RequestMapping(value = "/product")
    public String showProductList(Model model) {
        List<Product> productList = productRepository.findAll();
        model.addAttribute("products", productList);
        return "productList";
    }

    @RequestMapping(value = "/add")
    public String addProduct(Model model) {
        Product product = new Product();
        model.addAttribute("product", product);
        return "productAdd";
    }

    @RequestMapping(value = "/update/{id}")
    public String updateProduct(
            @PathVariable(value = "id") Long id, Model model) {
        Product product = productRepository.getById(id);
        model.addAttribute("product", product);
        return "productUpdate";
    }

    @RequestMapping(value = "save")
    public String saveUpdate(
            @RequestParam(value = "id", required = false) Long id,
            @Valid Product product, BindingResult result) {
        if (result.hasErrors()){
            if (id == null)
                return "productAdd";
            else
                return "productUpdate";
        }
        product.setId(id);
        productRepository.save(product);
        return "redirect:/product/list";
    }

}

