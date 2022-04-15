package SE2.Admin.controller;


import SE2.Admin.model.DeliveryStatus;
import SE2.Admin.model.Status;
import SE2.Admin.repository.DeliveryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Controller

public class DeliveryController {
    @Autowired (required = false)
    DeliveryRepository deliveryRepository;
    @RequestMapping(value = "/Admin-delivery")
    public String getAllEmployee(Model model) {
        DeliveryStatus deliveryStatus = new DeliveryStatus();
        model.addAttribute("DeliveryStatus",deliveryStatus );
        return "Admin-delivery";
    }
    @RequestMapping(value = "/delivery-status")
    public String save(DeliveryStatus deliveryStatus, Model model) {
        model.addAttribute("DeliveryStatus", deliveryStatus);
        return "delivery-status";
    }
    @RequestMapping(value = "/save")
    public String saveUpdate(
            @RequestParam(value = "id", required = false) Long id, DeliveryStatus deliveryStatus)
    {
        deliveryStatus.setId(id);
        deliveryRepository.save(deliveryStatus);
        return "redirect:/";
    }

}
