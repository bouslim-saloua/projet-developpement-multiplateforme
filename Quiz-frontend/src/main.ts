import { bootstrapApplication } from '@angular/platform-browser';
import { AppComponent } from './app/app.component';  // Import your standalone AppComponent
import { provideRouter } from '@angular/router';  // Import router provider
import { routes } from './app/app.routes';  // Import your app's routes

// Use bootstrapApplication to bootstrap the AppComponent
bootstrapApplication(AppComponent, {
  providers: [
    provideRouter(routes),  // Provide routes to the application
  ]
}).catch(err => console.error(err));
