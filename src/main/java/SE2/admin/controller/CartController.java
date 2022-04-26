package SE2.admin.controller;

import SE2.admin.model.Cart;
import SE2.admin.model.Order;
import SE2.admin.repository.CartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping(value="/cart")
public class CartController {
    @Autowired
    CartRepository cartRepository;

    @RequestMapping(value ="/list")
    public String showCartList(Model model){
        List<Cart> cartList = cartRepository.findAll();
        model.addAttribute("cartList", cartList);
        return "cartList";
    }

    @RequestMapping(value="/{id}")
    public String showCart(@PathVariable(value = "id") Long id, Model model){
        Cart cart = cartRepository.getById(id);
        model.addAttribute("cart", cart);
        return "cartInfo";
    }

    @RequestMapping(value="/update/{id}")
    public String updateCart(@PathVariable(value ="id") Long id, Model model){
        List<Cart> carts = cartRepository.findAll();
        Cart cart = cartRepository.getById(id);
        model.addAttribute("cart", cart);
        return "cartUpdate";
    }
    @RequestMapping(value = "/delete/{id}")
    public String deleteCart(
            @PathVariable(value = "id") Long id) {
        Cart cart =  cartRepository.getById(id);
        cartRepository.delete(cart);
        return "redirect:/admin/cart/list";
    }

    @RequestMapping(value = "/save")
    public String saveUpdate(
            @RequestParam(value = "id", required = false) Long id,
            @Valid Cart cart, BindingResult result,
            Model model) {
        List<Cart> carts = cartRepository.findAll();
        model.addAttribute("carts", carts);
        if (result.hasErrors()) {
            if (id == null)
                return "cartAdd";
            else
                return "cartUpdate";
        }
        cart.setId(id);
        cartRepository.save(cart);
        return "redirect:/admin/cart/list";
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
