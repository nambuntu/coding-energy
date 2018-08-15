/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { EgatewayTestModule } from '../../../../test.module';
import { ConsumptionComponent } from 'app/entities/ecounter/consumption/consumption.component';
import { ConsumptionService } from 'app/entities/ecounter/consumption/consumption.service';
import { Consumption } from 'app/shared/model/ecounter/consumption.model';

describe('Component Tests', () => {
    describe('Consumption Management Component', () => {
        let comp: ConsumptionComponent;
        let fixture: ComponentFixture<ConsumptionComponent>;
        let service: ConsumptionService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [EgatewayTestModule],
                declarations: [ConsumptionComponent],
                providers: []
            })
                .overrideTemplate(ConsumptionComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(ConsumptionComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ConsumptionService);
        });

        it('Should call load all on init', () => {
            // GIVEN
            const headers = new HttpHeaders().append('link', 'link;link');
            spyOn(service, 'query').and.returnValue(
                of(
                    new HttpResponse({
                        body: [new Consumption(123)],
                        headers
                    })
                )
            );

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.query).toHaveBeenCalled();
            expect(comp.consumptions[0]).toEqual(jasmine.objectContaining({ id: 123 }));
        });
    });
});
