import { Moment } from 'moment';
import { ICounter } from 'app/shared/model/ecounter/counter.model';

export interface IConsumption {
    id?: number;
    amount?: number;
    dateCreated?: Moment;
    counter?: ICounter;
}

export class Consumption implements IConsumption {
    constructor(public id?: number, public amount?: number, public dateCreated?: Moment, public counter?: ICounter) {}
}
