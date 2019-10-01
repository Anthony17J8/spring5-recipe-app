package com.ico.ltd.spring5recipeapp.controllers;

import com.ico.ltd.spring5recipeapp.domain.Category;
import com.ico.ltd.spring5recipeapp.domain.UnitOfMeasure;
import com.ico.ltd.spring5recipeapp.repositories.CategoryRepository;
import com.ico.ltd.spring5recipeapp.repositories.UnitOfMeasureRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Optional;

@Controller
public class IndexController {

    private CategoryRepository categoryRepository;

    private UnitOfMeasureRepository unitOfMeasureRepository;

    @Autowired
    public IndexController(CategoryRepository categoryRepository,
                           UnitOfMeasureRepository unitOfMeasureRepository) {
        this.categoryRepository = categoryRepository;
        this.unitOfMeasureRepository = unitOfMeasureRepository;
    }

    @RequestMapping({"", "/", "/index"})
    public String getIndexPage() {
        System.out.println("ANYWANT");

        Optional<Category> catOpt = categoryRepository.findByDescription("American");
        Optional<UnitOfMeasure> uomOpt = unitOfMeasureRepository.findByDescription("Teaspoon");

        catOpt.ifPresent((s) -> System.out.println("Category id: " + s.getId()));
        uomOpt.ifPresent((u) -> System.out.println("UOM id: " + u.getId()));

        return "index";
    }
}
