export interface Examination {
  id: number;
  identifier: string;
  status: string;
  serviceType: string;
  priority: string;
  startDate: Date;
  endDate: Date;
  diagnosis: string;
  organizationId: number;
  practitionerIds: number[];
  patientId: number;
}
