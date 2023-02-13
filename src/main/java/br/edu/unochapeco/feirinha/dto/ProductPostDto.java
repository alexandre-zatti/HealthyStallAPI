package br.edu.unochapeco.feirinha.dto;

import jakarta.validation.constraints.*;

public record ProductPostDto(
    @NotNull
    @Size(min = 3, max = 30, message = "Titulo deve conter no minimo 3 e no maximo 30 caracteres!")
    String title,

    @Size(max = 255, message = "Descricao deve conter maximo 30 caracteres!")
    String description,

    @NotNull
    @DecimalMin(value = "0.00", message = "O valor do preco nao pode ser menor que 0!")
    @Digits(integer=4, fraction=2)
    Double price,

    @NotNull
    @Min(value = 0, message = "Valor do inventario nao pode ser negativo!")
    Integer inventory,

    @NotNull
    Boolean active,

    @Size(max = 1000, message = "Link para thumbnail deve conter maximo 1000 caracteres!")
    String thumbnail_path
) {
}
