package com.obstetricia.segurity.obstetricia.servicio;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.obstetricia.segurity.obstetricia.modelo.Paciente;
import com.obstetricia.segurity.obstetricia.repositorio.PacienteRepositorio;

@Service
public class PacienteServicio {

    @Autowired
    private PacienteRepositorio pacienteRepository;

    public Optional<Paciente> buscarPorRut(String rut) {
        return pacienteRepository.findByRut(rut);
    }
    public Page<Paciente> findAll(Pageable pageable) {
    return pacienteRepository.findAll(pageable);
}
       public List<Paciente> listarPacientes() {
        return pacienteRepository.findAll();
    }
    public void eliminarPaciente(Long idPaciente) {
    pacienteRepository.deleteById(idPaciente);
}


}
