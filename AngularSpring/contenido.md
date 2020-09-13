# :chocolate_bar::chocolate_bar::chocolate_bar: Crud en Angular + Spring de Java + MySQL
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
## Crear la interfaz grafica de un componente
Modificar archivo:`proyecto/src/app/listar/listar.component.html` este es el html del componente listar.
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
  Url='http://localhost:8080/Ejemplo01/personas';
  
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
## crear la estructura del proyecto backend
Generar el proyecto en https://start.spring.io/ con las siguientes opciones:

- maven project (x)
- java (x)
- spring boot: 2.3.3 (x)
- Group: com.organitiempo
- Artifact: res
- agregar dependencias web,mysql,jpa

>nota: :eyes: yo instale el JDK de java 14, y el apache netbeans 12,y cuando abri el proyecto le puse resolve para solucionar los errores del Maven POM

Modificar archivo:`nombreproyecto\src\main\resources\application.properties`  Other Sources
```
server.contextPath=/rest                                                    /*nombre del projecto */
spring.datasource.url=jdbc:mysql://localhost:3306/bd_rest_spring            /*nombre de la bd*/
spring.datasource.username=root                                             /*usuario de la bd*/
spring.datasource.password=                                                 /*constraseña de la bd*/
spring.datasource.driver-class-name=com.mysql.jdbc.Driver                   /*driver para conectar a mysql*/
server.port = 8070                                                          /* cambiamos de puerto si hay conflicto*/
```
Compilar el archivo anterior

>nota : tuve problemas con la zona horaria del mysql al compilar , para arreglarlo ejecute el siguiente comando en la base de datos
``` sql
SET @@global.time_zone = ‘+00:00’;
SET @@session.time_zone = ‘+00:00’;
```
