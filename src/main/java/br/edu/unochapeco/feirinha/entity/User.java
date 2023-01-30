package br.edu.unochapeco.feirinha.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.util.Date;

@Entity
@AllArgsConstructor
@NoArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;

    @NotNull
    @Size(min = 3, max = 20, message = "Nome deve conter no minimo 3 e no maximo 20 caracteres!")
    private String username;

    @NotNull
    @Size(max = 1000, message = "O link para imagem deve conter no maximo 1000 caracteres!")
    private String profileImgPath;

    @NotNull
    @DecimalMin(value = "0.00", message = "O valor do saldo nao pode ser menor que 0!")
    @Digits(integer=4, fraction=2)
    @Column(columnDefinition = "NUMERIC", precision = 2, scale = 10)
    private Double balance = 0.00;

    @NotNull
    @Size(max = 1000, message = "O qrcode pix deve conter no maximo 1000 caracteres!")
    private String pixQrcode;

    @CreationTimestamp
    private Date createdAt;

    @UpdateTimestamp
    private Date updatedAt;
}
