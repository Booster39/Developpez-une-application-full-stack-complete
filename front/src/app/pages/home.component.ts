import { Component, OnInit } from '@angular/core';
import { MatCardModule } from '@angular/material/card';
import { Router } from '@angular/router';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.scss'],
  
})
export class HomeComponent implements OnInit {
  constructor(private router: Router){
  }

  ngOnInit(): void {}
  goToPage(pageName:string){
    this.router.navigate([`${pageName}`]);
  }
}