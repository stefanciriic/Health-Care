import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { PatientRoutingModule } from './patient-routing.module';
import { PatientListComponent } from './pages/patient-list/patient-list.component';
import { PatientDetailsComponent } from './pages/patient-details/patient-details.component';
import { SharedModule } from 'src/app/shared/shared.module';
import { PatientFormComponent } from './pages/patient-form/patient-form.component';


@NgModule({
  declarations: [
    PatientListComponent,
    PatientDetailsComponent,
    PatientFormComponent
  ],
  imports: [
    CommonModule,
    PatientRoutingModule,
    SharedModule
  ]
})
export class PatientModule { }
