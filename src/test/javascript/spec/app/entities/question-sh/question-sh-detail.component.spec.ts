/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { ShTestModule } from '../../../test.module';
import { QuestionShDetailComponent } from 'app/entities/question-sh/question-sh-detail.component';
import { QuestionSh } from 'app/shared/model/question-sh.model';

describe('Component Tests', () => {
    describe('QuestionSh Management Detail Component', () => {
        let comp: QuestionShDetailComponent;
        let fixture: ComponentFixture<QuestionShDetailComponent>;
        const route = ({ data: of({ question: new QuestionSh(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [ShTestModule],
                declarations: [QuestionShDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(QuestionShDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(QuestionShDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.question).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
