import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { JhiAlertService } from 'ng-jhipster';

import { IQuestionSh } from 'app/shared/model/question-sh.model';
import { QuestionShService } from './question-sh.service';
import { ISectionSh } from 'app/shared/model/section-sh.model';
import { SectionShService } from 'app/entities/section-sh';

@Component({
    selector: 'jhi-question-sh-update',
    templateUrl: './question-sh-update.component.html'
})
export class QuestionShUpdateComponent implements OnInit {
    private _question: IQuestionSh;
    isSaving: boolean;

    sections: ISectionSh[];

    constructor(
        private jhiAlertService: JhiAlertService,
        private questionService: QuestionShService,
        private sectionService: SectionShService,
        private activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ question }) => {
            this.question = question;
        });
        this.sectionService.query().subscribe(
            (res: HttpResponse<ISectionSh[]>) => {
                this.sections = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.question.id !== undefined) {
            this.subscribeToSaveResponse(this.questionService.update(this.question));
        } else {
            this.subscribeToSaveResponse(this.questionService.create(this.question));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<IQuestionSh>>) {
        result.subscribe((res: HttpResponse<IQuestionSh>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }

    trackSectionById(index: number, item: ISectionSh) {
        return item.id;
    }
    get question() {
        return this._question;
    }

    set question(question: IQuestionSh) {
        this._question = question;
    }
}
