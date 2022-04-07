<<<<<<<< HEAD:src/main/java/se2Project/controller/BaseController.java
package se2Project.controller;
========
package SE2.controller;
>>>>>>>> main:src/main/java/SE2/controller/BaseController.java

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import se2Project.entity.User;
import se2Project.repository.UserRepository;


@Controller

<<<<<<<< HEAD:src/main/java/se2Project/controller/BaseController.java
public class BaseController {
<<<<<<< HEAD
    @Autowired
    private UserRepository repo;

    @GetMapping ("/Home")
=======
    @RequestMapping("/Home")
========
    @RequestMapping("/home")
>>>>>>>> main:src/main/java/SE2/controller/BaseController.java
>>>>>>> 5c341d1178bd3cbd532e24198d20d363243f5520
    public String index(){

        return index();
    }
    @GetMapping("/register")
    public String showSignUpForm(Model model){
        model.addAttribute("user", new User());
        return "signup_form";
    }

    @PostMapping("/process_register")
    public String processRegistration(User user){
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String encodedPassword = encoder.encode(user.getPassword());
        user.setPassword(encodedPassword);
        repo.save(user);
        return "register_sucess";
    }


}