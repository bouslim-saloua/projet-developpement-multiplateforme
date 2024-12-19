import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { HomeComponent } from './home/home.component';  // Import HomeComponent

// Define routes for the client area
const routes: Routes = [
  { path: '', component: HomeComponent }  // Default route for client
];

@NgModule({
  imports: [RouterModule.forChild(routes)],  // Use RouterModule.forChild for lazy-loaded routes
  exports: [RouterModule]
})
export class ClientRoutingModule {}
