package br.edu.unochapeco.feirinha.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.AssertFalse;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.util.Date;

@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Feirante {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;

    @ManyToOne
    @NotNull
    private User user;

    @NotNull
    private Boolean active;

    @CreationTimestamp
    private Date assignAt;

    @UpdateTimestamp
    private Date revokedAt;
}
