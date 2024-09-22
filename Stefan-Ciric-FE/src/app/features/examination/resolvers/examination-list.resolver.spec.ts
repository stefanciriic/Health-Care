import { TestBed } from '@angular/core/testing';

import { ExaminationListResolver } from './examination-list.resolver';

describe('ExaminationListResolver', () => {
  let resolver: ExaminationListResolver;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    resolver = TestBed.inject(ExaminationListResolver);
  });

  it('should be created', () => {
    expect(resolver).toBeTruthy();
  });
});
