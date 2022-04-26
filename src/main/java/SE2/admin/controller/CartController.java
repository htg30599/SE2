package SE2.admin.controller;

import SE2.admin.CartUpdateDTO;
import SE2.admin.model.Cart;
import SE2.admin.model.EntityProduct;
import SE2.admin.model.Order;
import SE2.admin.model.Product;
import SE2.admin.repository.CartRepository;
import SE2.admin.repository.EntityProductRepository;
import SE2.admin.repository.ProductRepository;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.*;
import java.util.stream.Collectors;

@Controller
@RequestMapping(value = "/cart")
public class CartController {
    CartRepository cartRepository;

    ProductRepository productRepository;

    EntityProductRepository entityProductRepository;

    @Autowired
    public CartController(CartRepository cartRepository, ProductRepository productRepository, EntityProductRepository entityProductRepository) {
        this.cartRepository = cartRepository;
        this.productRepository = productRepository;
        this.entityProductRepository = entityProductRepository;
    }

    @RequestMapping(value = "/singleCart")
    public String showCartList(Model model) {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String userEmail = userDetails.getUsername();

        Cart cart = cartRepository.findByUserEmailAndStatusIs(userEmail, 0);
        model.addAttribute("cart", Optional.ofNullable(cart).orElse(new Cart()));
        return "cartList";
    }


    // Giờ  cần add cái api này co cái button addToCart onClick() trigger gọi tới thằng api này, ngồi search jquery hoặc ajax để call dưới dạng method PUT
    // checkout thì tự lấy cái cart đã có sẵn ra mà hiển thị, nếu User confirm tạo đơn thì tạo 1 cái Order, nhớ phải update thằng status của cart về '1'(int)

    @PutMapping(value = "/addToCart")
    public String addToCart(@RequestBody CartUpdateDTO cartUpdateDTO, Model model) {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String userEmail = userDetails.getUsername();

        Optional<Product> productOpt = productRepository.findById(cartUpdateDTO.getProductId());
        if (!productOpt.isPresent()) {
            model.addAttribute("err", "Product is not found");
            //Return HomePage to show err TODO
            return "";
        }

        Product product = productOpt.get();


        Cart cart = Optional.ofNullable(cartRepository.findByUserEmailAndStatusIs(userEmail, 0)).orElse(new Cart());
        EntityProduct newProductCart = new EntityProduct(product, cart, cartUpdateDTO.getQuantity());
        int totalPrice = 0;

        if (cart.getId() != null) {
            List<EntityProduct> entityProducts = entityProductRepository.findAllByCartIdIs(cart.getId());

            if (CollectionUtils.isNotEmpty(entityProducts)) {
                for (EntityProduct entityProduct : entityProducts) {
                    totalPrice += entityProduct.getProduct().getPrice() * entityProduct.getQuantity();
                }

                EntityProduct existedProductCart = entityProducts.stream().filter(entityProduct -> entityProduct.getProduct() == product).findFirst().orElse(null);
                if (existedProductCart != null) {
                    totalPrice = totalPrice - existedProductCart.getQuantity() * existedProductCart.getProduct().getPrice() + existedProductCart.getProduct().getPrice() * cartUpdateDTO.getQuantity();
                    existedProductCart.setQuantity(cartUpdateDTO.getQuantity());
                    entityProducts.add(newProductCart);
                    entityProductRepository.saveAll(entityProducts);
                } else {
                    totalPrice = totalPrice + product.getPrice() * cart.getTotalPrice();
                    entityProductRepository.save(newProductCart);
                }
            } else {
                entityProductRepository.save(newProductCart);
                totalPrice = product.getPrice() * cart.getTotalPrice();
            }

        } else {
            cart.setUserEmail(userEmail);
            cart.setEntityProducts(Collections.singletonList(newProductCart));
            cart.setStatus(0);
        }

        cart.setTotalPrice(totalPrice);
        cartRepository.save(cart);
        model.addAttribute("cart", cart);
        return "cartUpdate";
    }


    @RequestMapping("/search")
    public String searchCart(
            Model model,
            @RequestParam(value = "id") int id) {
        List<Cart> cart = cartRepository.findAllById(id);
        model.addAttribute("cartList", cart);
        return "cartInfo";
    }

    @RequestMapping("/sort/asc")
    public String sortCart(Model model) {
        List<Cart> carts = cartRepository.findAll(Sort.by(Sort.Direction.ASC, "name"));
        model.addAttribute("cartList", carts);
        return "cartInfo";
    }

    @RequestMapping("/sort/desc")
    public String sortCartDesc(Model model) {
        List<Cart> cart = cartRepository.findAll(Sort.by(Sort.Direction.DESC, "name"));
        model.addAttribute("cartList", cart);
        return "cartList";
    }


}
