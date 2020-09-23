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
