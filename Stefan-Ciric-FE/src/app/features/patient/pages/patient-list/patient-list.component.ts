import { Component, OnInit, QueryList, ViewChildren } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { catchError, throwError } from 'rxjs';
import { Gender, MaritalStatus } from 'src/app/core/models/enums.model';
import { PageDto } from 'src/app/core/models/page.dto';
import { Patient } from 'src/app/core/models/patient.model';
import { HttpPatientService } from 'src/app/core/services/http-patient.service';
import { ToastService } from 'src/app/core/services/toast.service';
import { ConfirmDialogComponent } from 'src/app/shared/components/confirm-dialog/confirm-dialog.component';
import { SortableHeaderDirective, SortEvent } from 'src/app/shared/directives/sortable-header.directive';

@Component({
  selector: 'app-patient-list',
  templateUrl: './patient-list.component.html',
  styleUrls: ['./patient-list.component.css']
})
export class PatientListComponent implements OnInit {
  patients?: Patient[];
  currentPage = 1;
  totalItems = 10;
  pageSize = 5;
  sortBy='name';
  sortOrder='asc';
  loadingData = false;
  availablePageSize = [2, 5, 10, 15, 20];
  errorMessage=''
  maritalStatuses:string[]=Object.values(MaritalStatus);
  maritalStatus=''
  genders:string[]=Object.values(Gender);
  gender=''
  filterWord=''



  @ViewChildren(SortableHeaderDirective)
  headers?: QueryList<SortableHeaderDirective>;

  constructor(
    private httpPatient: HttpPatientService,
    private router: Router,
    private activeRoute: ActivatedRoute,
    private modalService: NgbModal,
    private toastService: ToastService
  ) {
    const patientPage: PageDto<Patient> = this.activeRoute.snapshot.data['patientPage'];
    this.patients = patientPage.content;
    this.currentPage = patientPage.number  + 1 ;
    this.totalItems = patientPage.totalElements;
    this.pageSize = patientPage.size;

  }

  ngOnInit(): void {
    const page = this.activeRoute.snapshot.queryParams['page']
    this.loadPatients()
  }

  loadPatients() {
    this.httpPatient
      .getByPage(this.currentPage, this.pageSize, this.sortBy,this.sortOrder,this.gender,this.maritalStatus,this.filterWord)
      .subscribe((patientPage) => {
        this.patients = patientPage.content;
        this.totalItems = patientPage.totalElements;
        this.pageSize = patientPage.size;
      });
  }
  deletePatient(patient:Patient) {
    const modalRef = this.modalService.open(ConfirmDialogComponent);
    modalRef.componentInstance.message = `Are you sure you want to delete patient <strong>${patient.name}</strong> ?`;
    modalRef.componentInstance.headerText = 'Deleting patient';
    modalRef.result.then(
      (result) => result === 'Ok' && this.deleteSelectedPatient(patient)

      );

  }

  deleteSelectedPatient(patient: Patient) {

    this.httpPatient.deletePatient(patient.id).pipe(catchError((err) => {
      this.parseError(err); return throwError(err);})).subscribe(()=>{

        this.loadPatients();
        window.location.reload();
      this.router.navigate(['patient'])
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
    this.headers?.forEach( header => {
      if (header.sortable !== sortEvent.column) {
        header.direction = '';
      }
    })
    this.loadPatients();
  }
  onPageSizeChange() {
    this.loadPatients();
  }
  onPageChange(page: number) {
    this.loadPatients();
  }


  filterByGender(type:string){

    if(type=="Show all"){
      type=''
    }
    this.gender=type;
    this.loadPatients();
  }

  filterByMaritalStatus(type:string){

    if(type=="Show all"){
      type=''
    }

    this.maritalStatus=type;
    this.loadPatients();
  }

  filterByWord(type:string){
    this.filterWord=type;
    this.loadPatients();
    this.filterWord='';
  }


  getEnumValue(s:string){
    let key = Object.keys(MaritalStatus).indexOf(s);
    let value = Object.values(MaritalStatus)[key];
    return value;
  }
  getGenderValue(s:string){
    let key = Object.keys(Gender).indexOf(s);
    let value = Object.values(Gender)[key];
    return value;
  }

}
