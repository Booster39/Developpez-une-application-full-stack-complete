import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { HomeComponent } from './pages/home/home.component';
import { NotFoundComponent } from './components/not-found/not-found.component';
import { AuthGuard } from './guards/auth.guard';
import { UnauthGuard } from './guards/unauth.guard';
import { MeComponent } from './components/me/me.component';

// consider a guard combined with canLoad / canActivate route option
// to manage unauthenticated user to access private routes
const routes: Routes = [{ path: '', component: HomeComponent },
  {
    path: 'articles',
    canActivate: [AuthGuard],
    loadChildren: () => import('./features/articles/articles.module').then(m => m.ArticlesModule)
  },
  {
    path: '',
    canActivate: [UnauthGuard],
    loadChildren: () => import('./features/auth/auth.module').then(m => m.AuthModule)
  },
  {
    path: 'me',
    canActivate: [AuthGuard], 
    component: MeComponent
  },
  { path: '404', component: NotFoundComponent },
  { path: '**', redirectTo: '404' }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule],
})
export class AppRoutingModule {}
