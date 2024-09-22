import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { PractitionerRoutingModule } from './practitioner-routing.module';
import { PractitionerListComponent } from './pages/practitioner-list/practitioner-list.component';
import { PractitionerDetailsComponent } from './pages/practitioner-details/practitioner-details.component';
import { SharedModule } from 'src/app/shared/shared.module';
import { PractitionerFormComponent } from './pages/practitioner-form/practitioner-form.component';


@NgModule({
  declarations: [
    PractitionerListComponent,
    PractitionerDetailsComponent,
    PractitionerFormComponent
  ],
  imports: [
    CommonModule,
    PractitionerRoutingModule,
    SharedModule
  ]
})
export class PractitionerModule { }
