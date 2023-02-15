package br.edu.unochapeco.feirinha.dto;

import br.edu.unochapeco.feirinha.entity.Person;

import java.util.Date;

public record FeiranteGetDto(
        Long id,
        Person person,
        Boolean active,
        Date assignAt,
        Date revokedAt
) {
}
