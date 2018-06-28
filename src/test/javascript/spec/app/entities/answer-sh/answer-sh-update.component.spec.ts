/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { ShTestModule } from '../../../test.module';
import { AnswerShUpdateComponent } from 'app/entities/answer-sh/answer-sh-update.component';
import { AnswerShService } from 'app/entities/answer-sh/answer-sh.service';
import { AnswerSh } from 'app/shared/model/answer-sh.model';

describe('Component Tests', () => {
    describe('AnswerSh Management Update Component', () => {
        let comp: AnswerShUpdateComponent;
        let fixture: ComponentFixture<AnswerShUpdateComponent>;
        let service: AnswerShService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [ShTestModule],
                declarations: [AnswerShUpdateComponent]
            })
                .overrideTemplate(AnswerShUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(AnswerShUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(AnswerShService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new AnswerSh(123);
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.answer = entity;
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
                    const entity = new AnswerSh();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.answer = entity;
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
