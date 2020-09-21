# Componente Eliminar

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
