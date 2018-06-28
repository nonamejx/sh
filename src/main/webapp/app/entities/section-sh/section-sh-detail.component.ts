import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ISectionSh } from 'app/shared/model/section-sh.model';

@Component({
    selector: 'jhi-section-sh-detail',
    templateUrl: './section-sh-detail.component.html'
})
export class SectionShDetailComponent implements OnInit {
    section: ISectionSh;

    constructor(private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ section }) => {
            this.section = section;
        });
    }

    previousState() {
        window.history.back();
    }
}
