/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { EgatewayTestModule } from '../../../../test.module';
import { CounterUpdateComponent } from 'app/entities/ecounter/counter/counter-update.component';
import { CounterService } from 'app/entities/ecounter/counter/counter.service';
import { Counter } from 'app/shared/model/ecounter/counter.model';

describe('Component Tests', () => {
    describe('Counter Management Update Component', () => {
        let comp: CounterUpdateComponent;
        let fixture: ComponentFixture<CounterUpdateComponent>;
        let service: CounterService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [EgatewayTestModule],
                declarations: [CounterUpdateComponent]
            })
                .overrideTemplate(CounterUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(CounterUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(CounterService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new Counter(123);
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.counter = entity;
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
                    const entity = new Counter();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.counter = entity;
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
