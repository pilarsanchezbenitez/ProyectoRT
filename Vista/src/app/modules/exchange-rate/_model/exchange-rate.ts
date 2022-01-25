export class ExchangeRate{
    provider!: string;
    WARNING_UPGRADE_TO_V6!: string;
    terms!: string;
    base!: string;
    date !: Date;
    time_last_update!: number;
    rates!: Map<"rate", "value">;

    constructor(){
    this.provider = "";
    this.WARNING_UPGRADE_TO_V6 = "";
    this.terms= "";
    this.time_last_update = 0;
    this.base="";
    this.date = new Date;
    this.rates = new Map;
    }
}