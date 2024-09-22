export interface Practitioner{
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
  qualification:string,
  organizationId:number
}
