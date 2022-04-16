package SE2.admin.controller;

import SE2.admin.model.Product;
import SE2.admin.model.Promotion;
import SE2.admin.repository.ProductRepository;
import SE2.admin.repository.PromotionRepository;
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
@RequestMapping(value = "/admin/promotion")
public class PromotionController {
    @Autowired
    private PromotionRepository promotionRepository;
    @Autowired
    private ProductRepository productRepository;

    @RequestMapping("/list")
    public String showPromotion(Model model){
        List<Promotion> promotionList = promotionRepository.findAll();
        model.addAttribute("promotionList", promotionList);
        return "promotionList";
    }

    @RequestMapping(value = "/{id}")
    public String showPromotion(
            @PathVariable(value = "id") Long id, Model model) {
        Promotion promotion = promotionRepository.getById(id);
        model.addAttribute("promotion", promotion);
        return "promotionInfo";
    }

    @RequestMapping(value = "/add")
    public String addPromotion(Model model) {
        Promotion promotion = new Promotion();
        model.addAttribute("promotion", promotion);
        return "promotionAdd";
    }

    @RequestMapping(value = "/update/{id}")
    public String updatePromotion(
            @PathVariable(value = "id") Long id, Model model) {
        Promotion promotion = promotionRepository.getById(id);
        model.addAttribute("promotion", promotion);
        return "promotionUpdate";
    }

    @RequestMapping(value = "/delete/{id}")
    public String deletePromotion(
            @PathVariable(value = "id") Long id) {
        Promotion promotion = promotionRepository.getById(id);
        promotionRepository.delete(promotion);
        return "redirect:/admin/promotion/list";
    }

    @RequestMapping(value = "/save")
    public String saveUpdate(
            @RequestParam(value = "id", required = false) Long id,
            @Valid Promotion promotion, BindingResult result) {
        if (result.hasErrors()) {
            if (id == null)
                return "promotionAdd";
            else
                return "promotionUpdate";
        }
        promotion.setId(id);
        promotionRepository.save(promotion);
        return "redirect:/admin/promotion/list";
    }

    @RequestMapping("/search")
    public String searchPromotion(
            Model model,
            @RequestParam(value = "name") String name) {
        List<Promotion> promotions = promotionRepository.findByNameContaining(name);
        model.addAttribute("promotions", promotions);
        return "promotionList";
    }

    @RequestMapping("/sort/asc")
    public String sortPromotion(Model model) {
        List<Promotion> promotions = promotionRepository.findAll(Sort.by(Sort.Direction.ASC, "name"));
        model.addAttribute("promotions", promotions);
        return "promotionList";
    }

    @RequestMapping("/sort/desc")
    public String sortPromotionDesc(Model model) {
        List<Promotion> promotions = promotionRepository.findAll(Sort.by(Sort.Direction.DESC, "name"));
        model.addAttribute("promotions", promotions);
        return "promotionList";
    }

}
