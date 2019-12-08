package com.ico.ltd.spring5recipeapp.services;

import com.ico.ltd.spring5recipeapp.commands.UnitOfMeasureCommand;
import com.ico.ltd.spring5recipeapp.converters.UnitOfMeasureToUnitOfMeasureCommandConverter;
import com.ico.ltd.spring5recipeapp.repositories.reactive.UnitOfMeasureReactiveRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;

@Component
public class UnitOfMeasureServiceImpl implements UnitOfMeasureService {

    private final UnitOfMeasureReactiveRepository unitOfMeasureReactiveRepository;

    private final UnitOfMeasureToUnitOfMeasureCommandConverter toUnitOfMeasureCommandConverter;

    @Autowired
    public UnitOfMeasureServiceImpl(UnitOfMeasureReactiveRepository unitOfMeasureReactiveRepository, UnitOfMeasureToUnitOfMeasureCommandConverter toUnitOfMeasureCommandConverter) {
        this.unitOfMeasureReactiveRepository = unitOfMeasureReactiveRepository;
        this.toUnitOfMeasureCommandConverter = toUnitOfMeasureCommandConverter;
    }

    @Override
    public Flux<UnitOfMeasureCommand> listAllUoms() {
        return unitOfMeasureReactiveRepository
                .findAll()
                .map(toUnitOfMeasureCommandConverter::convert);
    }
}
