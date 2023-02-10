package br.edu.unochapeco.feirinha.dto;

import jakarta.validation.constraints.*;

public record PersonPostDto(
    @NotNull(message = "Nome nao pode estar vazio!")
    @NotBlank(message = "Nome nao pode estar vazio!")
    @Size(min = 3, max = 20, message = "Nome deve conter no minimo 3 e no maximo 20 caracteres!")
    String username,

    @NotNull(message = "Path da imagem nao pode estar vazio!")
    @NotBlank(message = "Path da imagem nao pode estar vazio!")
    @Size(max = 1000, message = "O link para imagem deve conter no maximo 1000 caracteres!")
    String profileImgPath,

    @NotNull(message = "O valor do saldo nao pode estar vazio!")
    @DecimalMin(value = "0.00", message = "O valor do saldo nao pode ser menor que 0!")
    @Digits(integer=4, fraction=2)
    Double balance
) {
}
