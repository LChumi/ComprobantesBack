package com.cumple.comprobantes.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.List;

@Entity
@Data
@Table(name = "estado")
public class Estado implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "est_codigo")
    private Long codigo;

    @Column(name = "est_id")
    private String id;

    @NotBlank
    @Size(min =2,max = 50)
    @Pattern(regexp = "^[a-zA-Z0-9 ]*$")
    @Column(name = "est_nombre")
    private String nombre;

    @OneToMany(mappedBy = "estado", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Comprobante> comprobantes;
}
