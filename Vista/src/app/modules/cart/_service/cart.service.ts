import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { ApisURI } from 'src/app/shared/apis-uri';
import { RFCs } from 'src/app/shared/rfcs';
import { Product } from '../../product/_model/product';
import { Cart } from '../_model/cart';
@Injectable({
  providedIn: 'root'
})
export class CartService {

  private apiURI = ApisURI.dwf20221apiURI;
  private rfc = RFCs.rfc;
  private resource = "/cart";

  constructor(
    private http: HttpClient
  ) { }

  addToCart(cart: Cart){
    return this.http.post<Cart>(this.apiURI + this.resource, cart);
  }

  getCart(){
    console.log(this.rfc);
    return this.http.get<Cart[]>(this.apiURI + this.resource + "/" + this.rfc);
  }

  removeFromCart(id_cart: number){
    return this.http.delete(this.apiURI + this.resource + "/" + id_cart);
  }

  deleteCart(rfc: string){
    return this.http.delete(this.apiURI + this.resource + "/clear/" + rfc);
  }
}
