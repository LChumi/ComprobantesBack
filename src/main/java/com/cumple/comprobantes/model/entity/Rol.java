package com.cumple.comprobantes.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AccessLevel;
import lombok.Data;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.List;

@Entity
@Data
@Table(name = "rol")
public class Rol implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "rol_codigo")
    @Setter(AccessLevel.NONE)
    private Long codigo;

    @Column(name = "rol_id")
    private String id;

    @Size(min = 5,max = 50)
    @Column(name = "rol_nombre")
    private String nombre;

    @OneToMany(mappedBy = "rol",cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Usuario> usuarios;
}
