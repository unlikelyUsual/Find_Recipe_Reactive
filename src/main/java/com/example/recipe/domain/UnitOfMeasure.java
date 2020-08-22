package com.example.recipe.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;


@Document
@Getter
@Setter
@NoArgsConstructor
public class UnitOfMeasure {

    @Id
    private String id;

    private String description;

    public UnitOfMeasure(String id, String description) {
        this.id = id;
        this.description = description;
    }

    public UnitOfMeasure(String description) {
        this.description = description;
    }
}
