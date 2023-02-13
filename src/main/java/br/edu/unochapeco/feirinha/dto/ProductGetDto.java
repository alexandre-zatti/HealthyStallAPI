package br.edu.unochapeco.feirinha.dto;

import java.util.Date;

public record ProductGetDto(
        Long id,
        String title,
        String description,
        Double price,
        Integer inventory,
        Boolean active,
        String thumbnail_path,
        Date createdAt,
        Date updatedAt
) {
}
