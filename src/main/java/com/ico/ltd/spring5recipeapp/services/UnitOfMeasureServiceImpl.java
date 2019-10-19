package com.ico.ltd.spring5recipeapp.services;

import com.ico.ltd.spring5recipeapp.commands.UnitOfMeasureCommand;
import com.ico.ltd.spring5recipeapp.converters.UnitOfMeasureToUnitOfMeasureCommandConverter;
import com.ico.ltd.spring5recipeapp.repositories.UnitOfMeasureRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Component
public class UnitOfMeasureServiceImpl implements UnitOfMeasureService {

    private final UnitOfMeasureRepository unitOfMeasureRepository;

    private final UnitOfMeasureToUnitOfMeasureCommandConverter toUnitOfMeasureCommandConverter;

    @Autowired
    public UnitOfMeasureServiceImpl(UnitOfMeasureRepository unitOfMeasureRepository, UnitOfMeasureToUnitOfMeasureCommandConverter toUnitOfMeasureCommandConverter) {
        this.unitOfMeasureRepository = unitOfMeasureRepository;
        this.toUnitOfMeasureCommandConverter = toUnitOfMeasureCommandConverter;
    }

    @Override
    public Set<UnitOfMeasureCommand> listAllUoms() {
        return StreamSupport.stream(
                unitOfMeasureRepository.findAll().spliterator(), false
        )
                .map(toUnitOfMeasureCommandConverter::convert)
                .collect(Collectors.toSet());
    }
}
