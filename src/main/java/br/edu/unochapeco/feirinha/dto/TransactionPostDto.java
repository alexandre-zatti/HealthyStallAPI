package br.edu.unochapeco.feirinha.dto;

import br.edu.unochapeco.feirinha.enums.TransactionType;
import jakarta.validation.constraints.*;

public record TransactionPostDto(
    @NotNull(message = "Pessoa nao pode estar vazio!")
    @Min(1)
    Long personId,
    Long productId,
    @DecimalMin(value = "0.00", message = "O valor do deposito nao pode ser menor que 0!")
    @Digits(integer=4, fraction=2)
    Double deposit,
    @NotNull(message = "Tipo de transacao nao pode estar vazio! valores válidos são: DEPOSIT ou PURCHASE!")
    @NotBlank(message = "Tipo de transacao nao pode estar vazio! valores válidos são: DEPOSIT ou PURCHASE!")
    String transactionType
) {
}
