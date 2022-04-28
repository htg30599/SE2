
package SE2.admin.controller;

import SE2.admin.model.*;
import SE2.admin.repository.*;

import SE2.admin.service.CustomerUserDetail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.*;


@Controller
public class BaseController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    CartRepository cartRepository;

    @Autowired
    EntityProductsRepository entityProductsRepository;

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
    public String homepage() {
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

//    @RequestMapping("/shop/addToCart/{id}")
//    public String showCart(
//            @PathVariable(value = "id") Long id, Model model) {
//        Product product = productRepository.getById(id);
//        CustomerUserDetail userDetails = (CustomerUserDetail) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//        String email = userDetails.getUserName();
//        Cart cart = cartRepository.findByUserEmailAndStatusIs(email, 0);
//        List<EntityProduct> entityProducts = entityProductsRepository.findAllByCart(cart);
//        entityProducts.add(new EntityProduct(product, cart, 1));
//        model.addAttribute("product",product);
//        model.addAttribute("entityProducts", entityProducts);
//        model.addAttribute("cart", cart);
//        return "cart";
//    }

    @RequestMapping(value = "/shop/addToCart/{id}")
    public String addProduct(
            @PathVariable(value = "id") Long id, Model model) {

        CustomerUserDetail userDetails = (CustomerUserDetail) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Product product = productRepository.getById(id);
        String email = userDetails.getUserName();
        Cart cart = cartRepository.findByUserEmailAndStatusIs(email, 0);
        List<EntityProduct> entityProducts;

        //create new cart if it's null
        if (cart == null) {
            cart = new Cart(email, product.getPrice(), 0);
            cartRepository.save(cart);
            entityProducts = new ArrayList<>();
            EntityProduct entityProduct = new EntityProduct(product, cart, 1);
            entityProducts.add(entityProduct);
            entityProductsRepository.save(entityProduct);
        } else {
            //calculate new total Price
            cart.setTotalPrice(cart.getTotalPrice() + product.getPrice());
            entityProducts = entityProductsRepository.findAllByCart(cart);
            EntityProduct entityProduct = new EntityProduct(product, cart, 1);
            entityProducts.add(entityProduct);
            entityProductsRepository.save(entityProduct);
        }
        //create ClientForm to save user input
        ClientForm clientForm = new ClientForm(entityProducts);

        //send categories to header
        model.addAttribute("categories", categoryRepository.findAll());
        model.addAttribute("clientForm", clientForm);
        model.addAttribute("cart", cart);
        return "cart";
    }

    @RequestMapping(value = "/shop/addToCart/save", method = RequestMethod.POST)
    public String saveCart(
            @ModelAttribute ClientForm clientForm,
            Model model) {
        CustomerUserDetail userDetails = (CustomerUserDetail) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        List<EntityProduct> entityProducts = clientForm.getEntityProducts();

        Cart cart = cartRepository.findByUserEmailAndStatusIs(userDetails.getUserName(), 0);
        cart.setTotalPrice(0);
        if (entityProducts != null) {
            for (int i = 0; i < entityProducts.size(); i++) {
                entityProductsRepository.save(entityProducts.get(i));
                cart.setTotalPrice(cart.getTotalPrice() + entityProducts.get(i).getQuantity() * entityProducts.get(i).getProduct().getPrice());
            }
            cartRepository.save(cart);
        }
        model.addAttribute("clientForm", clientForm);
        model.addAttribute("cart", cart);
        return "checkout";
    }

}


