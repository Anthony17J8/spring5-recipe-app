package com.ico.ltd.spring5recipeapp.domain;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode(exclude = {"recipe"})
public class Notes {

    private String id;

    private String recipeNotes;

    private Recipe recipe;
}
