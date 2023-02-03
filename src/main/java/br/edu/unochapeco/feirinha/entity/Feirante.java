package br.edu.unochapeco.feirinha.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
@Entity
public class Feirante {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;

    @ManyToOne
    @NotNull
    private Person person;

    @NotNull
    private Boolean active;

    @CreationTimestamp
    private Date assignAt;

    @UpdateTimestamp
    private Date revokedAt;
}
