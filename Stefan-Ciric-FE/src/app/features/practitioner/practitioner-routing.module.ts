import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { PractitionerDetailsComponent } from './pages/practitioner-details/practitioner-details.component';
import { PractitionerFormComponent } from './pages/practitioner-form/practitioner-form.component';
import { PractitionerListComponent } from './pages/practitioner-list/practitioner-list.component';
import { PractitionerListResolver } from './resolvers/practitioner-list.resolver';

const routes: Routes = [
  {path:'practitioner-list',component:PractitionerListComponent,resolve: { practitionerPage: PractitionerListResolver }},
  {path:'practitioner-details/:id',component:PractitionerDetailsComponent},
  {path:'practitioner-form', component:PractitionerFormComponent, data: {edit: false}},
  {path:'practitioner-form/:id', component:PractitionerFormComponent, data: {edit: true}},
  {path:'',redirectTo:'practitioner-list',pathMatch:'full'}
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class PractitionerRoutingModule { }
