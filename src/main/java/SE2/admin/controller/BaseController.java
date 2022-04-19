
package SE2.admin.controller;

import SE2.admin.model.Category;
import SE2.admin.model.Product;
import SE2.admin.model.Role;
import SE2.admin.model.User;
import SE2.admin.repository.CategoryRepository;
import SE2.admin.repository.ProductRepository;
import SE2.admin.repository.RoleRepository;
import SE2.admin.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


@Controller
public class BaseController {

    // Không dùng trực tiếp repository ở controller mà tạo service ra
    @Autowired
    private UserRepository repo;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ProductRepository productRepository;


//    @GetMapping("/Home")
//    public String index() {
//
//        return index();
//    }

    @GetMapping("/register")
    public String showSignUpForm(Model model) {
//        List<Roles> rolesList = rolesRepository.findAll();
        model.addAttribute("user", new User());
//        model.addAttribute("roles",rolesList);
        return "signup_form";
    }

    @RequestMapping(value = "/process_register", method = RequestMethod.POST)
    public String processRegistration(User user) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String encodedPassword = encoder.encode(user.getPassword());
        user.setPassword(encodedPassword);
        Role role = roleRepository.getById(1);
        Set<Role> roleSet = new HashSet<>();
        roleSet.add(role);
        user.setRole(role);
        repo.save(user);

        return "register_success";
    }

    @GetMapping("/checkout")
    public String showCheckout(Model model) {
        return "checkout";
    }

    @RequestMapping("/profile/{id}")
    public String viewProfile(
            @PathVariable(value = "id") Long id, Model model) {
        User user = repo.getById(id);
        model.addAttribute("user", user);
        return "profile";
    }

    @RequestMapping("/profile/save")
    public String saveProfile(@RequestParam(value = "id", required = false) Long id,
                              @Valid User user, BindingResult result,
                              Model model) {
        if (result.hasErrors()) {
            return "profile";
        }
        repo.save(user);
        return "redirect:/";
    }

    @RequestMapping("/category/{id}")
    public String showProducts(
            @PathVariable(value = "id") Long id, Model model) {
        Category category = categoryRepository.getById(id);
        List<Product> products = productRepository.findByCategory(category);
        model.addAttribute("products", products);
        return "shop";
    }

    @RequestMapping(value = "/product/{id}")
    public String showProduct(
            @PathVariable(value = "id") Long id, Model model) {
        Product product = productRepository.getById(id);
        model.addAttribute("product", product);
        return "userProductInfo";
    }
  /*  @RequestMapping(value = "/login")
    public String loginTemplate() {
        return "login";
    }

    @RequestMapping(value = "/")
    public String AdminHomepage() {
        return "AdminHomepage";
    }*/

    /*@RequestMapping(value = "/login", method = RequestMethod.POST)
    public String login(String userName) {
        CustomerUserDetailsService service = new CustomerUserDetailsService();
        service.loadUserByUsername(userName);
        *//*if (repo.findByEmail(userName).getRoles().getId() == 1)*//*
            return "redirect:/";*/

}


