package SE2.admin.controller;

import SE2.admin.model.Order;
import SE2.admin.model.Role;
import SE2.admin.model.User;
import SE2.admin.repository.OrderRepository;
import SE2.admin.repository.RoleRepository;
import SE2.admin.repository.UserRepository;
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
@RequestMapping(value = "/admin/user")
public class UserController {
    @Autowired
    UserRepository userRepository;
    @Autowired
    RoleRepository roleRepository;
    @Autowired
    OrderRepository orderRepository;

    @RequestMapping(value = "/list")
    public String showProductList(Model model) {
        List<User> users = userRepository.findAll();
        model.addAttribute("users", users);
        return "userList";
    }

    @RequestMapping(value = "/{id}")
    public String showProduct(
            @PathVariable(value = "id") Long id, Model model) {
        User user = userRepository.getById(id);
        model.addAttribute("user", user);
        return "profile";
    }

    @RequestMapping(value = "/update/{id}")
    public String updateProduct(
            @PathVariable(value = "id") Long id, Model model) {
        List<Role> roles = roleRepository.findAll();
        User user = userRepository.getById(id);
        model.addAttribute("user", user);
        model.addAttribute("roles", roles);
        return "userUpdate";
    }

    @RequestMapping(value = "/delete/{id}")
    public String deleteProduct(
            @PathVariable(value = "id") Long id) {
        User user = userRepository.getById(id);
        userRepository.delete(user);
        return "redirect:/admin/user/list";
    }

    @RequestMapping(value = "/save")
    public String saveUpdate(
            @RequestParam(value = "id", required = false) Long id,
            @Valid User user, BindingResult result,
            Model model) {
        List<Role> roles = roleRepository.findAll();
        model.addAttribute("roles", roles);
        if (result.hasErrors()) {
            if (id == null)
                return "userAdd";
            else
                return "userUpdate";
        }
        user.setId(id);
        userRepository.save(user);
        Order order = new Order();
        orderRepository.save(order);
        return "redirect:/admin/user/list";
    }

    @RequestMapping("/search")
    public String searchProduct(
            Model model,
            @RequestParam(value = "name") String name) {
        List<User> users = userRepository.findByUserNameContaining(name);
        model.addAttribute("users", users);
        return "userList";
    }

    @RequestMapping("/sort/asc")
    public String sortProduct(Model model) {
        List<User> users = userRepository.findAll(Sort.by(Sort.Direction.ASC, "name"));
        model.addAttribute("users", users);
        return "userList";
    }

    @RequestMapping("/sort/desc")
    public String sortProductDesc(Model model) {
        List<User> users = userRepository.findAll(Sort.by(Sort.Direction.DESC, "name"));
        model.addAttribute("users", users);
        return "userList";
    }

}
