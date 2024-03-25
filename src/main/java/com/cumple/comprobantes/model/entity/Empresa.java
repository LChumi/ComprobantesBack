package com.cumple.comprobantes.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AccessLevel;
import lombok.Data;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.List;

@Entity
@Data
@Table(name = "empresa")
public class Empresa implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "emp_codigo")
    @Setter(AccessLevel.NONE)
    private Long codigo;

    @Column(name = "emp_id")
    private String id;

    @Column(name = "emp_ruc",unique = true)
    @NotBlank
    @Pattern(regexp = "\\d{11}")
    private String ruc;

    @NotBlank
    @Pattern(regexp = "^[a-zA-Z ]*$")
    @Size(min=5, max = 50)
    @Column(name = "emp_nombre")
    private String nombre;

    @Column(name = "emp_ruta_xml")
    private String emp_ruta_xml;

    @Column(name = "emp_ruta_xml_firmado")
    private String rutaXmlFirmado;

    @Column(name = "emp_ruta_respuesta")
    private String rutaRespuesta;

    @Column(name = "emp_ruta_certificado")
    private String rutaCertificado;

    @OneToMany(mappedBy = "empresa",cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Comprobante> comprobantes;

    @OneToMany(mappedBy = "empresa",cascade = CascadeType.ALL)
    @JsonIgnore
    private List<UsuarioEmpresa> usuarios;

    @OneToMany(mappedBy = "empresa",cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Certificado> certificados;
}
