package SE2.admin.controller;

import SE2.admin.model.Category;
import SE2.admin.repository.CategoryRepository;
import SE2.admin.repository.ProductRepository;
import SE2.admin.model.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping(value = "/admin/product")
public class ProductController {
    @Autowired
    ProductRepository productRepository;
    @Autowired
    CategoryRepository categoryRepository;

    @RequestMapping(value = "/list")
    public String showProductList(Model model) {
        List<Product> productList = productRepository.findAll();
        model.addAttribute("products", productList);
        return "productList";
    }

    @RequestMapping(value = "/add")
    public String addProduct(Model model) {
        List<Category> categories = categoryRepository.findAll();
        Product product = new Product();
        model.addAttribute("product", product);
        model.addAttribute("categories", categories);
        return "productAdd";
    }

    @RequestMapping(value = "/update/{id}")
    public String updateProduct(
            @PathVariable(value = "id") Long id, Model model) {
        Product product = productRepository.getById(id);
        model.addAttribute("product", product);
        return "productUpdate";
    }

    @RequestMapping(value = "/save")
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

    @RequestMapping("/search")
    public String searchProduct(
            Model model,
            @RequestParam(value = "name") String name) {
        List<Product> products = productRepository.findByNameContaining(name);
        model.addAttribute("products", products);
        return "productList";
    }

    @RequestMapping("/sort/asc")
    public String sortProduct(Model model) {
        List<Product> products =productRepository.findAll(Sort.by(Sort.Direction.ASC,"name"));
        model.addAttribute("products", products);
        return "productList";
    }

    @RequestMapping("/sort/desc")
    public String sortProductDesc(Model model) {
        List<Product> products =productRepository.findAll(Sort.by(Sort.Direction.DESC,"name"));
        model.addAttribute("products", products);
        return "productList";
    }

}

