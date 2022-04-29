
package SE2.admin.controller;

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

    @RequestMapping("/shop/profile")
    public String viewProfile(Model model) {
        CustomerUserDetail userDetails = (CustomerUserDetail) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = userRepository.findByEmail(userDetails.getUserName());
        model.addAttribute("user", user);
        return "profile";
    }

    @RequestMapping("/shop/profile/save")
    public String saveProfile(@RequestParam(value = "id", required = false) Long id,
                              @Valid User user, BindingResult result,
                              Model model) {
        if (result.hasErrors()) {
            return "profile";
        }
       user.setPassword(userRepository.getById(id).getPassword());
        userRepository.save(user);
        List<Product>products= productRepository.findAll();
        for (Product product : products) {
            if (product.getQuantity() < 0)
                products.remove(product);
        }
        model.addAttribute("products", products);
        model.addAttribute("categories", categoryRepository.findAll());
        return "shop";
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

        model.addAttribute("categories", categoryRepository.findAll());
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
        List<Cart> carts = cartRepository.findByUserEmailAndStatusIs(email, 0);
        List<EntityProduct> entityProducts;
        Cart cart;
        //create new cart if it's null
        if (carts == null||carts.size()==0) {
             cart= new Cart(email, product.getPrice(), 0);
            cartRepository.save(cart);
            entityProducts = new ArrayList<>();
            EntityProduct entityProduct = new EntityProduct(product, cart, 1);
            entityProducts.add(entityProduct);
            entityProductsRepository.save(entityProduct);
        } else {
            //calculate new total Price
            carts.get(0).setTotalPrice(carts.get(0).getTotalPrice() + product.getPrice());
            entityProducts = entityProductsRepository.findAllByCart(carts.get(0));
            EntityProduct entityProduct = new EntityProduct(product, carts.get(0), 1);
            entityProducts.add(entityProduct);
            entityProductsRepository.save(entityProduct);
            cart = carts.get(0);
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

        List<Cart> carts = cartRepository.findByUserEmailAndStatusIs(userDetails.getUserName(), 0);
        carts.get(0).setTotalPrice(0);

        if (CollectionUtils.isNotEmpty(entityProducts)) {
            entityProducts.forEach(entityProduct ->
                    clientForm.getEntityProducts()
                            .stream().filter(entityProduct1 ->
                                    Objects.equals(entityProduct1.getId(), entityProduct.getId()))
                            .findFirst().ifPresent(result -> entityProduct.setQuantity(result.getQuantity())));

            for (EntityProduct entityProduct : entityProducts) {
                entityProductsRepository.save(entityProduct);
                carts.get(0).setTotalPrice(carts.get(0).getTotalPrice() + entityProduct.getQuantity() * entityProduct.getProduct().getPrice());
            }
            cartRepository.save(carts.get(0));
            List<EntityProduct> list = entityProductsRepository.findAllByCart(carts.get(0));
            for (int i = 0; i < list.size(); i++) {
                if (entityProducts.indexOf(list.get(i)) < 0||list.get(i).getQuantity()==0) {
                    entityProductsRepository.delete(list.get(i));
                }
            }
        }
        Order order = new Order();
        order.setCart(carts.get(0));
        order.setPlaceOfReceipt(user.getAddress());
        order.setTotalPrice(carts.get(0).getTotalPrice());

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
        CustomerUserDetail userDetails = (CustomerUserDetail) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
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
        List<Cart> carts = cartRepository.findAllByUserEmail(userDetails.getUserName());
        List<Order> orders = new ArrayList<>();

        for (Cart cartItem : carts) {
            orders.add(orderRepository.findByCart(cartItem));
        }

        model.addAttribute("categories", categoryRepository.findAll());
        model.addAttribute("orders", orders);
        return "orderList";
    }

    @RequestMapping(value = "/shop/showOrder")
    public String showOrder(Model model) {
        CustomerUserDetail userDetails = (CustomerUserDetail) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String email = userDetails.getUserName();
        model.addAttribute("categories", categoryRepository.findAll());
        List<Cart> carts = cartRepository.findByUserEmailAndStatusIs(email,1);
        List<Order> orders = new ArrayList<>();

        for (Cart cart : carts) {
            orders.add(orderRepository.findByCart(cart));
        }

        model.addAttribute("categories", categoryRepository.findAll());
        model.addAttribute("orders", orders);
        return "orderList";
    }

    @RequestMapping(value = "/shop/showCart")
    public String showCart(Model model) {
        CustomerUserDetail userDetails = (CustomerUserDetail) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String email = userDetails.getUserName();
        List<Cart> carts = cartRepository.findByUserEmailAndStatusIs(email, 0);
        List<EntityProduct> entityProducts = entityProductsRepository.findAllByCart(carts.get(0));
        ClientForm clientForm = new ClientForm(entityProducts);

        model.addAttribute("clientForm", clientForm);
        model.addAttribute("categories", categoryRepository.findAll());
        model.addAttribute("cart", carts.get(0));
        return "cart";
    }

}


