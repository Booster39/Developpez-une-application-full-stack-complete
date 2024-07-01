import { NgModule } from '@angular/core';
import { MatButtonModule } from '@angular/material/button';
import { BrowserModule } from '@angular/platform-browser';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { HomeComponent } from './pages/home/home.component';
import { MatGridListModule } from '@angular/material/grid-list';
import { SessionService } from './services/session.service';
import { MatCardModule } from '@angular/material/card';
import { MatIconModule } from '@angular/material/icon';
import { NotFoundComponent } from './components/not-found/not-found.component';
import { HTTP_INTERCEPTORS, HttpClientModule } from '@angular/common/http';
import { JwtInterceptor } from './interceptors/jwt.interceptor';
import { MatToolbarModule } from '@angular/material/toolbar';
import { FlexLayoutModule } from '@angular/flex-layout';

const materialModule = [
  MatButtonModule,
  MatCardModule,
  MatIconModule,
  MatToolbarModule,
]

@NgModule({
  declarations: [
    AppComponent,
     HomeComponent,
     NotFoundComponent],
  imports: [
    BrowserModule,
    AppRoutingModule,
    BrowserAnimationsModule,
    MatGridListModule,
    //FlexLayoutModule,
    HttpClientModule,
    ...materialModule
  ],
  providers: [
    { provide: HTTP_INTERCEPTORS, useClass: JwtInterceptor, multi: true,  },
    SessionService],
  bootstrap: [AppComponent],
})
export class AppModule {}
