import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { ExaminationDetailsComponent } from './pages/examination-details/examination-details.component';
import { ExaminationFormComponent } from './pages/examination-form/examination-form.component';
import { ExaminationListComponent } from './pages/examination-list/examination-list.component';
import { ExaminationListResolver } from './resolvers/examination-list.resolver';

const routes: Routes = [
  {path: 'examination-list', component: ExaminationListComponent, resolve: { examinationPage: ExaminationListResolver }},
  {path: 'examination-details/:id', component: ExaminationDetailsComponent},
  {path:'examination-form', component:ExaminationFormComponent, data: {edit: false}},
  {path:'examination-form/:id', component:ExaminationFormComponent, data: {edit: true}},
  {path:'', redirectTo:'examination-list', pathMatch:'full'}
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class ExaminationRoutingModule { }
