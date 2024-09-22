import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { SharedModule } from 'src/app/shared/shared.module';
import { OrganizationRoutingModule } from './organization-routing.module';
import { OrganizationListComponent } from './pages/organization-list/organization-list.component';
import { OrganizationDetailsComponent } from './pages/organization-details/organization-details.component';
import { OrganizationFormComponent } from './pages/organization-form/organization-form.component';


@NgModule({
  declarations: [
    OrganizationListComponent,
    OrganizationDetailsComponent,
    OrganizationFormComponent
  ],
  imports: [
    CommonModule,
    OrganizationRoutingModule,
    SharedModule
  ]
})
export class OrganizationModule { }
