import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { JhiAlertService } from 'ng-jhipster';

import { IConsumption } from 'app/shared/model/ecounter/consumption.model';
import { ConsumptionService } from './consumption.service';
import { ICounter } from 'app/shared/model/ecounter/counter.model';
import { CounterService } from 'app/entities/ecounter/counter';

@Component({
    selector: 'jhi-consumption-update',
    templateUrl: './consumption-update.component.html'
})
export class ConsumptionUpdateComponent implements OnInit {
    private _consumption: IConsumption;
    isSaving: boolean;

    counters: ICounter[];
    dateCreated: string;

    constructor(
        private jhiAlertService: JhiAlertService,
        private consumptionService: ConsumptionService,
        private counterService: CounterService,
        private activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ consumption }) => {
            this.consumption = consumption;
        });
        this.counterService.query().subscribe(
            (res: HttpResponse<ICounter[]>) => {
                this.counters = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        this.consumption.dateCreated = moment(this.dateCreated, DATE_TIME_FORMAT);
        if (this.consumption.id !== undefined) {
            this.subscribeToSaveResponse(this.consumptionService.update(this.consumption));
        } else {
            this.subscribeToSaveResponse(this.consumptionService.create(this.consumption));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<IConsumption>>) {
        result.subscribe((res: HttpResponse<IConsumption>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }

    trackCounterById(index: number, item: ICounter) {
        return item.id;
    }
    get consumption() {
        return this._consumption;
    }

    set consumption(consumption: IConsumption) {
        this._consumption = consumption;
        this.dateCreated = moment(consumption.dateCreated).format(DATE_TIME_FORMAT);
    }
}
