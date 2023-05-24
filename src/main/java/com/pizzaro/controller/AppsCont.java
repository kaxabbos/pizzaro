package com.pizzaro.controller;

import com.pizzaro.controller.main.Attributes;
import com.pizzaro.model.Details;
import com.pizzaro.model.Orderings;
import com.pizzaro.model.enums.Status;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/apps")
public class AppsCont extends Attributes {

    @GetMapping
    public String apps(Model model) {
        AddAttributesApps(model);
        return "apps";
    }

    @GetMapping("/my")
    public String appsMy(Model model) {
        AddAttributesAppsMy(model);
        return "appsMy";
    }

    @GetMapping("/confirmed/{id}")
    public String appsConfirmed(@PathVariable Long id) {
        Orderings ordering = orderingsRepo.getReferenceById(id);
        ordering.setStatus(Status.CONFIRMED);
        orderingsRepo.save(ordering);
        return "redirect:/apps/my";
    }

    @GetMapping("/rejected/{id}")
    public String appsRejected(@PathVariable Long id) {
        Orderings ordering = orderingsRepo.getReferenceById(id);
        ordering.setStatus(Status.REJECTED);
        orderingsRepo.save(ordering);
        return "redirect:/apps/my";
    }

    @GetMapping("/delivered/{id}")
    public String appsDelivered(Model model, @PathVariable Long id) {
        Orderings ordering = orderingsRepo.getReferenceById(id);
        for (Details d : ordering.getDetails()) {
            if (d.getQuantity() > d.getProduct().getQuantity()) {
                AddAttributesAppsMy(model);
                model.addAttribute("message", "Недостаточно товаров для доставки!");
                return "appsMy";
            }
        }

        for (Details d : ordering.getDetails()) {
            d.getProduct().setQuantity(d.getProduct().getQuantity() - d.getQuantity());
        }

        ordering.getManager().getStats().setQuantity(ordering.getManager().getStats().getQuantity() + 1);
        ordering.setStatus(Status.DELIVERED);
        orderingsRepo.save(ordering);
        return "redirect:/apps/my";
    }

    @GetMapping("/processing/{id}")
    public String appProcessing(@PathVariable Long id) {
        Orderings ordering = orderingsRepo.getReferenceById(id);
        ordering.setStatus(Status.PROCESSED);
        ordering.setManager(getUser());
        orderingsRepo.save(ordering);
        return "redirect:/apps";
    }

}
