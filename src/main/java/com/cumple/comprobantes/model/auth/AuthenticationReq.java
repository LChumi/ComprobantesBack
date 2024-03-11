/*
 * Copyright (c) 2024 Luis Chumi.
 * Este software está licenciado bajo la Licencia Pública General de GNU versión 3.
 * Puedes encontrar una copia de la licencia en https://www.gnu.org/licenses/gpl-3.0.html.
 *
 * Para consultas o comentarios, puedes contactarme en luischumi.9@gmail.com
 */

package com.cumple.comprobantes.model.auth;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.io.Serial;
import java.io.Serializable;

@Data
public class AuthenticationReq implements Serializable {

    @Serial
    private static final long serialVersionUID= 1L;

    @Pattern(regexp = "^[a-zA-Z ]*$")
    @NotBlank
    private String nombreUsuario;
    @NotBlank
    private String clave;
}
