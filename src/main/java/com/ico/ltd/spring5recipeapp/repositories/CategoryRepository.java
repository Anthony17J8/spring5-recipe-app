package com.ico.ltd.spring5recipeapp.repositories;

import com.ico.ltd.spring5recipeapp.domain.Category;
import org.springframework.data.repository.CrudRepository;

public interface CategoryRepository extends CrudRepository<Category, Long> {
}
