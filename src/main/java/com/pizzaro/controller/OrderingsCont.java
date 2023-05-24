package com.pizzaro.controller;

import com.pizzaro.controller.main.Attributes;
import com.pizzaro.model.Details;
import com.pizzaro.model.Orderings;
import com.pizzaro.model.Stats;
import com.pizzaro.model.Users;
import com.pizzaro.model.enums.Discount;
import com.pizzaro.model.enums.Status;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/orderings")
public class OrderingsCont extends Attributes {
    @GetMapping
    public String orderings(Model model) {
        AddAttributesOrderings(model);
        return "orderings";
    }

    @GetMapping("/paid/{id}")
    public String orderingPaid(@PathVariable Long id) {
        Orderings ordering = orderingsRepo.getReferenceById(id);
        ordering.setStatus(Status.PAID);
        Stats stat = ordering.getManager().getStats();
        stat.setPrice(stat.getPrice() + ordering.getPrice());
        orderingsRepo.save(ordering);
        return "redirect:/orderings";
    }

    @PostMapping("/add")
    public String orderingAdd(@RequestParam String date, @RequestParam String discount) {
        Users user = getUser();
        List<Details> details = user.getDetails();
        Orderings ordering = new Orderings(user, date);
        int quantity = 0;
        int price = 0;
        for (Details d : details) {
            d.setOwner(null);
            d.setOrdering(ordering);
            quantity += d.getQuantity();
            price += d.getPrice();
        }
        ordering.setQuantity(quantity);

        for (Discount i : Discount.values()){
            if (i.name().equals(discount)){
                price *= i.getDiscount();
            }
        }
        ordering.setPrice(price);
        orderingsRepo.save(ordering);
        return "redirect:/orderings";
    }
}
