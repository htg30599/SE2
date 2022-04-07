<<<<<<<< HEAD:src/main/java/se2Project/controller/BaseController.java
package se2Project.controller;
========
package SE2.controller;
>>>>>>>> main:src/main/java/SE2/controller/BaseController.java

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller

<<<<<<<< HEAD:src/main/java/se2Project/controller/BaseController.java
public class BaseController {
    @RequestMapping("/Home")
========
    @RequestMapping("/home")
>>>>>>>> main:src/main/java/SE2/controller/BaseController.java
    public String index(){
        return index();
    }

}