import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IAnswerSh } from 'app/shared/model/answer-sh.model';

type EntityResponseType = HttpResponse<IAnswerSh>;
type EntityArrayResponseType = HttpResponse<IAnswerSh[]>;

@Injectable({ providedIn: 'root' })
export class AnswerShService {
    private resourceUrl = SERVER_API_URL + 'api/answers';
    private resourceSearchUrl = SERVER_API_URL + 'api/_search/answers';

    constructor(private http: HttpClient) {}

    create(answer: IAnswerSh): Observable<EntityResponseType> {
        return this.http.post<IAnswerSh>(this.resourceUrl, answer, { observe: 'response' });
    }

    update(answer: IAnswerSh): Observable<EntityResponseType> {
        return this.http.put<IAnswerSh>(this.resourceUrl, answer, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<IAnswerSh>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IAnswerSh[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    search(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IAnswerSh[]>(this.resourceSearchUrl, { params: options, observe: 'response' });
    }
}
