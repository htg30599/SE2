
package SE2.admin.controller;

import SE2.admin.UpdateCartRequestDTO;
import SE2.admin.model.*;
import SE2.admin.repository.*;

import SE2.admin.service.CustomerUserDetail;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;


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

    @Autowired
    private OrderRepository orderRepository;

    @GetMapping("/register")
    public String showSignUpForm(Model model) {
        model.addAttribute("user", new User());
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
        for (Product product : products) {
            if (product.getQuantity() < 0)
                products.remove(product);
        }
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
            @ModelAttribute(name = "clientForm") ClientForm clientForm,
            Model model) {
        // functional method, consumer
        CustomerUserDetail userDetails = (CustomerUserDetail) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = userRepository.findByEmail(userDetails.getUserName());
        List<EntityProduct> entityProducts = entityProductsRepository.findAllByIdIn(clientForm.getEntityProducts().stream().map(EntityProduct::getId).collect(Collectors.toList()));

        Cart cart = cartRepository.findByUserEmailAndStatusIs(userDetails.getUserName(), 0);
        cart.setTotalPrice(0);
        if (CollectionUtils.isNotEmpty(entityProducts)) {
            entityProducts.forEach(entityProduct -> clientForm.getEntityProducts().stream().filter(entityProduct1 -> Objects.equals(entityProduct1.getId(), entityProduct.getId())).findFirst().ifPresent(result -> entityProduct.setQuantity(result.getQuantity())));
            for (EntityProduct entityProduct : entityProducts) {
                entityProductsRepository.save(entityProduct);
                cart.setTotalPrice(cart.getTotalPrice() + entityProduct.getQuantity() * entityProduct.getProduct().getPrice());
            }
            cartRepository.save(cart);
            List<EntityProduct> list = entityProductsRepository.findAllByCart(cart);
            for (int i = 0; i < list.size(); i++) {
                if (entityProducts.indexOf(list.get(i)) < 0) {
                    entityProductsRepository.delete(list.get(i));
                }
            }
        }
        Order order = new Order();
        order.setCart(cart);
        order.setPlaceOfReceipt(user.getAddress());
        order.setTotalPrice(cart.getTotalPrice());

        clientForm.setEntityProducts(entityProducts);

        model.addAttribute("user", user);
        model.addAttribute("order", order);
        model.addAttribute("categories", categoryRepository.findAll());
        model.addAttribute("clientForm", clientForm);
        return "checkout";
    }

    @RequestMapping(value = "/shop/saveOrder", method = RequestMethod.POST)
    public String saveOrder(
            @ModelAttribute(name = "order") Order order,
            Model model) {
        Cart cart = order.getCart();
        cart.setStatus(1);
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        order.setCreateDate(formatter.format(new Date()));
        order.setTotalPrice(cart.getTotalPrice());
        cartRepository.save(cart);
        List<EntityProduct> entityProducts = entityProductsRepository.findAllByCart(cart);

        for (EntityProduct entityProduct : entityProducts) {
            Product productItem = entityProduct.getProduct();
            Long productId = productItem.getId();
            Product product = productRepository.getById(productId);
            product.setQuantity(productRepository.getById(productId).getQuantity() - entityProduct.getQuantity());
            productRepository.save(product);
        }
        orderRepository.save(order);
        return "orderList";
    }

    @RequestMapping("/shop/showOrder")
    public String showCart(
            @ModelAttribute(name = "order") Order order,
            Model model) {
        String userEmail = order.getCart().getUserEmail();
        model.addAttribute("categories", categoryRepository.findAll());
        List<Cart> carts = cartRepository.findAllByUserEmail(userEmail);
        List<Order> orders = new ArrayList<>();
        for (Cart cart : carts) {
            orders.add(orderRepository.findByCart(cart));
        }
        model.addAttribute("orders", orders);
        return "orderList";
    }

}


