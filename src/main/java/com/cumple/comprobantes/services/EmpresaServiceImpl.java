/*
 * Copyright (c) 2024 Luis Chumi.
 * Este software está licenciado bajo la Licencia Pública General de GNU versión 3.
 * Puedes encontrar una copia de la licencia en https://www.gnu.org/licenses/gpl-3.0.html.
 *
 * Para consultas o comentarios, puedes contactarme en luischumi.9@gmail.com
 */

package com.cumple.comprobantes.services;

import com.cumple.comprobantes.model.entity.Empresa;
import com.cumple.comprobantes.repository.EmpresaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;

@Service
public class EmpresaServiceImpl extends GenericServiceImpl<Empresa,Long> implements EmpresaService {

    @Autowired
    private EmpresaRepository empresaRepository;
    @Override
    public CrudRepository<Empresa, Long> getDao() {
        return empresaRepository;
    }
}
