package SE2.user.controller;

import SE2.user.model.Product;
import SE2.user.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
public class BaseController {

    @Autowired
    ProductRepository productRepository;

    @RequestMapping("/")
    public String index(){
        return index();
    }

    @RequestMapping(value = "/search", method = RequestMethod.GET)
    @ResponseBody
    public ModelAndView search(@RequestParam("name") String name) {
        ModelAndView mv = new ModelAndView();
        mv.setViewName("fragments/searchFragment");
        List<Product> products = productRepository.findByNameContainingIgnoreCase(name);
        mv.addObject("products", products);
        return mv;
    }
}
