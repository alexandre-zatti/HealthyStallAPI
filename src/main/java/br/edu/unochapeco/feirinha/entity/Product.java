package br.edu.unochapeco.feirinha.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Entity
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;

    @NotNull
    @Size(min = 3, max = 30, message = "Titulo deve conter no minimo 3 e no maximo 30 caracteres!")
    private String title;

    @Size(max = 255, message = "Descricao deve conter maximo 30 caracteres!")
    private String description;

    @NotNull
    @DecimalMin(value = "0.00", message = "O valor do preco nao pode ser menor que 0!")
    @Digits(integer=4, fraction=2)
    @Column(columnDefinition = "NUMERIC", precision = 2, scale = 10)
    private Double price = 0.00;

    @NotNull
    @Min(value = 0, message = "Valor do inventario nao pode ser negativo!")
    private Integer inventory = 0;

    @NotNull
    private Boolean active = true;

    @Size(max = 1000, message = "Link para thumbnail deve conter maximo 1000 caracteres!")
    private String thumbnail_path;

    @CreationTimestamp
    private Date createdAt;

    @UpdateTimestamp
    private Date updatedAt;
}
