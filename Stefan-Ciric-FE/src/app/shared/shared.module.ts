import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { RouterModule } from '@angular/router';
import { HttpClientModule } from '@angular/common/http';
import { SortableHeaderDirective } from './directives/sortable-header.directive';
import { NgbDropdownModule, NgbModalModule, NgbNavModule, NgbPaginationModule, NgbToastModule, NgbTooltipModule } from '@ng-bootstrap/ng-bootstrap';
import { ConfirmDialogComponent } from './components/confirm-dialog/confirm-dialog.component';
import { FooterComponent } from './footer/footer.component';
import { NavBarComponent } from './nav-bar/nav-bar.component';
import { GlobalToastComponent } from './components/global-toast/global-toast.component';


@NgModule({
  declarations: [
    SortableHeaderDirective,
    GlobalToastComponent,
    ConfirmDialogComponent,
    FooterComponent,
    NavBarComponent
  ],
  imports: [
    CommonModule,
    FormsModule,
    ReactiveFormsModule,
    RouterModule,
    HttpClientModule,
    NgbTooltipModule,
    NgbToastModule,
    NgbNavModule,
    NgbDropdownModule,
    NgbPaginationModule,
    NgbModalModule,
    NgbPaginationModule
  ],
  exports:[
    CommonModule,
    FormsModule,
    ReactiveFormsModule,
    RouterModule,
    HttpClientModule,
    NgbTooltipModule,
    NgbToastModule,
    NgbNavModule,
    NgbDropdownModule,
    NgbPaginationModule,
    NgbModalModule,
    NavBarComponent,
    FooterComponent,
    NgbPaginationModule,
    SortableHeaderDirective,
    FooterComponent,
    NavBarComponent,
    GlobalToastComponent

  ]
})
export class SharedModule { }
