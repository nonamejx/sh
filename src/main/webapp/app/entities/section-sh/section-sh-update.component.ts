import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { ISectionSh } from 'app/shared/model/section-sh.model';
import { SectionShService } from './section-sh.service';

@Component({
    selector: 'jhi-section-sh-update',
    templateUrl: './section-sh-update.component.html'
})
export class SectionShUpdateComponent implements OnInit {
    private _section: ISectionSh;
    isSaving: boolean;

    constructor(private sectionService: SectionShService, private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ section }) => {
            this.section = section;
        });
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.section.id !== undefined) {
            this.subscribeToSaveResponse(this.sectionService.update(this.section));
        } else {
            this.subscribeToSaveResponse(this.sectionService.create(this.section));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<ISectionSh>>) {
        result.subscribe((res: HttpResponse<ISectionSh>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    private onSaveError() {
        this.isSaving = false;
    }
    get section() {
        return this._section;
    }

    set section(section: ISectionSh) {
        this._section = section;
    }
}
