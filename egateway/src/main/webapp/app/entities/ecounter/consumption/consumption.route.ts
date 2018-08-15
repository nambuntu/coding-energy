import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { of } from 'rxjs';
import { map } from 'rxjs/operators';
import { Consumption } from 'app/shared/model/ecounter/consumption.model';
import { ConsumptionService } from './consumption.service';
import { ConsumptionComponent } from './consumption.component';
import { ConsumptionUpdateComponent } from './consumption-update.component';
import { IConsumption } from 'app/shared/model/ecounter/consumption.model';

@Injectable({ providedIn: 'root' })
export class ConsumptionResolve implements Resolve<IConsumption> {
    constructor(private service: ConsumptionService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(map((consumption: HttpResponse<Consumption>) => consumption.body));
        }
        return of(new Consumption());
    }
}

export const consumptionRoute: Routes = [
    {
        path: 'consumption',
        component: ConsumptionComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Consumptions'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'consumption/new',
        component: ConsumptionUpdateComponent,
        resolve: {
            consumption: ConsumptionResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Consumptions'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'consumption/:id/edit',
        component: ConsumptionUpdateComponent,
        resolve: {
            consumption: ConsumptionResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Consumptions'
        },
        canActivate: [UserRouteAccessService]
    }
];
