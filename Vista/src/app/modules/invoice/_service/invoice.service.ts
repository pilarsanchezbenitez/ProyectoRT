import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { ApisURI } from 'src/app/shared/apis-uri';
import { RFCs } from 'src/app/shared/rfcs';
import { Invoice } from '../_model/invoice';
import { Item } from '../_model/item';
@Injectable({
  providedIn: 'root'
})
export class InvoiceService {

  private apiURI = ApisURI.dwf20221apiURI;
  private rfc = RFCs.rfc;
  private resource = "/invoice";

  constructor(
    private http: HttpClient
  ) { }

  getCart(){
    return this.http.get<Invoice[]>(this.apiURI + this.resource + "/"+ this.rfc);
  }

  purchase(){
    return this.http.post(this.apiURI + this.resource +"/"+ this.rfc, this.rfc);
  }

  getItems(id_invoice: number){
    return this.http.get<Item[]>(this.apiURI + this.resource + "/item/" + id_invoice );
  }

}
