# :chocolate_bar::chocolate_bar::chocolate_bar: Crud en Angular + Spring de Java + MySQL
>Esta guia esta basada en https://www.youtube.com/watch?v=O13X14TGtm8&ab_channel=SinFloo yo hize las mejoras

>Nota :eyes: : Yo tenia previamente instalado el NODE js, JDK 14 de java y el Apache Netbeans 12(creo que ya viene con maven, por que no instale maven) para que todo funcione bien.Tambien use el mysql de mi wamp que ya estaba instalado

Crear un nuevo proyecto angular con routing
```
ng new proyecto --routing=true
```
Iniciar el proyecto
```
ng serve
```
## Archivos
- `proyecto/src/app/app.component.html` (Archivo html principal del proyecto)

- para usar **Bootstrap** (estilo) solo debemos agregar la cdn al archivo `proyecto/src/index.html`
  
## Crear un componente
```
ng g c Persona/listar
```
## Crear un servicio
```
ng g s Service/service
```

## Crear rutas para los componentes creados
Modificar archivo: `proyecto/src/app/app-routing.module.ts`
``` ts
const routes: Routes = [
  {path:'listar',component:ListarComponent},
  {path:'add',component:AddComponent},
  {path:'edit',component:EditComponent}
];
```
## Agregamos las rutas de los componentes creados al proyecto
Modificar archivo:`proyecto/src/app/app.component.ts`
``` ts
import { Component } from '@angular/core';
import { Router } from '@angular/router';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {
  title = 'proyecto';
  //creamos el constructor donde definimos el enrutador
  constructor(private router:Router){}
  //metodo para acceder a la ruta del componente listar
  Listar(){
    this.router.navigate(["listar"]);
  }
  //metodo para acceder a la ruta del componente add
  Nuevo(){
    this.router.navigate(["add"]);
  }
}
```
## Crear la interfaz grafica del componente listar
Modificar archivo:`proyecto/src/app/Persona/listar/listar.component.html` este es el html del componente listar.
``` html
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
                    <tr class="text-center">
                        <td></td>
                        <td></td>
                        <td></td>
                        <td>
                            <button class="btn btn-warning">Editar</button>
                            <button class="btn btn-danger">Eliminar</button>
                        </td>
                    </tr>
                </tbody>
            </table>
        </div>
    </div>
</div>
``` 

## Crear modelos(clase) para traer data mediante el backend de la base de datos
- crear la carpeta `proyecto/src/app/Modelo`
- Crear archivo:`proyecto/src/app/Modelo/Persona.ts` crearemos el modelo de datos de una persona mediante una clase
- Contenido de Persona.ts
  ``` ts
  class Persona{
      id:number;
      name: string;
      apellidos: string;
  }
  export default Persona;
  ``` 
## Creamos un metodo en el servicio del proyecto para obtener datos del backend
Modificar archivo:`proyecto/src/app/Service/service.service.ts` 
``` ts
import { Injectable } from '@angular/core';
import {HttpClient} from '@angular/common/http';
import Persona from '../Modelo/Persona';

@Injectable({
  providedIn: 'root'
})
export class ServiceService {
  //definimos en el constructor el HttpClient para conectarnos al backend por REST(web service)
  constructor(private http:HttpClient) { }
  
  //ruta del backend
  Url='http://localhost:8070/personas';//ruta del proyecto backend en Java
  
  // metodo para obtener datos de las personas del backend
  getPersonas(){
    return this.http.get<Persona[]>(this.Url);
  }
}
```
## Creacion de la base de datos
- crear la base de datos `bd_rest_spring` en mysql
- crear la tabla persona

|  persona |
|--|
| `id` int AUTO_INCREMENT :key: |
| `name` varchar(255)|
| `apellidos` varchar(255)|

- Insertamos data de prueba en la tabla persona

|id|name|apellidos|
|--|--|--|
|1|JUAN|PEREZ|
|2|PEPE|GARCIA|
## crear el backend (JAVA) :coffee:
Generar el proyecto en https://start.spring.io/ con las siguientes opciones:

- maven project (x)
- java 14(x)
- spring boot: 2.3.3 (x)
- Group: com.organitiempo
- Artifact: res
- agregar dependencias web,mysql,jpa

>nota: :eyes: yo instale el JDK de java 14, y el apache netbeans 12,y cuando abri el proyecto le puse resolve para solucionar los errores del Maven POM

Modificar archivo:`nombreproyecto\src\main\resources\application.properties`  Other Sources
```
server.contextPath=/rest                                                    /*nombre del projecto */
spring.datasource.url=jdbc:mysql://localhost:3306/bd_rest_spring?serverTimezone=UTC           /*nombre de la bd*/
spring.datasource.username=root                                             /*usuario de la bd*/
spring.datasource.password=                                                 /*constraseña de la bd*/
spring.datasource.driver-class-name=com.mysql.jdbc.Driver                   /*driver para conectar a mysql*/
server.port = 8070                                                          /* cambiamos de puerto si hay conflicto*/
```
Compilar el archivo anterior `application.properties`

