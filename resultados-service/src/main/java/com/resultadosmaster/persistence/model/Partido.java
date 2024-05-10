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

    @Column(nullable = false)
    private String ronda;

    @Column(nullable = false)
    private String fecha_hora;

    @Column(nullable = false)
    private String pareja_1;

    @Column(nullable = false)
    private String pareja_2;

    @Column(nullable = false)
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
