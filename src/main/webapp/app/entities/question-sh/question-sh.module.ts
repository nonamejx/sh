import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { ShSharedModule } from 'app/shared';
import {
    QuestionShComponent,
    QuestionShDetailComponent,
    QuestionShUpdateComponent,
    QuestionShDeletePopupComponent,
    QuestionShDeleteDialogComponent,
    questionRoute,
    questionPopupRoute
} from './';

const ENTITY_STATES = [...questionRoute, ...questionPopupRoute];

@NgModule({
    imports: [ShSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        QuestionShComponent,
        QuestionShDetailComponent,
        QuestionShUpdateComponent,
        QuestionShDeleteDialogComponent,
        QuestionShDeletePopupComponent
    ],
    entryComponents: [QuestionShComponent, QuestionShUpdateComponent, QuestionShDeleteDialogComponent, QuestionShDeletePopupComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class ShQuestionShModule {}
