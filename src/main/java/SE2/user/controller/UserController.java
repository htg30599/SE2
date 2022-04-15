package SE2.user.controller;

import SE2.user.model.User;
import SE2.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;

@Controller
@RequestMapping(value = "/user")
public class UserController {
    @Autowired
    UserRepository userRepository;

    @RequestMapping("/")
    public String showAccountDetail(Model model,
                                 @PathVariable(value = "id") Long id) {
        User user = userRepository.getById(id);
        model.addAttribute("user", user);
        return "userDetailAccount";
    }

    @RequestMapping("/update_account/{id}")
    public String updateAccount(Model model,
                                @PathVariable(value = "id") Long id) {
        User user = userRepository.getById(id);
        model.addAttribute("user", user);
        return "userUpdateInfo";
    }

    @RequestMapping("/update_password/{id}")
    public String updatePassword(Model model,
                                 @PathVariable(value = "id") Long id) {
        User user = userRepository.getById(id);
        model.addAttribute("user", user);
        return "userUpdatePassword";
    }

    @RequestMapping("/save_change")
    public String saveChange(@Valid User user, BindingResult bindingResult,
                             @RequestParam(value = "id") Long id) {
        if (bindingResult.hasErrors()) {
            return "userUpdateInfo";
        }
        user.setId(id);
        userRepository.save(user);
        return "redirect:/user";
    }
}
