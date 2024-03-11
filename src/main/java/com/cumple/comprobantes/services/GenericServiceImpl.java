/*
 * Copyright (c) 2024 Luis Chumi.
 * Este software está licenciado bajo la Licencia Pública General de GNU versión 3.
 * Puedes encontrar una copia de la licencia en https://www.gnu.org/licenses/gpl-3.0.html.
 *
 * Para consultas o comentarios, puedes contactarme en luischumi.9@gmail.com
 */

package com.cumple.comprobantes.services;

import org.springframework.data.repository.CrudRepository;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public abstract class GenericServiceImpl<T ,ID extends Serializable> implements GenericService<T, ID> {

    public abstract CrudRepository<T,ID> getDao();
    @Override
    public T save(T entity) {
        return getDao().save(entity);
    }

    @Override
    public T findById(ID id) {
        Optional<T> obj = getDao().findById(id);
        return obj.orElse(null);
    }

    @Override
    public List<T> findByAll() {
        List<T> list= new ArrayList<>();
        getDao().findAll().forEach(list::add);
        return list;
    }

    @Override
    public void delete(ID id) {
        getDao().deleteById(id);
    }
}
