package com.cumple.comprobantes.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AccessLevel;
import lombok.Data;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.io.Serializable;
import java.util.List;

@Entity
@Data
@Table(name = "tipo")
public class Tipo implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ti_codigo")
    @Setter(AccessLevel.NONE)
    private Long codigo;

    @Column(name = "ti_id",unique = true)
    private String id;

    @NotBlank
    @Pattern(regexp = "^[a-zA-Z ]*$")
    @Column(name = "ti_nombre")
    private String nombre;

    @OneToMany(mappedBy = "tipo", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Comprobante> comprobantes;


}
