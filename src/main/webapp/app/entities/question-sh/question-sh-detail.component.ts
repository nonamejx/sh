import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IQuestionSh } from 'app/shared/model/question-sh.model';

@Component({
    selector: 'jhi-question-sh-detail',
    templateUrl: './question-sh-detail.component.html'
})
export class QuestionShDetailComponent implements OnInit {
    question: IQuestionSh;

    constructor(private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ question }) => {
            this.question = question;
        });
    }

    previousState() {
        window.history.back();
    }
}
