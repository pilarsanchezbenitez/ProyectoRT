import { Product } from "../../product/_model/product";

export class Cart{
    gtin: number;
    id_cart: number;
    id_product: number;
    quantity: number;
    rfc: string;
    product: Product;

    constructor(){
        this.gtin=0;
        this.id_cart=0;
        this.id_product=0;
        this.quantity=0;
        this.rfc="";
        this.product = new Product();

    }
}
