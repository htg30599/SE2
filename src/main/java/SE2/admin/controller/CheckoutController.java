package SE2.admin.controller;


import SE2.admin.model.Cart;

import SE2.admin.model.EntityProduct;
import SE2.admin.model.Product;
import SE2.admin.model.User;
import SE2.admin.repository.*;
import SE2.admin.service.CustomerUserDetail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;
import java.util.Optional;

public class CheckoutController {

    @Autowired
    ProductRepository productRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    OrderRepository orderRepository;

    @Autowired
    UserDetailsService userDetailsService;

    @Autowired
    CartRepository cartRepository;

    @Autowired
    EntityProductRepository entityProductRepository;

    @Autowired
    CategoryRepository categoryRepository;

    @GetMapping("/checkout")
    public String getAllItem(Model model) {
        CustomerUserDetail userDetails = (CustomerUserDetail) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String userEmail = userDetails.getUserName();
        User user = userRepository.findByEmail(userEmail);
        Optional<Cart> cartOptional = Optional.ofNullable(cartRepository.findByUserEmail(user.getEmail()));
        List<EntityProduct> productList = null;
        if (cartOptional.isPresent()) {
            Cart cart = cartOptional.get();
//            productList = cart.getEntityProducts();
            productList= entityProductRepository.findAllByCartIdIs(cart.getId());
//            int totalPrice =0;
//            for (int i = 0; i < productList.size(); i++) {
//                totalPrice+=productList.get(i).getPrice()*
//            }
        }
        model.addAttribute("cartegories",categoryRepository.findAll());
        model.addAttribute("products", productList);
        return "checkout";
    }

}
