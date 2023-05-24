package com.pizzaro.controller;

import com.pizzaro.controller.main.Attributes;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/stats")
public class StatsCont extends Attributes {
    @GetMapping
    public String Stats(Model model) {
        AddAttributesStats(model);
        return "stats";
    }

    @GetMapping("/{id}")
    public String AppsStats(Model model, @PathVariable Long id) {
        AddAttributesAppsStats(model, id);
        return "appsStats";
    }
}
