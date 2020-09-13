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
```
const routes: Routes = [
  {path:'listar',component:ListarComponent},
  {path:'add',component:AddComponent},
  {path:'edit',component:EditComponent}
];
```

