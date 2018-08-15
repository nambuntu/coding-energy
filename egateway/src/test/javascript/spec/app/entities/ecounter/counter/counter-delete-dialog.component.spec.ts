/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { EgatewayTestModule } from '../../../../test.module';
import { CounterDeleteDialogComponent } from 'app/entities/ecounter/counter/counter-delete-dialog.component';
import { CounterService } from 'app/entities/ecounter/counter/counter.service';

describe('Component Tests', () => {
    describe('Counter Management Delete Component', () => {
        let comp: CounterDeleteDialogComponent;
        let fixture: ComponentFixture<CounterDeleteDialogComponent>;
        let service: CounterService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [EgatewayTestModule],
                declarations: [CounterDeleteDialogComponent]
            })
                .overrideTemplate(CounterDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(CounterDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(CounterService);
            mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
            mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
        });

        describe('confirmDelete', () => {
            it('Should call delete service on confirmDelete', inject(
                [],
                fakeAsync(() => {
                    // GIVEN
                    spyOn(service, 'delete').and.returnValue(of({}));

                    // WHEN
                    comp.confirmDelete(123);
                    tick();

                    // THEN
                    expect(service.delete).toHaveBeenCalledWith(123);
                    expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    expect(mockEventManager.broadcastSpy).toHaveBeenCalled();
                })
            ));
        });
    });
});
