import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { JhiAlertService } from 'ng-jhipster';

import { IAnswerSh } from 'app/shared/model/answer-sh.model';
import { AnswerShService } from './answer-sh.service';
import { IQuestionSh } from 'app/shared/model/question-sh.model';
import { QuestionShService } from 'app/entities/question-sh';

@Component({
    selector: 'jhi-answer-sh-update',
    templateUrl: './answer-sh-update.component.html'
})
export class AnswerShUpdateComponent implements OnInit {
    private _answer: IAnswerSh;
    isSaving: boolean;

    questions: IQuestionSh[];

    constructor(
        private jhiAlertService: JhiAlertService,
        private answerService: AnswerShService,
        private questionService: QuestionShService,
        private activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ answer }) => {
            this.answer = answer;
        });
        this.questionService.query().subscribe(
            (res: HttpResponse<IQuestionSh[]>) => {
                this.questions = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.answer.id !== undefined) {
            this.subscribeToSaveResponse(this.answerService.update(this.answer));
        } else {
            this.subscribeToSaveResponse(this.answerService.create(this.answer));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<IAnswerSh>>) {
        result.subscribe((res: HttpResponse<IAnswerSh>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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

    trackQuestionById(index: number, item: IQuestionSh) {
        return item.id;
    }
    get answer() {
        return this._answer;
    }

    set answer(answer: IAnswerSh) {
        this._answer = answer;
    }
}
