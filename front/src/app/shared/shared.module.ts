import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { OwnerInfoComponent } from './components/owner-info/owner-info.component';
import { MatCardModule } from '@angular/material/card';
import { CommentsComponent } from './components/comments/comments.component';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatIconModule } from '@angular/material/icon';

@NgModule({
  declarations: [
    OwnerInfoComponent,
    CommentsComponent
  ],
  imports: [
    CommonModule,
    MatCardModule,
    MatFormFieldModule,
    MatIconModule
  ],
  exports: [
    OwnerInfoComponent
  ],
})
export class SharedModule { }
