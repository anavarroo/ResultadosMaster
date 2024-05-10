package com.master.ranking.persistence.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name = "jugadores")
@NoArgsConstructor
public class Jugador {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nombre;
    @Column(nullable = false)
    private String apellidos;

    @Column(nullable = false)
    private Long numero_ranking;

    @Column(nullable = false)
    private String puntos;
    @Column(nullable = false)
    private String imagen_url;

    @Column(nullable = false)
    private String bandera_url;

    @Column(nullable = false)
    private String genero;

}
