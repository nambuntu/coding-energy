import {CUSTOM_ELEMENTS_SCHEMA, NgModule} from '@angular/core';
import {RouterModule} from '@angular/router';

import {EgatewaySharedModule} from 'app/shared';
import {ConsumptionComponent, consumptionRoute, ConsumptionUpdateComponent,} from './';

const ENTITY_STATES = [...consumptionRoute];

@NgModule({
    imports: [EgatewaySharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        ConsumptionComponent,
        ConsumptionUpdateComponent,
    ],
    entryComponents: [ConsumptionComponent, ConsumptionUpdateComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class EgatewayConsumptionModule {
}