Creamos la clase `Persona` en el proyecto 
``` java
package com.organitiempo.rest;

import javax.persistence.*;

@Entity @Table(name="persona")

public class Persona {

    @Id @Column @GeneratedValue(strategy=GenerationType.IDENTITY) private int id;
    @Column private String name;
    @Column private String apellidos;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }
}
``` 
Creamos la Interfaz `PersonaService` 
``` java
package com.organitiempo.rest;

import java.util.List;

public interface PersonaService {
    // objeto de tipo List
    List<Persona>listar(); //listar todas las personas
    // objeto de tipo persona
    Persona listarId(int id); //listar una persona
    Persona add(Persona p);//agregar una persona
    Persona edit(Persona p);//editar una persona
    Persona delete(int id);//eliminar una persona
}
``` 
Creamos la Interfaz `PersonaRepositorio` 
``` java
package com.organitiempo.rest;

import java.util.List;
import org.springframework.data.repository.Repository;

public interface PersonaRepositorio extends Repository<Persona, Integer>{
    //para listar todas las personas
    List<Persona>findAll();
    //para listar una persona
    Persona findByid(int id);// nota!!!!! el metodo findOne genera error grave es por eso que no se usa en su lugar usamos findByid
    //para guardar cambios nuevos o actualizar
    Persona save(Persona p);
    //para eliminar
    void delete(Persona p);
    
}
```
Creamos la la clase  `PersonaServiceImp` 
``` java
package com.organitiempo.rest;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service



public class PersonaServiceImp implements PersonaService{
    
    @Autowired
    private PersonaRepositorio repositorio; 
    
    
    @Override
    public List<Persona> listar() {
        return repositorio.findAll();
    }

    @Override
    public Persona listarId(int id) {
        throw new UnsupportedOperationException("Not supported yet."); 
    }

    @Override
    public Persona add(Persona p) {
        throw new UnsupportedOperationException("Not supported yet."); 
    }

    @Override
    public Persona edit(Persona p) {
        throw new UnsupportedOperationException("Not supported yet."); 
    }

    @Override
    public Persona delete(int id) {
        throw new UnsupportedOperationException("Not supported yet."); 
    }
    
}

```
Creamos la la clase  `Controlador` 
``` java

package com.organitiempo.rest;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
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
}

```
> con todo lo anterior el Backend ya esta terminado para comprobar su correcto funcionamiento deber traer datos en la ruta http://localhost:8070/personas

## Continuamos con el front end para mostrar los datos en la web

Modificamos el archivo `proyecto/src/app/Persona/listar/listar.component.html` para que se muestren (listar) los datos traidos del backend
``` html
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
                            <button class="btn btn-warning">Editar</button>
                            <button class="btn btn-danger">Eliminar</button>
                        </td>
                    </tr>
                </tbody>
            </table>
        </div>
    </div>
</div>
```
Modificamos el archivo `proyecto/src/app/Persona/listar/listar.component.ts` para agregar el servicio y enrutamiento
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

}
```

Modificar el archivo `proyecto/src/app/app.module.ts` para referenciar las librerias y elementos que nos permitiran conectar con el back end
``` ts
import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { ListarComponent } from './Persona/listar/listar.component';
import { AddComponent } from './Persona/add/add.component';
import { EditComponent } from './Persona/edit/edit.component';
import {FormsModule} from '@angular/forms';
import {ServiceService} from '../app/Service/service.service';
import {HttpClientModule} from '@angular/common/http';

@NgModule({
  declarations: [
    AppComponent,
    ListarComponent,
    AddComponent,
    EditComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    FormsModule,
    HttpClientModule
  ],
  providers: [ServiceService],
  bootstrap: [AppComponent]
})
export class AppModule { }
```

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
## Implementamos el metodo Eliminar
>Ojo :eyes: : Eliminar no es un componente, no necesita interfaz ya que se realiza directamente del listado

En la interfaz de listar modificar el boton de eliminar, modificar archivo `proyecto/src/app/Persona/listar/listar.component.html`
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
                            <button (click)="Delete(persona)" class="btn btn-danger">Eliminar</button>
                        </td>
                    </tr>
                </tbody>
            </table>
        </div>
    </div>
</div>

```
Modificar el servicio `proyecto/src/app/Service/service.service.ts`
```ts
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
  deletePersona(persona:Persona){
    return this.http.delete<Persona>(this.Url+"/"+persona.id);
  }
}
```
Modificar el archivo `proyecto/src/app/Persona/listar/listar.component.ts` para implementar el boton eliminar del listado
```ts
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

  Delete(persona:Persona){
    this.service.deletePersona(persona)
    .subscribe(data=>{
      this.personas=this.personas.filter(p=>p!==persona);
      alert("Usuario eliminando....");
    })
  }
}
```
# Modificar backend
Modificar la clase `PersonaServiceImp.java`
```java

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
       Persona p=repositorio.findByid(id);
       if(p!=null){
           repositorio.delete(p);
       }
       return p;
    }
    
}

```

Modificar el controlador `Controlador.java`

```java
package com.organitiempo.rest;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
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
    @DeleteMapping(path={"/{id}"})
    public Persona delete(@PathVariable("id") int id){
        return service.delete(id);
    }
}

```
