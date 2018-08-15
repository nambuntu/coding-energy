import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { of } from 'rxjs';
import { map } from 'rxjs/operators';
import { Counter } from 'app/shared/model/ecounter/counter.model';
import { CounterService } from './counter.service';
import { CounterComponent } from './counter.component';
import { ICounter } from 'app/shared/model/ecounter/counter.model';

@Injectable({ providedIn: 'root' })
export class CounterResolve implements Resolve<ICounter> {
    constructor(private service: CounterService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(map((counter: HttpResponse<Counter>) => counter.body));
        }
        return of(new Counter());
    }
}

export const counterRoute: Routes = [
    {
        path: 'counter',
        component: CounterComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Counters'
        },
        canActivate: [UserRouteAccessService]
    },
];
