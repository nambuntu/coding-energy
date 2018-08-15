import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { EgatewaySharedModule } from 'app/shared';
import {
    CounterComponent,
    counterRoute,
} from './';

const ENTITY_STATES = [...counterRoute];

@NgModule({
    imports: [EgatewaySharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        CounterComponent,
    ],
    entryComponents: [CounterComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class EgatewayCounterModule {}
