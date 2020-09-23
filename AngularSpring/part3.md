## Implementamos el componente edit
Modificar la interfaz de listar `proyecto/src/app/Persona/listar/listar.component.html` para implementar el boton editar  del listado

```html 
<div class="container">
    <div class="card">
        <div class="card-header">
            <h3>Personas</h3>
        </div>
        <div class="card-body">
            <table class="table table-hover">
                <thead>
                    <tr>
                        <th>ID</th>
                        <th>NOMBRES</th>
                        <th>APELLIDOS</th>
                        <th>ACCIONES</th>
                    </tr>
                </thead>
                <tbody>
                    <tr *ngFor="let persona of personas" class="text-center">
                        <td>{{persona.id}}</td>
                        <td>{{persona.name}}</td>
                        <td>{{persona.apellidos}}</td>
                        <td>
                            <button (click)="Editar(persona)" class="btn btn-warning">Editar</button>
                            <button class="btn btn-danger">Eliminar</button>
                        </td>
                    </tr>
                </tbody>
            </table>
        </div>
    </div>
</div>

```
Modificar el servicio `proyecto/src/app/Service/service.service.ts`
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

  getPersonaId(id:number){
    return this.http.get<Persona>(this.Url+"/"+id);
  }

  updatePersona(persona:Persona){
    return this.http.put<Persona>(this.Url+"/"+persona.id,persona)
  }
}
```
Modificar el archivo `proyecto/src/app/Persona/listar/listar.component.ts`para implementar el boton editar del listado

``` ts
import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import {ServiceService} from '../../Service/service.service';
import Persona from 'src/app/Modelo/Persona';


@Component({
  selector: 'app-listar',
  templateUrl: './listar.component.html',
  styleUrls: ['./listar.component.css']
})
export class ListarComponent implements OnInit {

  personas:Persona[];
  constructor(private service:ServiceService,private router:Router) { }

  ngOnInit(): void {
    this.service.getPersonas()
    .subscribe(data=>{
      this.personas=data;
    })
  }

  Editar(persona:Persona):void{
    localStorage.setItem("id",persona.id.toString());
    this.router.navigate(["edit"]);
  }
}
```
Modificar el archivo `proyecto/src/app/Persona/edit/edit.component.ts`para implementar la funcionalidad del componente listar

``` ts
import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import Persona from 'src/app/Modelo/Persona';
import { ServiceService } from 'src/app/Service/service.service';

@Component({
  selector: 'app-edit',
  templateUrl: './edit.component.html',
  styleUrls: ['./edit.component.css']
})
export class EditComponent implements OnInit {

  persona:Persona = new Persona();
  constructor(private router:Router,private service:ServiceService) { }

  ngOnInit(): void {
    this.Editar();
  }

  Editar(){
    let id=localStorage.getItem("id");
    this.service.getPersonaId(+id)
    .subscribe(data=>{
      this.persona=data;
    })
  }

  Actualizar(persona:Persona){
    this.service.updatePersona(persona)
    .subscribe(data=>{
      this.persona=data;
      alert("Se Actualizo con Exito...!!!");
      this.router.navigate(["listar"]);
    })
  }
}
```
Modificar el archivo `proyecto/src/app/Persona/edit/edit.component.html`para implementar la interfaz del componente listar

``` html

<div class="container col-md-6">
    <div class="card">
        <div class="card-header">
            <h3>Modificar Datos</h3>
        </div>
        <div class="card-body">
            <form action="">
                <div class="form-group">
                    <label for="">Nombres:</label>
                    <input [(ngModel)]="persona.name" name="name" type="text" class="form-control">
                </div>
                <div class="form-group">
                    <label for="">Apellidos:</label>
                    <input [(ngModel)]="persona.apellidos" name="apellidos" cltype="text" class="form-control">
                    <button (click)="Actualizar(persona)" class="btn btn-danger">Guardar</button>
                </div>
            </form>
        </div>
    </div>
</div>

```

## Modificaciones en el backen para editar

Modificar la clase `PersonaServiceImp.java`

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
     return repositorio.findByid(id);
    }

    @Override
    public Persona add(Persona p) {
        return repositorio.save(p);
    }

    @Override
    public Persona edit(Persona p) {
        return repositorio.save(p);
    }

    @Override
    public Persona delete(int id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
```

Modificar el controlador `Controlador.java`

``` java
package com.organitiempo.rest;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "http://localhost:4200",maxAge = 3600)
@RestController
@RequestMapping({"/personas"})
public class Controlador {
    @Autowired
    PersonaService service;
    
    @GetMapping
    public List<Persona>listar(){
        return service.listar();
    }
    @PostMapping
    public Persona agregar(@RequestBody Persona p){
        return service.add(p);
    }
    @GetMapping(path={"/{id}"})
    public Persona listarID(@PathVariable("id") int id){
        return service.listarId(id);
    }
    
    @PutMapping(path = {"/{id}"})
    public Persona editar(@RequestBody Persona p,@PathVariable("id") int id){
        p.setId(id);
        return service.edit(p);
    }
}
```
