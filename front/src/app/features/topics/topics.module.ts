import { LOCALE_ID, NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { TopicRoutingModule } from './topic-routing.module';
import { ListComponent } from './components/list/list.component';
import { MatCardModule } from '@angular/material/card';
import { MatButtonModule } from '@angular/material/button';
import { MatIconModule } from '@angular/material/icon';
import { MatSnackBarModule } from '@angular/material/snack-bar';
import { registerLocaleData } from '@angular/common';
import localeFr from '@angular/common/locales/fr';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { SharedModule } from 'src/app/shared/shared.module';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatToolbarModule } from '@angular/material/toolbar';
import { MatTabsModule } from '@angular/material/tabs'; 
import { MatGridListModule } from '@angular/material/grid-list';
import { MatOptionModule } from '@angular/material/core';
import {MatMenuModule} from '@angular/material/menu';

registerLocaleData(localeFr);

const materialModules = [
  MatButtonModule,
  MatCardModule,
  MatFormFieldModule,
  MatIconModule,
  MatInputModule,
  MatSnackBarModule,
  MatToolbarModule,
  MatTabsModule,
  MatGridListModule,
  MatOptionModule,
  MatMenuModule
];

@NgModule({
  declarations: [
    ListComponent,
  ],
  imports: [
    CommonModule,
    FormsModule,
    ReactiveFormsModule,
    TopicRoutingModule,
    SharedModule,
    ...materialModules
  ],
  providers: [
    {
      provide: LOCALE_ID,
      useValue: 'fr-FR'
    },
  ],
})
export class TopicsModule { }
