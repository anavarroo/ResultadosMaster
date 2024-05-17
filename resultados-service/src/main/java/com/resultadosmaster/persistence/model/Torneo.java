package com.resultadosmaster.persistence.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name = "torneos")
@NoArgsConstructor
public class Torneo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @Column(name = "ciudad", nullable = false)
    private String ciudad;

    @Column(name = "tipo", nullable = false)
    private String tipo;

    @Column(name = "fechatorneo", nullable = false)
    private String fechatorneo;
}
