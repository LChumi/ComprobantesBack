package com.cumple.comprobantes.model.entity;


import lombok.AccessLevel;
import lombok.Data;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Future;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Past;
import javax.validation.constraints.Pattern;
import java.io.Serializable;
import java.util.Date;

@Entity
@Data
@Table(name = "certificado")
public class Certificado implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "cert_codigo")
    @Setter(AccessLevel.NONE)
    private Long codigo;

    @ManyToOne
    @JoinColumn(name = "cert_empresa",referencedColumnName = "emp_codigo")
    private Empresa empresa;

    @Column(name = "cert_nombre",unique = true)
    @NotBlank
    @Pattern(regexp = "^[a-zA-Z0-9 ]*$", message = "El nombre no puede contener caracteres especiales")
    private String nombre;

    @NotBlank
    @Column(name = "cert_clave")
    private String clave;

    @Past
    @Column(name = "cert_fecha_inicio")
    private Date fechaInicio;

    @Future
    @Column(name = "cert_fecha_fin")
    private Date fechaFin;
}
