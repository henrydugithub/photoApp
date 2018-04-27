import { TestBed, inject } from '@angular/core/testing';

import { PhotoAppService } from './photo-app.service';

describe('PhotoAppService', () => {
  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [PhotoAppService]
    });
  });

  it('should be created', inject([PhotoAppService], (service: PhotoAppService) => {
    expect(service).toBeTruthy();
  }));
});
