export class Item{
    gtin:	string;
    id_invoice:	number;
    id_item: number;
    quantity: number;
    subtotal:	number;
    taxes:	number;
    total:	number;
    unit_price:	number;

    constructor(){
        this.gtin="";
        this.id_invoice=0;
        this.id_item=0;
        this.quantity=0;
        this.subtotal=0.0;
        this.taxes=0.0;
        this.total=0.0;
        this.unit_price=0.0;
    }
}
