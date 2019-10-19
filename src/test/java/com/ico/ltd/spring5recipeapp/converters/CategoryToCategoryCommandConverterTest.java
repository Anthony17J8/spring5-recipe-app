package com.ico.ltd.spring5recipeapp.converters;

import com.ico.ltd.spring5recipeapp.commands.CategoryCommand;
import com.ico.ltd.spring5recipeapp.domain.Category;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class CategoryToCategoryCommandConverterTest {
    private static final Long ID_VALUE = 1L;

    private static final String DESCRIPTION = "descript";
    private CategoryToCategoryCommandConverter converter;

    @Before
    public void setUp() throws Exception {
        converter = new CategoryToCategoryCommandConverter();
    }

    @Test
    public void testNullObject() throws Exception {
        assertNull(converter.convert(null));
    }

    @Test
    public void testEmptyObject() throws Exception {
        assertNotNull(converter.convert(new Category()));
    }

    @Test
    public void convert() throws Exception {
        //given
        Category category = new Category();
        category.setId(ID_VALUE);
        category.setDescription(DESCRIPTION);

        //when
        CategoryCommand categoryCommand = converter.convert(category);

        //then
        assertEquals(ID_VALUE, categoryCommand.getId());
        assertEquals(DESCRIPTION, categoryCommand.getDescription());
    }
}