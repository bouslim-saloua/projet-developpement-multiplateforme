import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ClientRoutingModule } from './client-routing.module';  // Import client routing
import { HomeComponent } from './home/home.component';  // Import HomeComponent (standalone component)

@NgModule({
  imports: [
    CommonModule,
    ClientRoutingModule,  // Import the routing module for client-specific routes
    HomeComponent  // Import HomeComponent directly here, no need to declare it
  ]
})
export class ClientModule {}
