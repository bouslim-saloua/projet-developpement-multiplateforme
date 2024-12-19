import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

const routes: Routes = [
  { path: 'admin', loadChildren: () => import('./components/manager/manager.module').then(m => m.ManagerModule) },
  { path: 'client', loadChildren: () => import('./components/client/client.module').then(m => m.ClientModule) },
  { path: '**', redirectTo: 'client', pathMatch: 'full' }  
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule {}
