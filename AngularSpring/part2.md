## Implementando el componente guardar

Modificamos el archivo `proyecto/src/app/listar/listar.component.html` para diseñar la interfaz del componente guardar

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
