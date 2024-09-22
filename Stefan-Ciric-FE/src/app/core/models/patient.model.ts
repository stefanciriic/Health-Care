export interface Patient{
  id:number,
  identifier:string,
  active:boolean,
  name:string,
  surname:string,
  gender:string,
  birthDate:Date,
  address:string,
  phone:string,
  email:string,
  deceased:boolean,
  maritalStatus:string,
  organizationId:number,
  generalPractitionerId:number
}
