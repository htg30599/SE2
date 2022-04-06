package SE2.admin.controller;

import SE2.admin.entity.Roles;
import SE2.admin.entity.User;
import SE2.admin.repository.RolesRepository;
import SE2.admin.repository.UserRepository;
import SE2.admin.service.CustomUserDetails;
import SE2.admin.service.CustomerUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;


@Controller

public class BaseController {
    @Autowired
    private UserRepository repo;

    @Autowired
    private RolesRepository rolesRepository;

    @GetMapping("/Home")
    public String index() {

        return index();
    }

    @GetMapping("/register")
    public String showSignUpForm(Model model) {
//        List<Roles> rolesList = rolesRepository.findAll();
        model.addAttribute("user", new User());
//        model.addAttribute("roles",rolesList);
        return "signup_form";
    }

    @PostMapping("/process_register")
    public String processRegistration(User user) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String encodedPassword = encoder.encode(user.getPassword());
        user.setPassword(encodedPassword);
        repo.save(user);
        return "register_success";
    }

    @PostMapping("/login1")
    public String login(String userName) {
        CustomerUserDetailsService service = new CustomerUserDetailsService();
        service.loadUserByUsername(userName);
        if (repo.findByEmail(userName).getRoles().getId()==1)
            return "AdminHomepage";
        return "UserHomepage";
    }

}