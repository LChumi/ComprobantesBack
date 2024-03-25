package com.cumple.comprobantes.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AccessLevel;
import lombok.Data;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.List;

@Entity
@Data
@Table(name = "usuario")
public class Usuario implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "usr_codigo")
    @Setter(AccessLevel.NONE)
    private Long codigo;

    @Column(name = "usr_id")
    private String id;

    @NotBlank
    @Size(min = 5, max = 50)
    @Column(name = "usr_nombre")
    private String nombre;

    @Column(name = "usr_clave")
    private String clave;

    @ManyToOne
    @JoinColumn(name = "usr_rol",referencedColumnName = "rol_codigo")
    private Rol rol;

    @OneToMany(mappedBy = "usuario",cascade = CascadeType.ALL)
    @JsonIgnore
    private List<UsuarioEmpresa> empresas;

}
