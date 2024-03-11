package com.cumple.comprobantes.model.entity;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Data
@Table(name = "usuario_empresa")
public class UsuarioEmpresa implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "usr_empr_codigo")
    private Long usrEmprCodigo;

    @ManyToOne
    @JoinColumn(name = "usr_emp_empresa",referencedColumnName = "emp_codigo")
    private Empresa empresa;

    @ManyToOne
    @JoinColumn(name = "usr_emp_usuario",referencedColumnName = "usr_codigo")
    private Usuario usuario;
}
