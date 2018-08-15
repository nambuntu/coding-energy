import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IConsumption } from 'app/shared/model/ecounter/consumption.model';

type EntityResponseType = HttpResponse<IConsumption>;
type EntityArrayResponseType = HttpResponse<IConsumption[]>;

@Injectable({ providedIn: 'root' })
export class ConsumptionService {
    private resourceUrl = SERVER_API_URL + 'ecounter/api/consumptions';

    constructor(private http: HttpClient) {}

    create(consumption: IConsumption): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(consumption);
        return this.http
            .post<IConsumption>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http
            .get<IConsumption>(`${this.resourceUrl}/${id}`, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http
            .get<IConsumption[]>(this.resourceUrl, { params: options, observe: 'response' })
            .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
    }

    private convertDateFromClient(consumption: IConsumption): IConsumption {
        const copy: IConsumption = Object.assign({}, consumption, {
            dateCreated: consumption.dateCreated != null && consumption.dateCreated.isValid() ? consumption.dateCreated.toJSON() : null
        });
        return copy;
    }

    private convertDateFromServer(res: EntityResponseType): EntityResponseType {
        res.body.dateCreated = res.body.dateCreated != null ? moment(res.body.dateCreated) : null;
        return res;
    }

    private convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
        res.body.forEach((consumption: IConsumption) => {
            consumption.dateCreated = consumption.dateCreated != null ? moment(consumption.dateCreated) : null;
        });
        return res;
    }
}
