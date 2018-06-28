import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { Observable } from 'rxjs';
import { AnswerSh } from 'app/shared/model/answer-sh.model';
import { AnswerShService } from './answer-sh.service';
import { AnswerShComponent } from './answer-sh.component';
import { AnswerShDetailComponent } from './answer-sh-detail.component';
import { AnswerShUpdateComponent } from './answer-sh-update.component';
import { AnswerShDeletePopupComponent } from './answer-sh-delete-dialog.component';
import { IAnswerSh } from 'app/shared/model/answer-sh.model';

@Injectable({ providedIn: 'root' })
export class AnswerShResolve implements Resolve<IAnswerSh> {
    constructor(private service: AnswerShService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).map((answer: HttpResponse<AnswerSh>) => answer.body);
        }
        return Observable.of(new AnswerSh());
    }
}

export const answerRoute: Routes = [
    {
        path: 'answer-sh',
        component: AnswerShComponent,
        resolve: {
            pagingParams: JhiResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            defaultSort: 'id,asc',
            pageTitle: 'shApp.answer.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'answer-sh/:id/view',
        component: AnswerShDetailComponent,
        resolve: {
            answer: AnswerShResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'shApp.answer.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'answer-sh/new',
        component: AnswerShUpdateComponent,
        resolve: {
            answer: AnswerShResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'shApp.answer.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'answer-sh/:id/edit',
        component: AnswerShUpdateComponent,
        resolve: {
            answer: AnswerShResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'shApp.answer.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const answerPopupRoute: Routes = [
    {
        path: 'answer-sh/:id/delete',
        component: AnswerShDeletePopupComponent,
        resolve: {
            answer: AnswerShResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'shApp.answer.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
