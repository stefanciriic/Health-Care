import { Component, OnInit, QueryList, ViewChildren } from '@angular/core';
import { Organization } from 'src/app/core/models/organization.model';
import { ActivatedRoute, Router } from '@angular/router';
import { HttpOrganizationService } from 'src/app/core/services/http-organization.service';
import { PageDto } from 'src/app/core/models/page.dto';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { catchError, take, throwError } from 'rxjs';
import {
  SortableHeaderDirective,
  SortEvent,
} from 'src/app/shared/directives/sortable-header.directive';
import { ConfirmDialogComponent } from 'src/app/shared/components/confirm-dialog/confirm-dialog.component';
import { ToastService } from 'src/app/core/services/toast.service';
import { OrganizationType } from 'src/app/core/models/enums.model';

@Component({
  selector: 'app-organization-list',
  templateUrl: './organization-list.component.html',
  styleUrls: ['./organization-list.component.css'],
})
export class OrganizationListComponent implements OnInit {
  organizations?: Organization[];
  currentPage = 1;
  totalItems = 10;
  pageSize = 5;
  sortBy = '';
  sortOrder = 'asc';
  orgType='';
  name='';
  errorMessage='';
  organizationTypes:string[]=Object.values(OrganizationType);
  availablePageSize = [2, 5, 10, 15, 20];

  @ViewChildren(SortableHeaderDirective)
  headers?: QueryList<SortableHeaderDirective>;
  constructor(
    private httpOrganization: HttpOrganizationService,
    private router: Router,
    private activeRoute: ActivatedRoute,
    private modalService: NgbModal,
    private toastService: ToastService
  ) {
    const organizationPage: PageDto<Organization> =
      this.activeRoute.snapshot.data['organizationPage'];
      this.organizations = organizationPage.content;
      this.currentPage = organizationPage.number + 1;
      this.totalItems = organizationPage.totalElements;
      this.pageSize = organizationPage.size;
  }

  ngOnInit(): void {
    this.loadOrganizations();
  }

  loadOrganizations() {
    this.httpOrganization
      .getByPage(this.currentPage, this.pageSize, this.sortOrder,this.sortBy,this.orgType,this.name)
      .subscribe((organizationPage) => {
        this.organizations = organizationPage.content;
        this.totalItems = organizationPage.totalElements;
        this.pageSize = organizationPage.size;
      });
  }

  filterByType(type:string){

    if(type=='Show all'){
      this.orgType=''
      this.loadOrganizations();
    }
    this.orgType=type;
    this.loadOrganizations();
}

  deleteOrganization(organization: Organization) {
    const modalRef = this.modalService.open(ConfirmDialogComponent);
    modalRef.componentInstance.message = `Are you sure you want to delete organization <strong>${organization.name}</strong> ?`;
    modalRef.componentInstance.headerText = 'Deleting organization';
    modalRef.result.then(
      (result) =>
        result === 'Ok' && this.deleteSelectedOrganization(organization)
    );
  }

  deleteSelectedOrganization(organization: Organization) {
    this.errorMessage=''
    this.httpOrganization.deleteOrganization(organization.id).pipe(catchError((err) => {
      this.parseError(err); return throwError(err);})).subscribe(()=>{

        this.loadOrganizations();
        window.location.reload();

      this.router.navigate(['organization'])
      });
  }

  parseError(err: any){
    const str = JSON.stringify(err);
      const obj = JSON.parse(str);
      this.errorMessage = obj.error;
  }

  onSort(sortEvent: SortEvent) {
    this.sortBy = sortEvent.column;
    this.sortOrder = sortEvent.direction;
    this.headers?.forEach((header) => {
      if (header.sortable !== sortEvent.column) {
        header.direction = '';
      }
    });
    this.loadOrganizations();
  }

  onPageSizeChange() {
    this.loadOrganizations();
  }
  onPageChange(page: number) {
    this.loadOrganizations();
  }
}
