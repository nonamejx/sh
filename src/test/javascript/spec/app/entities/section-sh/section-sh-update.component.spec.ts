/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { ShTestModule } from '../../../test.module';
import { SectionShUpdateComponent } from 'app/entities/section-sh/section-sh-update.component';
import { SectionShService } from 'app/entities/section-sh/section-sh.service';
import { SectionSh } from 'app/shared/model/section-sh.model';

describe('Component Tests', () => {
    describe('SectionSh Management Update Component', () => {
        let comp: SectionShUpdateComponent;
        let fixture: ComponentFixture<SectionShUpdateComponent>;
        let service: SectionShService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [ShTestModule],
                declarations: [SectionShUpdateComponent]
            })
                .overrideTemplate(SectionShUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(SectionShUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(SectionShService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new SectionSh(123);
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.section = entity;
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
                    const entity = new SectionSh();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.section = entity;
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
