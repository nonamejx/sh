/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { ShTestModule } from '../../../test.module';
import { SectionShDeleteDialogComponent } from 'app/entities/section-sh/section-sh-delete-dialog.component';
import { SectionShService } from 'app/entities/section-sh/section-sh.service';

describe('Component Tests', () => {
    describe('SectionSh Management Delete Component', () => {
        let comp: SectionShDeleteDialogComponent;
        let fixture: ComponentFixture<SectionShDeleteDialogComponent>;
        let service: SectionShService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [ShTestModule],
                declarations: [SectionShDeleteDialogComponent]
            })
                .overrideTemplate(SectionShDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(SectionShDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(SectionShService);
            mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
            mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
        });

        describe('confirmDelete', () => {
            it(
                'Should call delete service on confirmDelete',
                inject(
                    [],
                    fakeAsync(() => {
                        // GIVEN
                        spyOn(service, 'delete').and.returnValue(of({}));

                        // WHEN
                        comp.confirmDelete(123);
                        tick();

                        // THEN
                        expect(service.delete).toHaveBeenCalledWith(123);
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalled();
                    })
                )
            );
        });
    });
});
