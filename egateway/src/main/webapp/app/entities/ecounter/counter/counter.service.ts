import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { ICounter } from 'app/shared/model/ecounter/counter.model';

type EntityResponseType = HttpResponse<ICounter>;
type EntityArrayResponseType = HttpResponse<ICounter[]>;

@Injectable({ providedIn: 'root' })
export class CounterService {
    private resourceUrl = SERVER_API_URL + 'ecounter/api/counters';

    constructor(private http: HttpClient) {}

    create(counter: ICounter): Observable<EntityResponseType> {
        return this.http.post<ICounter>(this.resourceUrl, counter, { observe: 'response' });
    }

    update(counter: ICounter): Observable<EntityResponseType> {
        return this.http.put<ICounter>(this.resourceUrl, counter, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<ICounter>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<ICounter[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }
}
