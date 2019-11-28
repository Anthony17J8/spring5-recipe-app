package com.ico.ltd.spring5recipeapp.repositories;

import com.ico.ltd.spring5recipeapp.domain.UnitOfMeasure;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

import static org.junit.Assert.assertEquals;

@Ignore
@RunWith(SpringRunner.class)
@DataJpaTest
public class UnitOfMeasureRepositoryIT {

    @Autowired
    private UnitOfMeasureRepository repository;

    @Before
    public void setUp() throws Exception {
    }

    @Test
    //@DirtiesContext
    public void findByDescription() {
        Optional<UnitOfMeasure> uomOptional = repository.findByDescription("Teaspoon");
        assertEquals("Teaspoon", uomOptional.get().getDescription());
    }

    @Test
    public void findByDescriptionCup() {
        Optional<UnitOfMeasure> uomOptional = repository.findByDescription("Cup");
        assertEquals("Cup", uomOptional.get().getDescription());
    }
}