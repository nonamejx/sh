import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { Observable } from 'rxjs';
import { SectionSh } from 'app/shared/model/section-sh.model';
import { SectionShService } from './section-sh.service';
import { SectionShComponent } from './section-sh.component';
import { SectionShDetailComponent } from './section-sh-detail.component';
import { SectionShUpdateComponent } from './section-sh-update.component';
import { SectionShDeletePopupComponent } from './section-sh-delete-dialog.component';
import { ISectionSh } from 'app/shared/model/section-sh.model';

@Injectable({ providedIn: 'root' })
export class SectionShResolve implements Resolve<ISectionSh> {
    constructor(private service: SectionShService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).map((section: HttpResponse<SectionSh>) => section.body);
        }
        return Observable.of(new SectionSh());
    }
}

export const sectionRoute: Routes = [
    {
        path: 'section-sh',
        component: SectionShComponent,
        resolve: {
            pagingParams: JhiResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            defaultSort: 'id,asc',
            pageTitle: 'shApp.section.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'section-sh/:id/view',
        component: SectionShDetailComponent,
        resolve: {
            section: SectionShResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'shApp.section.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'section-sh/new',
        component: SectionShUpdateComponent,
        resolve: {
            section: SectionShResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'shApp.section.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'section-sh/:id/edit',
        component: SectionShUpdateComponent,
        resolve: {
            section: SectionShResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'shApp.section.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const sectionPopupRoute: Routes = [
    {
        path: 'section-sh/:id/delete',
        component: SectionShDeletePopupComponent,
        resolve: {
            section: SectionShResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'shApp.section.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
