import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { ICounter } from 'app/shared/model/ecounter/counter.model';
import { CounterService } from './counter.service';

@Component({
    selector: 'jhi-counter-update',
    templateUrl: './counter-update.component.html'
})
export class CounterUpdateComponent implements OnInit {
    private _counter: ICounter;
    isSaving: boolean;

    constructor(private counterService: CounterService, private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ counter }) => {
            this.counter = counter;
        });
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.counter.id !== undefined) {
            this.subscribeToSaveResponse(this.counterService.update(this.counter));
        } else {
            this.subscribeToSaveResponse(this.counterService.create(this.counter));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<ICounter>>) {
        result.subscribe((res: HttpResponse<ICounter>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    private onSaveError() {
        this.isSaving = false;
    }
    get counter() {
        return this._counter;
    }

    set counter(counter: ICounter) {
        this._counter = counter;
    }
}
