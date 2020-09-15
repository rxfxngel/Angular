## Implementando el componente add (para crear personas en la aplicacion)

Modificamos el archivo `proyecto/src/app/Persona/add/add.component.html` para diseñar la interfaz del componente guardar

```html
<div class="container col-md-6">
    <div class="card">
        <div class="card-header">
            <h3>Agregar Personas</h3>
        </div>
        <div class="card-body">
            <form action="">
                <div class="form-group">
                    <label for="">Nombres</label>
                    <input [(ngModel)]="persona.name" name="name" type="text" class="form-control">
                </div>
                <div class="form-group">
                    <label for="">Nombres</label>
                    <input [(ngModel)]="persona.apellidos" name="apellidos" cltype="text" class="form-control">
                    <button (click)="Guardar()" class="btn btn-danger">Guardar</button>
                </div>
            </form>
        </div>
    </div>
</div>
```
Modificamos el archivo `proyecto/src/app/service/service.service.ts` para implementar en el servicio el metodo createPersona
``` ts
import { Injectable } from '@angular/core';
import {HttpClient} from '@angular/common/http';
import Persona from '../Modelo/Persona';

@Injectable({
  providedIn: 'root'
})
export class ServiceService {

  constructor(private http:HttpClient) { }
  Url='http://localhost:8070/personas';

  getPersonas(){
    return this.http.get<Persona[]>(this.Url);
  }

  createPersona(persona:Persona){
    return this.http.post<Persona>(this.Url,persona);
  }
}

```
Modificamos el archivo `proyecto/src/app/Persona/add/add.component.ts` para implementar la funcionalidad del componente add
``` ts
import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import Persona from 'src/app/Modelo/Persona';
import { ServiceService } from 'src/app/Service/service.service';

@Component({
  selector: 'app-add',
  templateUrl: './add.component.html',
  styleUrls: ['./add.component.css']
})
export class AddComponent implements OnInit {

  persona:Persona= new Persona();

  constructor(private router:Router, private service:ServiceService) { }

  ngOnInit(): void {
  }
  Guardar(){
    this.service.createPersona(this.persona)
    .subscribe(data=>{
      alert("se agrego con exito");
      this.router.navigate(["listar"]);
    })
  }

}

```
## Modificamos el Backend

Modificar el archivo `PersonaServiceImp.java`
``` java
package com.organitiempo.rest;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
/**
 *
 * @author RAFAEL
 */
public class PersonaServiceImp implements PersonaService{
    @Autowired
    private PersonaRepositorio repositorio; 
    
    @Override
    public List<Persona> listar() {
       return repositorio.findAll();
    }

    @Override
    public Persona listarId(int id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Persona add(Persona p) {
        return repositorio.save(p);
    }

    @Override
    public Persona edit(Persona p) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Persona delete(int id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
```
