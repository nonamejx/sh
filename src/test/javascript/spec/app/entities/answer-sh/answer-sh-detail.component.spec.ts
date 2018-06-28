/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { ShTestModule } from '../../../test.module';
import { AnswerShDetailComponent } from 'app/entities/answer-sh/answer-sh-detail.component';
import { AnswerSh } from 'app/shared/model/answer-sh.model';

describe('Component Tests', () => {
    describe('AnswerSh Management Detail Component', () => {
        let comp: AnswerShDetailComponent;
        let fixture: ComponentFixture<AnswerShDetailComponent>;
        const route = ({ data: of({ answer: new AnswerSh(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [ShTestModule],
                declarations: [AnswerShDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(AnswerShDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(AnswerShDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.answer).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
