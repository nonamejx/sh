import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { ISectionSh } from 'app/shared/model/section-sh.model';

type EntityResponseType = HttpResponse<ISectionSh>;
type EntityArrayResponseType = HttpResponse<ISectionSh[]>;

@Injectable({ providedIn: 'root' })
export class SectionShService {
    private resourceUrl = SERVER_API_URL + 'api/sections';
    private resourceSearchUrl = SERVER_API_URL + 'api/_search/sections';

    constructor(private http: HttpClient) {}

    create(section: ISectionSh): Observable<EntityResponseType> {
        return this.http.post<ISectionSh>(this.resourceUrl, section, { observe: 'response' });
    }

    update(section: ISectionSh): Observable<EntityResponseType> {
        return this.http.put<ISectionSh>(this.resourceUrl, section, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<ISectionSh>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<ISectionSh[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    search(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<ISectionSh[]>(this.resourceSearchUrl, { params: options, observe: 'response' });
    }
}
