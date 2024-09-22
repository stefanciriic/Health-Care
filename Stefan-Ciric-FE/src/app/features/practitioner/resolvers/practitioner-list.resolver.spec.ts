import { TestBed } from '@angular/core/testing';

import { PractitionerListResolver } from './practitioner-list.resolver';

describe('PractitionerListResolver', () => {
  let resolver: PractitionerListResolver;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    resolver = TestBed.inject(PractitionerListResolver);
  });

  it('should be created', () => {
    expect(resolver).toBeTruthy();
  });
});
