
package SE2.admin.controller;

import SE2.admin.model.*;
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
    private UserRepository userRepository;

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
        Role role = roleRepository.getById(2);
        Set<Role> roleSet = new HashSet<>();
        roleSet.add(role);
        user.setRole(role);
        user.setImage("/images/icon.png");
        userRepository.save(user);

        return "register_success";
    }
    @GetMapping("/homepage")
    public String homepage(){
        return "homepage";
    }

    @GetMapping("/checkout")
    public String showCheckout(Model model) {
        return "checkout";
    }

    @RequestMapping("/profile/{id}")
    public String viewProfile(
            @PathVariable(value = "id") Long id, Model model) {
        User user = userRepository.getById(id);
        ChangePw changePw = new ChangePw("", "", "");
        model.addAttribute("user", user);
        model.addAttribute("changePw", changePw);
        return "profile";
    }

    @RequestMapping(name = "/profile/checkPass/{id}", method = RequestMethod.POST)
    public String isPasswordCorrect(@PathVariable(value = "id", required = false) Long id,
                                     @RequestBody ChangePw changePw,
                                     @Valid User user,
                                     Model model) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String enteredPassword = encoder.encode(changePw.getPassword());
        if (enteredPassword.equals(user.getPassword()) && changePw.getNewPassword().equals(changePw.getConfirmPassword())) {
            changePw.setPassword(encoder.encode(changePw.getNewPassword()));
        } else changePw = new ChangePw("", "", "");
        model.addAttribute("changePw", changePw);
        return "redirect:#";
    }

    @RequestMapping("/profile/save")
    public String saveProfile(@RequestParam(value = "id", required = false) Long id,
                              @Valid User user, BindingResult result,
                              Model model) {
        if (result.hasErrors()) {
            return "profile";
        }
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String encodedPassword = encoder.encode(user.getPassword());
        user.setPassword(encodedPassword);
        userRepository.save(user);
        return "profile";
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


