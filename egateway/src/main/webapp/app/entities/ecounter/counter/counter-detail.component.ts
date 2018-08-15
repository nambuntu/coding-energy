import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ICounter } from 'app/shared/model/ecounter/counter.model';

@Component({
    selector: 'jhi-counter-detail',
    templateUrl: './counter-detail.component.html'
})
export class CounterDetailComponent implements OnInit {
    counter: ICounter;

    constructor(private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ counter }) => {
            this.counter = counter;
        });
    }

    previousState() {
        window.history.back();
    }
}
