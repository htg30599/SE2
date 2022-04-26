package SE2.admin.controller;

import SE2.admin.model.Cart;
import SE2.admin.model.Category;
import SE2.admin.repository.CartRepository;
import SE2.admin.repository.CategoryRepository;
import SE2.admin.repository.ProductRepository;
import SE2.admin.model.Product;
import SE2.admin.service.CustomerUserDetail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.context.SecurityContextHolder;
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
    CartRepository cartRepository;

    @Autowired
    CategoryRepository categoryRepository;

    @RequestMapping(value = "/list")
    public String showProductList(Model model) {
        List<Product> productList = productRepository.findAll();
        model.addAttribute("productList", productList);
        return "productList";
    }

    @RequestMapping(value = "/{id}")
    public String showProduct(
            @PathVariable(value = "id") Long id, Model model) {
        Product product = productRepository.getById(id);
        model.addAttribute("product", product);
        return "productInfo";
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
        List<Category> categories = categoryRepository.findAll();
        Product product = productRepository.getById(id);
        model.addAttribute("product", product);
        model.addAttribute("categories", categories);
        return "productUpdate";
    }

    @RequestMapping(value = "/delete/{id}")
    public String deleteProduct(
            @PathVariable(value = "id") Long id) {
        Product product = productRepository.getById(id);
        productRepository.delete(product);
        return "redirect:/admin/product/list";
    }

    @RequestMapping(value = "/save")
    public String saveUpdate(
            @RequestParam(value = "id", required = false) Long id,
            @Valid Product product, BindingResult result,
            Model model) {
        List<Category> categories = categoryRepository.findAll();
        model.addAttribute("categories", categories);
        if (result.hasErrors()) {
            if (id == null)
                return "productAdd";
            else
                return "productUpdate";
        }
        product.setId(id);
        productRepository.save(product);
        return "redirect:/admin/product/list";
    }

    @RequestMapping("/search")
    public String searchProduct(
            Model model,
            @RequestParam(value = "name") String name) {
        List<Product> products = productRepository.findByNameContaining(name);
        model.addAttribute("productList", products);
        return "productList";
    }

    @RequestMapping("/sort/asc")
    public String sortProduct(Model model) {
        List<Product> products = productRepository.findAll(Sort.by(Sort.Direction.ASC, "name"));
        model.addAttribute("productList", products);
        return "productList";
    }

    @RequestMapping("/sort/desc")
    public String sortProductDesc(Model model) {
        List<Product> products = productRepository.findAll(Sort.by(Sort.Direction.DESC, "name"));
        model.addAttribute("productList", products);
        return "productList";
    }

}

