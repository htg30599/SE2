package SE2.customer.controller;

import SE2.admin.model.User;
import SE2.admin.repository.UserRepository;
import SE2.customer.model.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import SE2.customer.repository.OrderRepository;

import java.util.List;

@Controller
public class OrderController {
    @Autowired
    OrderRepository orderRepository;


    @RequestMapping(value= "/order")
    public String showOrderList(Model model){
        List<Order> order = orderRepository.findAll();
        model.addAttribute("order", order);
        return "orderList";
    }
    @RequestMapping(value= "/{id}")
    public String getOrderById(
            @PathVariable(value = "id") Long id, Model model) {
        Order order= orderRepository.getById(id);
        model.addAttribute("order", order);
        return "orderDetail";
    }
    @RequestMapping(value= "/add")
    public String addOrder (Long id, Model model) {
        Order order = new Order();
        model.addAttribute("order", order);
        return "orderAdd";
    }
    @RequestMapping(value = "/update/{id}")
    public String updateOrder(
            @PathVariable (value = "id") Long id, Model model) {
        Order order = orderRepository.getById(id);
        model.addAttribute(order);
        return "orderUpdate";
    }
    @RequestMapping(value = "/save")
    public String saveUpdate(
            @RequestParam(value="id", required = false) Long id, Order order) {
        order.setId(id);
        orderRepository.save(order);
        return "redirect:/";
    }
    @RequestMapping(value ="/delete/{id}")
    public String deleteOrder(
            @PathVariable(value = "id") Long id){
        Order order = orderRepository.getById(id);
        orderRepository.delete(order);
        return "redirect:/";
    }



}
