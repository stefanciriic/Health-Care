import { Component, OnInit, QueryList, ViewChildren } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { Priority, Status } from 'src/app/core/models/enums.model';
import { Examination } from 'src/app/core/models/examination.model';
import { Organization } from 'src/app/core/models/organization.model';
import { PageDto } from 'src/app/core/models/page.dto';
import { Patient } from 'src/app/core/models/patient.model';
import { HttpExaminationService } from 'src/app/core/services/http-examination.service';
import { HttpOrganizationService } from 'src/app/core/services/http-organization.service';
import { HttpPatientService } from 'src/app/core/services/http-patient.service';
import { ToastService } from 'src/app/core/services/toast.service';
import { ConfirmDialogComponent } from 'src/app/shared/components/confirm-dialog/confirm-dialog.component';
import {
  SortableHeaderDirective,
  SortEvent,
} from 'src/app/shared/directives/sortable-header.directive';

@Component({
  selector: 'app-examination-list',
  templateUrl: './examination-list.component.html',
  styleUrls: ['./examination-list.component.css'],
})
export class ExaminationListComponent implements OnInit {
  examinations?: Examination[];
  organizations?: Organization[];
  patients?: Patient[];
  statuses:string[]=Object.values(Status);
  status = '';
  priorities:string[]=Object.values(Priority);
  priority = '';
  currentPage = 1;
  totalItems = 10;
  pageSize = 5;
  sortBy = 'identifier';
  sortOrder = 'asc';
  loadingData = false;
  availablePageSize = [2, 5, 10, 15, 20];

  @ViewChildren(SortableHeaderDirective)
  headers?: QueryList<SortableHeaderDirective>;

  constructor(
    private httpExamination: HttpExaminationService,
    private httpOrganization: HttpOrganizationService,
    private httpPatient: HttpPatientService,
    private router: Router,
    private activeRoute: ActivatedRoute,
    private modalService: NgbModal,
    private toastService: ToastService
  ) {
    const examinationPage: PageDto<Examination> =
    this.activeRoute.snapshot.data['examinationPage'];
    this.examinations = examinationPage.content;
    this.currentPage = examinationPage.number + 1;
    this.totalItems = examinationPage.totalElements;
    this.pageSize = examinationPage.size;
  }

  ngOnInit(): void {
    this.loadExaminations();

    this.httpOrganization.getAll().subscribe((organizationPage) => {
      this.organizations = organizationPage;
    });

    this.httpPatient.getAll().subscribe((patientPage) => {
      this.patients = patientPage;
    });
  }
  orgName(id: number) {
    const org = this.organizations?.filter((org) => org.id == id);
    if (org) {
      return org[0].name;
    } else {
      return null;
    }
  }
  filterByStatus(type:string){

    if(type=="Show all"){
      type=''
    }
    this.status=type;
    this.loadExaminations();
  }

  filterByPriority(type:string){

    if(type=="Show all"){
      type=''
    }
    this.priority=type;
    this.loadExaminations();
  }

  getPatient(id: number) {
    const pat = this.patients?.filter((pat) => pat.id == id);
    if (pat) {
      return pat[0].name + ' ' + pat[0].surname;
    } else {
      return null;
    }

  }

  loadExaminations() {
    this.httpExamination
      .getByPage(this.currentPage, this.pageSize, this.sortBy,this.sortOrder,this.status,this.priority)
      .subscribe((examinationPage) => {
        this.examinations = examinationPage.content;
        this.totalItems = examinationPage.totalElements;
        this.pageSize = examinationPage.size;
      });
  }

  deleteExamination(examination: Examination) {
    const modalRef = this.modalService.open(ConfirmDialogComponent);
    modalRef.componentInstance.message = `Are you sure you want to delete examination <strong>${examination.identifier}</strong> ?`;
    modalRef.componentInstance.headerText = 'Deleting examination';
    modalRef.result.then(
      (result) => result === 'Ok' && this.deleteSelectedExamination(examination)
    );
  }

  deleteSelectedExamination(examination: Examination) {
    this.httpExamination.deleteExamination(examination.id);
    this.loadExaminations();
    window.location.reload();
  }
  onSort(sortEvent: SortEvent) {
    this.sortBy = sortEvent.column;
    this.sortOrder = sortEvent.direction;
    this.headers?.forEach( header => {
      if (header.sortable !== sortEvent.column) {
        header.direction = '';
      }
    })
    this.loadExaminations();
  }

  onPageSizeChange() {
    this.loadExaminations();
  }
  onPageChange(page: number) {
    this.loadExaminations();
  }


  getStatusValue(s:string){
    let key = Object.keys(Status).indexOf(s);
    let value = Object.values(Status)[key];
    return value;
  }
  getPriorityValue(s:string){
    let key = Object.keys(Priority).indexOf(s);
    let value = Object.values(Priority)[key];
    return value;
  }


}
