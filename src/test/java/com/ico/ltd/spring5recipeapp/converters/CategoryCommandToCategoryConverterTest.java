package com.ico.ltd.spring5recipeapp.converters;

import com.ico.ltd.spring5recipeapp.commands.CategoryCommand;
import com.ico.ltd.spring5recipeapp.domain.Category;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class CategoryCommandToCategoryConverterTest {

    private static final String ID_VALUE = "1";

    private static final String DESCRIPTION = "description";

    private CategoryCommandToCategoryConverter converter;

    @Before
    public void setUp() throws Exception {
        converter = new CategoryCommandToCategoryConverter();
    }

    @Test
    public void testNullObject() throws Exception {
        assertNull(converter.convert(null));
    }

    @Test
    public void testEmptyObject() throws Exception {
        assertNotNull(converter.convert(new CategoryCommand()));
    }

    @Test
    public void convert() throws Exception {
        //given
        CategoryCommand categoryCommand = new CategoryCommand();
        categoryCommand.setId(ID_VALUE);
        categoryCommand.setDescription(DESCRIPTION);

        //when
        Category category = converter.convert(categoryCommand);

        //then
        assertEquals(ID_VALUE, category.getId());
        assertEquals(DESCRIPTION, category.getDescription());
    }
}