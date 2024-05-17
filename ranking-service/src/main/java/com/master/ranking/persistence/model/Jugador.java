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
    @Column(name = "id")
    private Long id;

    @Column(name = "nombre", nullable = false)
    private String nombre;

    @Column(name = "apellidos", nullable = false)
    private String apellidos;

    @Column(name = "numero_ranking", nullable = false)
    private Long numero_ranking;

    @Column(name = "puntos", nullable = false)
    private String puntos;

    @Column(name = "imagen_url", nullable = false)
    private String imagen_url;

    @Column(name = "bandera_url", nullable = false)
    private String bandera_url;

    @Column(name = "genero")
    private String genero;

}
