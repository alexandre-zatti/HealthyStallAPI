package br.edu.unochapeco.feirinha.dto;

import java.util.Date;

public record PersonGetDto(
        Long id,
        String username,
        String profileImgPath,
        Double balance,
        Date createdAt
) {
}
