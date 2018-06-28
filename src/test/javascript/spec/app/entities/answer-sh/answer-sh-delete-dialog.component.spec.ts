/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { ShTestModule } from '../../../test.module';
import { AnswerShDeleteDialogComponent } from 'app/entities/answer-sh/answer-sh-delete-dialog.component';
import { AnswerShService } from 'app/entities/answer-sh/answer-sh.service';

describe('Component Tests', () => {
    describe('AnswerSh Management Delete Component', () => {
        let comp: AnswerShDeleteDialogComponent;
        let fixture: ComponentFixture<AnswerShDeleteDialogComponent>;
        let service: AnswerShService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [ShTestModule],
                declarations: [AnswerShDeleteDialogComponent]
            })
                .overrideTemplate(AnswerShDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(AnswerShDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(AnswerShService);
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
