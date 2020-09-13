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
  //creamos el constructor donde definimos en el enrutador
  constructor(private router:Router){}
  //metodo para acceder a la ruta al compente listar
  Listar(){
    this.router.navigate(["listar"]);
  }
  //metodo para acceder a la ruta al compente add
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
