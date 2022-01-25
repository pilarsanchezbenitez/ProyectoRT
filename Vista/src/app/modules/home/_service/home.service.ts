import { Injectable } from '@angular/core';
import {HttpClient} from '@angular/common/http';
import { ApisURI } from '../../../shared/apis-uri';
import { Product } from '../../product/_model/product';
import { Category } from '../../product/_model/category';
@Injectable({
  providedIn: 'root'
})
export class HomeService {

  private apiURI = ApisURI.dwf20221apiURI;
  private resource = "/home";

  constructor(
    private http: HttpClient
  ) { }
  getProductsCategory(id_category: number){
    console.log(id_category);
    return this.http.get<Product[]>(this.apiURI + this.resource + "/category" + "/" + id_category);
  }
  getProductsRandom(){
    return this.http.get<Product[]>(this.apiURI + this.resource);
  }
}
