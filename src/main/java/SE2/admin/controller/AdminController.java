package SE2.admin.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
@Controller

public class AdminController {
    @RequestMapping(value = "/adminHomepage")
@PreAuthorize("ADMIN")
    public String userHome(){
        return "adminHomepage";
    }
}

