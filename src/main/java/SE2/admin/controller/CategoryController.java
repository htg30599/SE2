package SE2.admin.controller;

import SE2.admin.model.Category;
import SE2.admin.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping(value = "/admin/category")
public class CategoryController {
    @Autowired
    CategoryRepository categoryRepository;

    @RequestMapping(value = "/list")
    public String getAllCategory(Model model) {
        List<Category> categories = categoryRepository.findAll();
        model.addAttribute("categories", categories);
        return "categoryList";
    }

    @RequestMapping(value = "/add")
    public String addEmployee (Model model) {
        Category category = new Category();
        model.addAttribute("category", category);
        return "categoryAdd";
    }

    @RequestMapping (value = "/update/{id}")
    public String updateEmployee(
            @PathVariable (value = "id") Long id, Model model)  {
        Category category = categoryRepository.getById(id);
        model.addAttribute(category);
        return "categoryUpdate";
    }

    @RequestMapping(value = "/save")
    public String saveUpdate(
            @RequestParam(value = "id", required = false) Long id, @Valid Category category, BindingResult result)
    {
        if (result.hasErrors()) {
            if (id == null) {
                return "categoryAdd";
            } else {
                return "categoryUpdate";
            }
        }
        category.setId(id);
        categoryRepository.save(category);
        return "redirect:/admin/category/list";
    }
    @RequestMapping("/search")
    public String searchCategory(
            Model model,
            @RequestParam(value = "name") String name) {
        List<Category> categories = categoryRepository.findByNameContaining(name);
        model.addAttribute("categories", categories);
        return "categoryList";
    }
    @RequestMapping(value = "/delete/{id}")
    public String deleteCategory(
            @PathVariable(value = "id") Long id) {
        Category category = categoryRepository.getById(id);
        categoryRepository.delete(category);
        return "redirect:/admin/category/list";
    }
}