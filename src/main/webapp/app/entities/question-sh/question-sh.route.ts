import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { Observable } from 'rxjs';
import { QuestionSh } from 'app/shared/model/question-sh.model';
import { QuestionShService } from './question-sh.service';
import { QuestionShComponent } from './question-sh.component';
import { QuestionShDetailComponent } from './question-sh-detail.component';
import { QuestionShUpdateComponent } from './question-sh-update.component';
import { QuestionShDeletePopupComponent } from './question-sh-delete-dialog.component';
import { IQuestionSh } from 'app/shared/model/question-sh.model';

@Injectable({ providedIn: 'root' })
export class QuestionShResolve implements Resolve<IQuestionSh> {
    constructor(private service: QuestionShService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).map((question: HttpResponse<QuestionSh>) => question.body);
        }
        return Observable.of(new QuestionSh());
    }
}

export const questionRoute: Routes = [
    {
        path: 'question-sh',
        component: QuestionShComponent,
        resolve: {
            pagingParams: JhiResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            defaultSort: 'id,asc',
            pageTitle: 'shApp.question.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'question-sh/:id/view',
        component: QuestionShDetailComponent,
        resolve: {
            question: QuestionShResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'shApp.question.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'question-sh/new',
        component: QuestionShUpdateComponent,
        resolve: {
            question: QuestionShResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'shApp.question.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'question-sh/:id/edit',
        component: QuestionShUpdateComponent,
        resolve: {
            question: QuestionShResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'shApp.question.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const questionPopupRoute: Routes = [
    {
        path: 'question-sh/:id/delete',
        component: QuestionShDeletePopupComponent,
        resolve: {
            question: QuestionShResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'shApp.question.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
