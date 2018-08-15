/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { EgatewayTestModule } from '../../../../test.module';
import { CounterComponent } from 'app/entities/ecounter/counter/counter.component';
import { CounterService } from 'app/entities/ecounter/counter/counter.service';
import { Counter } from 'app/shared/model/ecounter/counter.model';

describe('Component Tests', () => {
    describe('Counter Management Component', () => {
        let comp: CounterComponent;
        let fixture: ComponentFixture<CounterComponent>;
        let service: CounterService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [EgatewayTestModule],
                declarations: [CounterComponent],
                providers: []
            })
                .overrideTemplate(CounterComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(CounterComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(CounterService);
        });

        it('Should call load all on init', () => {
            // GIVEN
            const headers = new HttpHeaders().append('link', 'link;link');
            spyOn(service, 'query').and.returnValue(
                of(
                    new HttpResponse({
                        body: [new Counter(123)],
                        headers
                    })
                )
            );

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.query).toHaveBeenCalled();
            expect(comp.counters[0]).toEqual(jasmine.objectContaining({ id: 123 }));
        });
    });
});
