package com.cumple.comprobantes.model.entity;

import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Data
@Table(name = "comprobante")
public class Comprobante implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "comp_codigo")
    private Long codigo;

    @Column(name = "comp_clave_acceso",unique = true)
    @NotBlank(message = "campo Obligarorio")
    private String claveAcceso;

    @Column(name = "comp_autorizacion")
    private String autorizacion;

    @Column(name = "comp_fecha_carga",updatable = false)
    @CreationTimestamp
    private LocalDateTime fechaCarga;

    @Column(name = "comp_fecha_comprobante")
    private Date fechaComprobante;

    @Column(name = "comp_fecha_autorizacion")
    private Date fechaAutorizacion;

    @Column(name = "com_nro_comprobante")
    private String nroComprobante;

    @Column(name = "comp_cliente")
    private String cliente;

    @Column(name = "comp_total")
    private String total;

    @NotBlank
    @Pattern(regexp = "\\d{11}", message = "El Ruc debe tener 11 digitos")
    @Column(name = "comp_ruc")
    private String ruc;

    @Email
    @Column(name = "comp_email")
    private String email;

    @Column(name = "comp_almacen")
    public int almacen;

    @Column(name = "comp_p_venta")
    public int pVenta;

    @Column(name = "comp_numero")
    public int numero;

    @ManyToOne
    @JoinColumn(name = "comp_empresa",referencedColumnName = "emp_codigo")
    private Empresa empresa;

    @ManyToOne
    @JoinColumn(name = "comp_tipo", referencedColumnName = "ti_codigo")
    private Tipo tipo;

    @ManyToOne
    @JoinColumn(name = "comp_estado",referencedColumnName = "est_codigo")
    private Estado estado;

}
