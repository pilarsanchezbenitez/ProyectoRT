import { Component, OnInit } from '@angular/core';

import { Invoice } from '../../_model/invoice';
import { Item } from '../../_model/item';
import { InvoiceService } from '../../_service/invoice.service';
declare var $: any;

  import Swal from 'sweetalert2';

@Component({
  selector: 'app-invoice',
  templateUrl: './invoice.component.html',
  styleUrls: ['./invoice.component.css']
})
export class InvoiceComponent implements OnInit {

  invoices: Invoice[] = [];
  items: Item[] = [];
  info: Invoice = new Invoice();

  constructor(
    private invoice_service: InvoiceService
  ) { }

  ngOnInit(): void {
    this.getCart()
  }

  getCart(){
    this.invoice_service.getCart().subscribe(
      res => {
        this.invoices = res;
        console.log(this.invoices);
      },
      err => console.log(err)
    )
  }

  purchase(){
    this.invoice_service.purchase().subscribe(
      res => {
        this.getCart();
        Swal.fire({
          position: 'top-end',
          icon: 'success',
          title: 'Compra exitosa',
          showConfirmButton: false,
          timer: 1500
        })
      },
      err => {
        console.log(err);
        Swal.fire({
          icon: 'error',
          title: 'Error!',
          text: 'No se puede realizar la compra',
        })
      }
    )
  }

  getItems(id_invoice: number,invoiceX: Invoice ){
    this.info = invoiceX
    $("#info_invoice").modal("show");
    this.invoice_service.getItems(id_invoice).subscribe(
      res => {
        this.items = res;
        console.log(this.items)

      },
      err => console.log(err)
    )
  }

  closeInfo(){
    $("#info_invoice").modal("hide");
  }


}
