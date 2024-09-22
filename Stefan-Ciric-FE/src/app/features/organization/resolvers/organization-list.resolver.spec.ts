import { TestBed } from '@angular/core/testing';

import { OrganizationListResolver } from './organization-list.resolver';

describe('OrganizationListResolver', () => {
  let resolver: OrganizationListResolver;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    resolver = TestBed.inject(OrganizationListResolver);
  });

  it('should be created', () => {
    expect(resolver).toBeTruthy();
  });
});
