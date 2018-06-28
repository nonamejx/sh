/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { ShTestModule } from '../../../test.module';
import { QuestionShDeleteDialogComponent } from 'app/entities/question-sh/question-sh-delete-dialog.component';
import { QuestionShService } from 'app/entities/question-sh/question-sh.service';

describe('Component Tests', () => {
    describe('QuestionSh Management Delete Component', () => {
        let comp: QuestionShDeleteDialogComponent;
        let fixture: ComponentFixture<QuestionShDeleteDialogComponent>;
        let service: QuestionShService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [ShTestModule],
                declarations: [QuestionShDeleteDialogComponent]
            })
                .overrideTemplate(QuestionShDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(QuestionShDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(QuestionShService);
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
