package SE2.admin.controller;

import SE2.admin.entity.User;
import SE2.admin.repository.UserRepository;
import SE2.admin.service.CustomUserDetails;
import SE2.admin.service.CustomerUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;


@Controller

public class BaseController {
    @Autowired
    private UserRepository repo;

    @GetMapping("/Home")
    public String index() {

        return index();
    }

    @GetMapping("/register")
    public String showSignUpForm(Model model) {
        model.addAttribute("user", new User());
        return "signup_form";
    }

    @PostMapping("/process_register")
    public String processRegistration(User user) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String encodedPassword = encoder.encode(user.getPassword());
        user.setPassword(encodedPassword);
        repo.save(user);
        return "register_sucess";
    }

    @PostMapping("/login")
    public String login(String userName, String password) {
        CustomerUserDetailsService service = new CustomerUserDetailsService();
        CustomUserDetails userDetails = service.loadUserByUsername(userName);
        if (repo.findByUserName(userName).getRoles().getId()==1)
            return "AdminHomepage";
        return "UserHomepage";
    }

}