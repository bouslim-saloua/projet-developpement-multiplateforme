import { Routes } from '@angular/router';
import { HomeComponent } from './components/client/home/home.component';  
import { DashboardComponent } from './components/manager/dashboard/dashboard.component';
export const routes: Routes = [
  { path: 'client', component: HomeComponent },
  { path: 'manager', component: DashboardComponent },  
  { path: '', redirectTo: '/client', pathMatch: 'full' },
  { path: '**', redirectTo: '/client' }
];
