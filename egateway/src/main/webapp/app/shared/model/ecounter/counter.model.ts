import { IConsumption } from 'app/shared/model/ecounter/consumption.model';

export interface ICounter {
    id?: number;
    villageName?: string;
    consumptions?: IConsumption[];
}

export class Counter implements ICounter {
    constructor(public id?: number, public villageName?: string, public consumptions?: IConsumption[]) {}
}
