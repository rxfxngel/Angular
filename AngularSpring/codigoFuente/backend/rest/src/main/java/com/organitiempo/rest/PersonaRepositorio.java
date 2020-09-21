package com.organitiempo.rest;

import java.util.List;
import org.springframework.data.repository.Repository;



public interface PersonaRepositorio extends Repository<Persona, Integer>{
    //para listar todas las personas
    List<Persona>findAll();
    //para listar una persona
    Persona findByid(int id);
    //para guardar cambios nuevos o actualizar
    Persona save(Persona p);
    //para eliminar
    void delete(Persona p);

}