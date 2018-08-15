/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { EgatewayTestModule } from '../../../../test.module';
import { CounterDetailComponent } from 'app/entities/ecounter/counter/counter-detail.component';
import { Counter } from 'app/shared/model/ecounter/counter.model';

describe('Component Tests', () => {
    describe('Counter Management Detail Component', () => {
        let comp: CounterDetailComponent;
        let fixture: ComponentFixture<CounterDetailComponent>;
        const route = ({ data: of({ counter: new Counter(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [EgatewayTestModule],
                declarations: [CounterDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(CounterDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(CounterDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.counter).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
