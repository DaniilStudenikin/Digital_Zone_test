package ru.itis.javalab.models;

import lombok.*;

@Data
@Builder
@AllArgsConstructor
@ToString
public class Well {
    private int id;
    private String name;
}
