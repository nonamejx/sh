import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IAnswerSh } from 'app/shared/model/answer-sh.model';

@Component({
    selector: 'jhi-answer-sh-detail',
    templateUrl: './answer-sh-detail.component.html'
})
export class AnswerShDetailComponent implements OnInit {
    answer: IAnswerSh;

    constructor(private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ answer }) => {
            this.answer = answer;
        });
    }

    previousState() {
        window.history.back();
    }
}
