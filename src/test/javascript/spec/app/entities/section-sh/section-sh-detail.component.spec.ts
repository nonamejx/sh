/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { ShTestModule } from '../../../test.module';
import { SectionShDetailComponent } from 'app/entities/section-sh/section-sh-detail.component';
import { SectionSh } from 'app/shared/model/section-sh.model';

describe('Component Tests', () => {
    describe('SectionSh Management Detail Component', () => {
        let comp: SectionShDetailComponent;
        let fixture: ComponentFixture<SectionShDetailComponent>;
        const route = ({ data: of({ section: new SectionSh(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [ShTestModule],
                declarations: [SectionShDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(SectionShDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(SectionShDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.section).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
