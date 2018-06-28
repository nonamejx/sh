import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { ShSharedModule } from 'app/shared';
import {
    AnswerShComponent,
    AnswerShDetailComponent,
    AnswerShUpdateComponent,
    AnswerShDeletePopupComponent,
    AnswerShDeleteDialogComponent,
    answerRoute,
    answerPopupRoute
} from './';

const ENTITY_STATES = [...answerRoute, ...answerPopupRoute];

@NgModule({
    imports: [ShSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        AnswerShComponent,
        AnswerShDetailComponent,
        AnswerShUpdateComponent,
        AnswerShDeleteDialogComponent,
        AnswerShDeletePopupComponent
    ],
    entryComponents: [AnswerShComponent, AnswerShUpdateComponent, AnswerShDeleteDialogComponent, AnswerShDeletePopupComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class ShAnswerShModule {}
