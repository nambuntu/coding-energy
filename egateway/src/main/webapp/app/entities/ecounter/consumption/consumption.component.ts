import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IConsumption } from 'app/shared/model/ecounter/consumption.model';
import { Principal } from 'app/core';
import { ConsumptionService } from './consumption.service';

@Component({
    selector: 'jhi-consumption',
    templateUrl: './consumption.component.html'
})
export class ConsumptionComponent implements OnInit, OnDestroy {
    consumptions: IConsumption[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private consumptionService: ConsumptionService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {}

    loadAll() {
        this.consumptionService.query().subscribe(
            (res: HttpResponse<IConsumption[]>) => {
                this.consumptions = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    ngOnInit() {
        this.loadAll();
        this.principal.identity().then(account => {
            this.currentAccount = account;
        });
        this.registerChangeInConsumptions();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: IConsumption) {
        return item.id;
    }

    registerChangeInConsumptions() {
        this.eventSubscriber = this.eventManager.subscribe('consumptionListModification', response => this.loadAll());
    }

    private onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }
}
