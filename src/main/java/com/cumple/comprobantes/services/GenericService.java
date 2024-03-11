/*
 * Copyright (c) 2024 Luis Chumi.
 * Este software está licenciado bajo la Licencia Pública General de GNU versión 3.
 * Puedes encontrar una copia de la licencia en https://www.gnu.org/licenses/gpl-3.0.html.
 *
 * Para consultas o comentarios, puedes contactarme en luischumi.9@gmail.com
 */

package com.cumple.comprobantes.services;

import java.io.Serializable;
import java.util.List;

public interface GenericService <T,ID extends Serializable> {

    public T save(T entity);
    public T findById(ID id);
    public List<T> findByAll();
    public void delete(ID id);

}
