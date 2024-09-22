import { Component, OnInit, QueryList, ViewChildren } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { catchError, take, throwError } from 'rxjs';
import { Gender, Qualification } from 'src/app/core/models/enums.model';
import { PageDto } from 'src/app/core/models/page.dto';
import { Practitioner } from 'src/app/core/models/practitioner.model';
import { HttpPractitionerService } from 'src/app/core/services/http-practitioner.service';
import { ToastService } from 'src/app/core/services/toast.service';
import { ConfirmDialogComponent } from 'src/app/shared/components/confirm-dialog/confirm-dialog.component';
import { SortableHeaderDirective, SortEvent } from 'src/app/shared/directives/sortable-header.directive';

@Component({
  selector: 'app-practitioner-list',
  templateUrl: './practitioner-list.component.html',
  styleUrls: ['./practitioner-list.component.css']
})
export class PractitionerListComponent implements OnInit {
  practitioners?: Practitioner[];
  currentPage = 1;
  totalItems = 10;
  pageSize = 5;
  sortBy='name';
  sortOrder='asc';
  loadingData = false;
  availablePageSize = [2, 5, 10, 15, 20];
  errorMessage=''
  gender=''
  qualification=''
  genders:string[]=Object.values(Gender);
  qualifications:string[]=Object.values(Qualification);


  @ViewChildren(SortableHeaderDirective)
  headers?: QueryList<SortableHeaderDirective>;

  constructor(
    private httpPractitioner: HttpPractitionerService,
    private router: Router,
    private activeRoute: ActivatedRoute,
    private modalService: NgbModal,
    private toastService: ToastService
  ) {
    const practitionerPage: PageDto<Practitioner> = this.activeRoute.snapshot.data['practitionerPage'];
    this.practitioners = practitionerPage.content;
    this.currentPage = practitionerPage.number  + 1 ;
    this.totalItems = practitionerPage.totalElements;
    this.pageSize = practitionerPage.size;
  }

  ngOnInit(): void {
    const page = this.activeRoute.snapshot.queryParams['page']
  }

  loadPractitioners() {
    this.httpPractitioner
      .getByPage(this.currentPage, this.pageSize, this.sortBy,this.sortOrder,this.gender,this.qualification)
      .subscribe((practitionerPage) => {
        this.practitioners = practitionerPage.content;
        this.totalItems = practitionerPage.totalElements;
        this.pageSize = practitionerPage.size;
      });
  }


  filterByGender(type:string){
    if(type=="Show all genders"){
      type=''
    }
    this.gender=type;
    this.loadPractitioners();
  }

  filterByQualification(type:string){

    if(type=="Show all qualifications"){
      type=''
    }
    this.qualification=type;
    this.loadPractitioners();
  }

  showAll(){
    this.gender=''
    this.qualification=''
    this.loadPractitioners();
  }

  deletePractitioner(practitioner:Practitioner) {
    const modalRef = this.modalService.open(ConfirmDialogComponent);
    modalRef.componentInstance.message = `Are you sure you want to delete practitioner <strong>${practitioner.name}</strong> ?`;
    modalRef.componentInstance.headerText = 'Deleting practitioner';
    modalRef.result.then(
      (result) => result === 'Ok' && this.deleteSelectedPractitioner(practitioner)

      );

  }
  deleteSelectedPractitioner(practitioner: Practitioner) {
    this.httpPractitioner.deletePractitioner(practitioner.id).pipe(catchError((err) => {
      this.parseError(err); return throwError(err);})).subscribe(()=>{

        this.loadPractitioners();
        window.location.reload();
      this.router.navigate(['practitioner'])
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
    this.loadPractitioners();
  }
  onPageSizeChange() {
    this.loadPractitioners();
  }
  onPageChange(page: number) {
    this.loadPractitioners();
  }

  getEnumValue(s:string){
    let key = Object.keys(Qualification).indexOf(s);
    let value = Object.values(Qualification)[key];
    return value;
  }
  getGenderValue(s:string){
    let key = Object.keys(Gender).indexOf(s);
    let value = Object.values(Gender)[key];
    return value;
  }

}
