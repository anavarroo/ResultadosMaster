package com.resultadosmaster.persistence.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name = "partidos")
@NoArgsConstructor
public class Partido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "torneo_Id")
    private Long torneoId;

    @Column(name = "ronda", nullable = false)
    private String ronda;

    @Column(name = "fecha_hora", nullable = false)
    private String fecha_hora;

    @Column(name = "pareja_1", nullable = false)
    private String pareja_1;

    @Column(name = "pareja_2", nullable = false)
    private String pareja_2;

    @Column(name = "resultado", nullable = false)
    private String resultado;


    public Partido(Long id, Long torneoId, String ronda, String fecha_hora, String pareja_1, String pareja_2, String resultado) {
        this.id = id;
        this.torneoId = torneoId;
        this.ronda = ronda;
        this.fecha_hora = fecha_hora;
        this.pareja_1 = pareja_1;
        this.pareja_2 = pareja_2;
        this.resultado = resultado;
    }
}
