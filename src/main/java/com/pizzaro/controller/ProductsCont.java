package com.pizzaro.controller;

import com.pizzaro.controller.main.Attributes;
import com.pizzaro.model.Products;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.Objects;
import java.util.UUID;

@Controller
@RequestMapping("/products")
public class ProductsCont extends Attributes {

    @GetMapping
    public String products(Model model) {
        AddAttributesProducts(model);
        return "products";
    }

    @GetMapping("/add")
    public String productAdd(Model model) {
        AddAttributes(model);
        return "productAdd";
    }

    @GetMapping("/edit/{id}")
    public String productEdit(Model model, @PathVariable Long id) {
        AddAttributesProductEdit(model, id);
        return "productEdit";
    }

    @GetMapping("/delete/{id}")
    public String productDelete(@PathVariable Long id) {
        productsRepo.deleteById(id);
        return "redirect:/products";
    }

    @PostMapping("/add")
    public String productAdd(Model model, @RequestParam String name, @RequestParam MultipartFile photo, @RequestParam int quantity, @RequestParam int price) {
        String res = "";
        if (photo != null && !Objects.requireNonNull(photo.getOriginalFilename()).isEmpty()) {
            String uuidFile = UUID.randomUUID().toString();
            boolean createDir = true;
            try {
                File uploadDir = new File(uploadImg);
                if (!uploadDir.exists()) createDir = uploadDir.mkdir();
                if (createDir) {
                    res = uuidFile + "_" + photo.getOriginalFilename();
                    photo.transferTo(new File(uploadImg + "/" + res));
                }
            } catch (Exception e) {
                model.addAttribute("message", "Некорректный данные!");
                AddAttributes(model);
                return "productAdd";
            }
        }


        productsRepo.save(new Products(name, res, quantity, price));
        return "redirect:/products";
    }

    @PostMapping("/edit/{id}")
    public String productEdit(Model model, @RequestParam String name, @RequestParam MultipartFile photo, @RequestParam int quantity, @RequestParam int price, @PathVariable Long id) {
        Products product = productsRepo.getReferenceById(id);

        if (photo != null && !Objects.requireNonNull(photo.getOriginalFilename()).isEmpty()) {
            String res = "";
            String uuidFile = UUID.randomUUID().toString();
            boolean createDir = true;
            try {
                File uploadDir = new File(uploadImg);
                if (!uploadDir.exists()) createDir = uploadDir.mkdir();
                if (createDir) {
                    res = uuidFile + "_" + photo.getOriginalFilename();
                    photo.transferTo(new File(uploadImg + "/" + res));
                }
            } catch (Exception e) {
                model.addAttribute("message", "Некорректный данные!");
                AddAttributesProductEdit(model, id);
                return "productEdit";
            }
            product.setPhoto(res);
        }


        product.setName(name);
        product.setQuantity(quantity);
        product.setPrice(price);
        productsRepo.save(product);
        return "redirect:/products";
    }
}
