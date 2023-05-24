package com.pizzaro.controller.main;

import com.pizzaro.model.Orderings;
import com.pizzaro.model.Stats;
import com.pizzaro.model.Users;
import com.pizzaro.model.enums.Role;
import com.pizzaro.model.enums.Status;
import org.springframework.ui.Model;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class Attributes extends Main {

    protected void AddAttributes(Model model) {
        model.addAttribute("role", getRole());
        model.addAttribute("user", getUser());
    }

    protected void AddAttributesUsers(Model model) {
        AddAttributes(model);
        model.addAttribute("users", usersRepo.findAllByOrderByRole());
        model.addAttribute("roles", Role.values());
    }

    protected void AddAttributesProducts(Model model) {
        AddAttributes(model);
        model.addAttribute("products", productsRepo.findAll());
    }

    protected void AddAttributesDetails(Model model, Long id) {
        AddAttributes(model);
        model.addAttribute("ordering", orderingsRepo.getReferenceById(id));
    }

    protected void AddAttributesApps(Model model) {
        AddAttributes(model);
        model.addAttribute("orderings", orderingsRepo.findAllByStatus(Status.WAITING));
    }

    protected void AddAttributesAppsMy(Model model) {
        AddAttributes(model);
        List<Orderings> orderings = orderingsRepo.findAllByManager_IdAndStatus(getUser().getId(), Status.PROCESSED);
        orderings.addAll(orderingsRepo.findAllByManager_IdAndStatus(getUser().getId(), Status.CONFIRMED));
        orderings.addAll(orderingsRepo.findAllByManager_IdAndStatus(getUser().getId(), Status.PAID));
        orderings.addAll(orderingsRepo.findAllByManager_IdAndStatus(getUser().getId(), Status.DELIVERED));
        orderings.addAll(orderingsRepo.findAllByManager_IdAndStatus(getUser().getId(), Status.REJECTED));
        model.addAttribute("orderings", orderings);
    }

    protected void AddAttributesOrderings(Model model) {
        AddAttributes(model);
        Users user = getUser();
        List<Orderings> orderings = orderingsRepo.findAllByOwner_IdAndStatus(user.getId(), Status.WAITING);
        orderings.addAll(orderingsRepo.findAllByOwner_IdAndStatus(user.getId(), Status.PROCESSED));
        orderings.addAll(orderingsRepo.findAllByOwner_IdAndStatus(user.getId(), Status.CONFIRMED));
        orderings.addAll(orderingsRepo.findAllByOwner_IdAndStatus(user.getId(), Status.PAID));
        orderings.addAll(orderingsRepo.findAllByOwner_IdAndStatus(user.getId(), Status.DELIVERED));
        orderings.addAll(orderingsRepo.findAllByOwner_IdAndStatus(user.getId(), Status.REJECTED));
        model.addAttribute("orderings", orderings);
    }

    protected void AddAttributesProductEdit(Model model, Long id) {
        AddAttributes(model);
        model.addAttribute("product", productsRepo.getReferenceById(id));
    }

    protected void AddAttributesAppsStats(Model model, Long id) {
        AddAttributes(model);
        model.addAttribute("orderings", orderingsRepo.findAllByManager_Id(id));
    }

    protected void AddAttributesStats(Model model) {
        AddAttributes(model);
        List<Stats> stats = statsRepo.findAll();
        stats.sort(Comparator.comparing(Stats::getPrice));
        Collections.reverse(stats);
        model.addAttribute("stats", stats);

        int[] topPriceNumber = new int[5];
        String[] topPriceName = new String[5];
        for (int i = 0; i < stats.size(); i++) {
            if (i == 5) break;
            topPriceName[i] = stats.get(i).getManager().getUsername();
            topPriceNumber[i] = stats.get(i).getPrice();
        }
        model.addAttribute("topPriceName", topPriceName);
        model.addAttribute("topPriceNumber", topPriceNumber);

        stats.sort(Comparator.comparing(Stats::getQuantity));
        Collections.reverse(stats);
        String[] topQuantityName = new String[5];
        int[] topQuantityNumber = new int[5];
        for (int i = 0; i < stats.size(); i++) {
            if (i == 5) break;
            topQuantityName[i] = stats.get(i).getManager().getUsername();
            topQuantityNumber[i] = stats.get(i).getQuantity();
        }
        model.addAttribute("topQuantityName", topQuantityName);
        model.addAttribute("topQuantityNumber", topQuantityNumber);
    }
}
