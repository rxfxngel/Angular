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
                    <input type="text" class="form-control">
                </div>
                <div class="form-group">
                    <label for="">Nombres</label>
                    <input type="text" class="form-control">
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
