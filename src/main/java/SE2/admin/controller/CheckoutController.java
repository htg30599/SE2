//package SE2.admin.controller;
//
//
//import SE2.admin.model.Order;
//import SE2.admin.model.User;
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
//    @GetMapping("/checkout")
//    public String getAllItem(Model model){
//        CustomerUserDetail userDetails = (CustomerUserDetail) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//        String username = userDetails.getUsername();
//        User user = userRepository.findByUserName(username);
//        Optional<Order> orderOptional = orderRepository.findById();
//        if()
//        userDetails.getUserName();
//        return "";
//    }
//
//}
