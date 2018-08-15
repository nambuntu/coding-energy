import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';

import { EgatewayCounterModule as EcounterCounterModule } from './ecounter/counter/counter.module';
import { EgatewayConsumptionModule as EcounterConsumptionModule } from './ecounter/consumption/consumption.module';
/* jhipster-needle-add-entity-module-import - JHipster will add entity modules imports here */

@NgModule({
    // prettier-ignore
    imports: [
        EcounterCounterModule,
        EcounterConsumptionModule,
        /* jhipster-needle-add-entity-module - JHipster will add entity modules here */
    ],
    declarations: [],
    entryComponents: [],
    providers: [],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class EgatewayEntityModule {}
