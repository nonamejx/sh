import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';

import { ShSectionShModule } from './section-sh/section-sh.module';
import { ShQuestionShModule } from './question-sh/question-sh.module';
import { ShAnswerShModule } from './answer-sh/answer-sh.module';
/* jhipster-needle-add-entity-module-import - JHipster will add entity modules imports here */

@NgModule({
    // prettier-ignore
    imports: [
        ShSectionShModule,
        ShQuestionShModule,
        ShAnswerShModule,
        /* jhipster-needle-add-entity-module - JHipster will add entity modules here */
    ],
    declarations: [],
    entryComponents: [],
    providers: [],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class ShEntityModule {}
