package com.ico.ltd.spring5recipeapp.domain;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class CategoryTest {

    private Category category;

    @Before
    public void setUp(){
        category = new Category();
    }

    @Test
    public void getId() {
        String idValue = "4";
        category.setId(idValue);
        assertEquals(idValue, category.getId());
    }
}