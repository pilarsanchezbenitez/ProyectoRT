export class Invoice{
    created_at: string;
    id_invoice: number;
    rfc:	string;
    subtotal: number;
    taxes: number;
    total: number;

    constructor(){
        this.created_at="";
        this.id_invoice=0;
        this.rfc="";
        this.subtotal=0.0;
        this.taxes=0.0;
        this.total=0.0;
    }

}