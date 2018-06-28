import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IQuestionSh } from 'app/shared/model/question-sh.model';

type EntityResponseType = HttpResponse<IQuestionSh>;
type EntityArrayResponseType = HttpResponse<IQuestionSh[]>;

@Injectable({ providedIn: 'root' })
export class QuestionShService {
    private resourceUrl = SERVER_API_URL + 'api/questions';
    private resourceSearchUrl = SERVER_API_URL + 'api/_search/questions';

    constructor(private http: HttpClient) {}

    create(question: IQuestionSh): Observable<EntityResponseType> {
        return this.http.post<IQuestionSh>(this.resourceUrl, question, { observe: 'response' });
    }

    update(question: IQuestionSh): Observable<EntityResponseType> {
        return this.http.put<IQuestionSh>(this.resourceUrl, question, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<IQuestionSh>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IQuestionSh[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    search(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IQuestionSh[]>(this.resourceSearchUrl, { params: options, observe: 'response' });
    }
}
