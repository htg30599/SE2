package SE2.admin.controller;

import SE2.admin.model.Roles;
import SE2.admin.model.User;
import SE2.admin.repository.RolesRepository;
import SE2.admin.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.HashSet;
import java.util.Set;


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

    @RequestMapping(value = "/process_register", method = RequestMethod.POST)
    public String processRegistration(User user) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String encodedPassword = encoder.encode(user.getPassword());
        user.setPassword(encodedPassword);
        Roles roles= rolesRepository.getById(1);
        Set<Roles> rolesSet=new HashSet<>();
        rolesSet.add(roles);
        user.setRoles(rolesSet);
        repo.save(user);

        return "register_success";
    }

   @GetMapping("/checkout")
    public String showCheckout(Model model) {
        return "checkout";
   }

  /*  @RequestMapping(value = "/login")
    public String loginTemplate() {
        return "login";
    }

    @RequestMapping(value = "/")
    public String AdminHomepage() {
        return "AdminHomepage";
    }*/

    /*@RequestMapping(value = "/login", method = RequestMethod.POST)
    public String login(String userName) {
        CustomerUserDetailsService service = new CustomerUserDetailsService();
        service.loadUserByUsername(userName);
        *//*if (repo.findByEmail(userName).getRoles().getId() == 1)*//*
            return "redirect:/";*/

    }


