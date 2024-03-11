/*
 * Copyright (c) 2024 Luis Chumi.
 * Este software está licenciado bajo la Licencia Pública General de GNU versión 3.
 * Puedes encontrar una copia de la licencia en https://www.gnu.org/licenses/gpl-3.0.html.
 *
 * Para consultas o comentarios, puedes contactarme en luischumi.9@gmail.com
 */

package com.cumple.comprobantes.services;

import com.cumple.comprobantes.model.entity.Usuario;
import com.cumple.comprobantes.model.entity.UsuarioEmpresa;
import com.cumple.comprobantes.repository.UsuarioEmpresaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;

@Service
public class UsuarioEmpresaServiceImpl extends GenericServiceImpl<UsuarioEmpresa, Long> implements UsuarioEmpresaService {

    @Autowired
    private UsuarioEmpresaRepository repository;

    @Override
    public CrudRepository<UsuarioEmpresa, Long> getDao() {
        return repository;
    }
}
