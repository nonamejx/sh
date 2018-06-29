import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { ShSharedModule } from 'app/shared';
import {
    SectionShComponent,
    SectionShDetailComponent,
    SectionShUpdateComponent,
    SectionShDeletePopupComponent,
    SectionShDeleteDialogComponent,
    sectionRoute,
    sectionPopupRoute
} from './';
import { AutosizeDirective } from 'app/directive/autosize.directive';

const ENTITY_STATES = [...sectionRoute, ...sectionPopupRoute];

@NgModule({
    imports: [ShSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        SectionShComponent,
        SectionShDetailComponent,
        SectionShUpdateComponent,
        SectionShDeleteDialogComponent,
        SectionShDeletePopupComponent,
        AutosizeDirective
    ],
    entryComponents: [SectionShComponent, SectionShUpdateComponent, SectionShDeleteDialogComponent, SectionShDeletePopupComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class ShSectionShModule {}
