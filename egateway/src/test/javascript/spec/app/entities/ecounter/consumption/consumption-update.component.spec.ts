/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { EgatewayTestModule } from '../../../../test.module';
import { ConsumptionUpdateComponent } from 'app/entities/ecounter/consumption/consumption-update.component';
import { ConsumptionService } from 'app/entities/ecounter/consumption/consumption.service';
import { Consumption } from 'app/shared/model/ecounter/consumption.model';

describe('Component Tests', () => {
    describe('Consumption Management Update Component', () => {
        let comp: ConsumptionUpdateComponent;
        let fixture: ComponentFixture<ConsumptionUpdateComponent>;
        let service: ConsumptionService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [EgatewayTestModule],
                declarations: [ConsumptionUpdateComponent]
            })
                .overrideTemplate(ConsumptionUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(ConsumptionUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ConsumptionService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new Consumption(123);
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.consumption = entity;
                    // WHEN
                    comp.save();
                    tick(); // simulate async

                    // THEN
                    expect(service.update).toHaveBeenCalledWith(entity);
                    expect(comp.isSaving).toEqual(false);
                })
            );

            it(
                'Should call create service on save for new entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new Consumption();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.consumption = entity;
                    // WHEN
                    comp.save();
                    tick(); // simulate async

                    // THEN
                    expect(service.create).toHaveBeenCalledWith(entity);
                    expect(comp.isSaving).toEqual(false);
                })
            );
        });
    });
});
