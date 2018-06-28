/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { ShTestModule } from '../../../test.module';
import { QuestionShUpdateComponent } from 'app/entities/question-sh/question-sh-update.component';
import { QuestionShService } from 'app/entities/question-sh/question-sh.service';
import { QuestionSh } from 'app/shared/model/question-sh.model';

describe('Component Tests', () => {
    describe('QuestionSh Management Update Component', () => {
        let comp: QuestionShUpdateComponent;
        let fixture: ComponentFixture<QuestionShUpdateComponent>;
        let service: QuestionShService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [ShTestModule],
                declarations: [QuestionShUpdateComponent]
            })
                .overrideTemplate(QuestionShUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(QuestionShUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(QuestionShService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new QuestionSh(123);
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.question = entity;
                    // WHEN
                    comp.save();
                    tick(); // simulate async

                    // THEN
                    expect(service.update).toHaveBeenCalledWith(entity);
                    expect(comp.isSaving).toEqual(false);
                })
            );

            it(
                'Should call create service on save for new entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new QuestionSh();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.question = entity;
                    // WHEN
                    comp.save();
                    tick(); // simulate async

                    // THEN
                    expect(service.create).toHaveBeenCalledWith(entity);
                    expect(comp.isSaving).toEqual(false);
                })
            );
        });
    });
});
