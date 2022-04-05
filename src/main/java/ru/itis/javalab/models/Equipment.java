package ru.itis.javalab.models;

import lombok.*;

@Data
@Builder
@AllArgsConstructor
@ToString
public class Equipment {
    private int id;
    private String name;
    private Well well;
}
