package com.obstetricia.segurity.obstetricia.servicio;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.obstetricia.segurity.obstetricia.modelo.ControlEmbarazo;
import com.obstetricia.segurity.obstetricia.repositorio.ControlEmbarazoRepositorio;

@Service
public class ControlEmbarazoServicio {

    @Autowired
    private ControlEmbarazoRepositorio repositorio;

    public List<ControlEmbarazo> listarTodos() {
        return repositorio.findAll();
    }

    public ControlEmbarazo guardar(ControlEmbarazo control) {
        return repositorio.save(control);
    }

    public void eliminarPorId(Long id) {
        repositorio.deleteById(id);
    }

    public ControlEmbarazo obtenerPorId(Long id) {
        return repositorio.findById(id).orElse(null);
    }
}