import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { ICounter } from 'app/shared/model/ecounter/counter.model';
import { Principal } from 'app/core';
import { CounterService } from './counter.service';

@Component({
    selector: 'jhi-counter',
    templateUrl: './counter.component.html'
})
export class CounterComponent implements OnInit, OnDestroy {
    counters: ICounter[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private counterService: CounterService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {}

    loadAll() {
        this.counterService.query().subscribe(
            (res: HttpResponse<ICounter[]>) => {
                this.counters = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    ngOnInit() {
        this.loadAll();
        this.principal.identity().then(account => {
            this.currentAccount = account;
        });
        this.registerChangeInCounters();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: ICounter) {
        return item.id;
    }

    registerChangeInCounters() {
        this.eventSubscriber = this.eventManager.subscribe('counterListModification', response => this.loadAll());
    }

    private onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }
}
