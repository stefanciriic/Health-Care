import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { OrganizationDetailsComponent } from './pages/organization-details/organization-details.component';
import { OrganizationFormComponent } from './pages/organization-form/organization-form.component';
import { OrganizationListComponent } from './pages/organization-list/organization-list.component';
import { OrganizationListResolver } from './resolvers/organization-list.resolver';

const routes: Routes = [
  {path: 'organization-list', component: OrganizationListComponent, resolve: { organizationPage: OrganizationListResolver }},
  {path: 'organization-details/:id', component: OrganizationDetailsComponent},
  {path:'organization-form', component:OrganizationFormComponent, data: {edit: false}},
  {path:'organization-form/:id', component:OrganizationFormComponent, data: {edit: true}},
  {path:'', redirectTo:'organization-list', pathMatch:'full'}
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class OrganizationRoutingModule { }
