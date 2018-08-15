import { NgModule } from '@angular/core';

import { EgatewaySharedLibsModule, JhiAlertComponent, JhiAlertErrorComponent } from './';

@NgModule({
    imports: [EgatewaySharedLibsModule],
    declarations: [JhiAlertComponent, JhiAlertErrorComponent],
    exports: [EgatewaySharedLibsModule, JhiAlertComponent, JhiAlertErrorComponent]
})
export class EgatewaySharedCommonModule {}
