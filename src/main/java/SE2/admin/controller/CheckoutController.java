//package SE2.admin.controller;
//
//
//import SE2.admin.model.Cart;
//import SE2.admin.model.Order;
//import SE2.admin.model.Product;
//import SE2.admin.model.User;
//import SE2.admin.repository.CartRepository;
//import SE2.admin.repository.OrderRepository;
//import SE2.admin.repository.ProductRepository;
//import SE2.admin.repository.UserRepository;
//import SE2.admin.service.CustomerUserDetail;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.GetMapping;
//
//import java.util.List;
//import java.util.Optional;
//
//public class CheckoutController {
//
//    @Autowired
//    ProductRepository productRepository;
//
//    @Autowired
//    UserRepository userRepository;
//
//    @Autowired
//    OrderRepository orderRepository;
//
//    @Autowired
//    UserDetailsService userDetailsService;
//
//    @Autowired
//    CartRepository cartRepository;
//
//    @GetMapping("/checkout")
//    public String getAllItem(Model model) {
//        CustomerUserDetail userDetails = (CustomerUserDetail) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//        String userEmail = userDetails.getUserName();
//        User user = userRepository.findByEmail(userEmail);
//        Optional<Cart> cartOptional = cartRepository.findByUserEmail(user.getEmail());
//        List<Product> productList = null;
//        if (cartOptional.isPresent()) {
//            Cart cart = cartOptional.get();
//            productList = cart.getProductList();
////            int totalPrice =0;
////            for (int i = 0; i < productList.size(); i++) {
////                totalPrice+=productList.get(i).getPrice()*
////            }
//        }
//        model.addAttribute("products", productList);
//        return "checkout";
//    }
//
//}
