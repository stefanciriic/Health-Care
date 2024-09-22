import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { AuthGuard } from './core/guards/auth.guard';
import { LoginComponent } from './pages/login/login.component';

const routes: Routes = [
  {path:'login', component: LoginComponent},
  {path:'', component: LoginComponent},
  {
    path: 'organization',
    loadChildren: () =>
      import('./features/organization/organization.module').then(
        (o) => o.OrganizationModule
      ),canActivate: [AuthGuard]
  },
  {
    path: 'patient',
    loadChildren: () =>
      import('./features/patient/patient.module').then((p) => p.PatientModule),canActivate: [AuthGuard]
  },
  {
    path: 'home',
    loadChildren: () =>
      import('./features/home/home.module').then((h) => h.HomeModule),canActivate: [AuthGuard]
  },
  {
    path: 'practitioner',
    loadChildren: () =>
      import('./features/practitioner/practitioner.module').then(
        (p) => p.PractitionerModule
      ),canActivate: [AuthGuard]
  },{
    path: 'examination',
    loadChildren: () =>
      import('./features/examination/examination.module').then(
        (p) => p.ExaminationModule
      ),canActivate: [AuthGuard]
  },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule],
})
export class AppRoutingModule {}
