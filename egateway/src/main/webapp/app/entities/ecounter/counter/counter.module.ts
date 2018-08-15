import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { EgatewaySharedModule } from 'app/shared';
import {
    CounterComponent,
    CounterDetailComponent,
    CounterUpdateComponent,
    CounterDeletePopupComponent,
    CounterDeleteDialogComponent,
    counterRoute,
    counterPopupRoute
} from './';

const ENTITY_STATES = [...counterRoute, ...counterPopupRoute];

@NgModule({
    imports: [EgatewaySharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        CounterComponent,
        CounterDetailComponent,
        CounterUpdateComponent,
        CounterDeleteDialogComponent,
        CounterDeletePopupComponent
    ],
    entryComponents: [CounterComponent, CounterUpdateComponent, CounterDeleteDialogComponent, CounterDeletePopupComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class EgatewayCounterModule {}
