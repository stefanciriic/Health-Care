import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { ExaminationRoutingModule } from './examination-routing.module';
import { ExaminationDetailsComponent } from './pages/examination-details/examination-details.component';
import { ExaminationListComponent } from './pages/examination-list/examination-list.component';
import { SharedModule } from 'src/app/shared/shared.module';
import { ExaminationFormComponent } from './pages/examination-form/examination-form.component';


@NgModule({
  declarations: [
    ExaminationDetailsComponent,
    ExaminationListComponent,
    ExaminationFormComponent
  ],
  imports: [
    CommonModule,
    ExaminationRoutingModule,
    SharedModule
  ]
})
export class ExaminationModule { }
