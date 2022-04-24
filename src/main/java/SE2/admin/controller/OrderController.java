package SE2.admin.controller;


import SE2.admin.model.Order;
import SE2.admin.repository.OrderRepository;
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
@RequestMapping(value ="/admin/order")
public class OrderController {
    @Autowired
    OrderRepository orderRepository;

    @RequestMapping(value="/list")
    public  String showOrderList(Model model){
        List<Order> orderList = orderRepository.findAll();
        model.addAttribute("orderList", orderList);
        return "orderList";
    }

    @RequestMapping(value = "/{id}")
    public String showOrder(@PathVariable(value ="id") Long id, Model model){
        Order order = (Order) orderRepository.getById(id);
        model.addAttribute("oder", order);
        return "orderInfo";

    }

    @RequestMapping(value = "/add")
    public String addOrder(Model model){
        Order order1 = new Order();
        model.addAttribute("order", order1);
        return "orderAdd";
    }

    @RequestMapping(value = "/update/{id}")
    public String updateOrder(
            @PathVariable(value = "id") Long id, Model model) {
        List<Order> orders = orderRepository.findAll();
        Order order = (Order) orderRepository.getById(id);
        model.addAttribute("order", order);

        return "orderUpdate";
    }

    @RequestMapping(value = "/delete/{id}")
    public String deleteOrder(
            @PathVariable(value = "id") Long id) {
        Order order = (Order) orderRepository.getById(id);
        orderRepository.delete(order);
        return "redirect:/admin/order/list";
    }

    @RequestMapping(value = "/save")
    public String saveUpdate(
            @RequestParam(value = "id", required = false) Long id,
            @Valid Order order, BindingResult result,
            Model model) {
        List<Order> order1 = orderRepository.findAll();
        model.addAttribute("order", order1);
        if (result.hasErrors()) {
            if (id == null)
                return "orderAdd";
            else
                return "orderUpdate";
        }
        order.setId(id);
        orderRepository.save(order);
        return "redirect:/admin/order/list";
    }

    @RequestMapping("/search")
    public String searchOrder(
            Model model,
            @RequestParam(value = "id") int id) {
        List<Order> orders = orderRepository.findAllById(id);
        model.addAttribute("orderList", orders);
        return "orderInfo";
    }

    @RequestMapping("/sort/asc")
    public String sortOrder(Model model) {
        List<Order> orders = orderRepository.findAll(Sort.by(Sort.Direction.ASC, "name"));
        model.addAttribute("orderList", orders);
        return "orderInfo";
    }

    @RequestMapping("/sort/desc")
    public String sortProductDesc(Model model) {
        List<Order> orders = orderRepository.findAll(Sort.by(Sort.Direction.DESC, "name"));
        model.addAttribute("orderList", orders);
        return "ordertList";
    }
}
