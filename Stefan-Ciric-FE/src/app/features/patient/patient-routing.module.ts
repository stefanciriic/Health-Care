import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { PatientDetailsComponent } from './pages/patient-details/patient-details.component';
import { PatientFormComponent } from './pages/patient-form/patient-form.component';
import { PatientListComponent } from './pages/patient-list/patient-list.component';
import { PatientListResolver } from './resolvers/patient-list.resolver';

const routes: Routes = [
  {path:'patient-list',component:PatientListComponent,resolve: { patientPage: PatientListResolver }},
  {path:'patient-details/:id',component:PatientDetailsComponent},
  {path:'patient-form', component:PatientFormComponent, data: {edit: false}},
  {path:'patient-form/:id', component:PatientFormComponent, data: {edit: true}},
  {path:'',redirectTo:'patient-list',pathMatch:'full'}
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class PatientRoutingModule { }
