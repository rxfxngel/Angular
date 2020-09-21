
package com.organitiempo.rest;

import java.util.List;

public interface PersonaService {
    // objeto de tipo List
    List<Persona>listar();
    // objeto de tipo persona
    Persona listarId(int id);
    Persona add(Persona p);
    Persona edit(Persona p);
    Persona delete(int id);
}
