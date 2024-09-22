import { TestBed } from '@angular/core/testing';

import { PatientListResolver } from './patient-list.resolver';

describe('PatientListResolver', () => {
  let resolver: PatientListResolver;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    resolver = TestBed.inject(PatientListResolver);
  });

  it('should be created', () => {
    expect(resolver).toBeTruthy();
  });
});
