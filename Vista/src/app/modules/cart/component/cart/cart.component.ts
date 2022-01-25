import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router} from '@angular/router';
import { FormBuilder, Validators } from '@angular/forms';
import { CartService } from '../../_service/cart.service';
import { InvoiceService } from 'src/app/modules/invoice/_service/invoice.service';
import { Product } from 'src/app/modules/product/_model/product';
import { Cart } from '../../_model/cart';
import { RFCs } from 'src/app/shared/rfcs';
import Swal from 'sweetalert2';
declare var $:any;

@Component({
  selector: 'app-cart',
  templateUrl: './cart.component.html',
  styleUrls: ['./cart.component.css']
})
export class CartComponent implements OnInit {

  cart: Cart[] = [];
  product: Product = new Product();
  rfc: any = RFCs.rfc;

  formTarjeta = this.formBuilder.group({
      numTarjeta:['',Validators.required],
      ccv: ['', Validators.required],
      mesvencimiento: ['', Validators.required],
      });

  post_addCart = false;
  submitted = false;
  constructor(
    private formBuilder: FormBuilder,
    private cart_service: CartService,
    private invoice_service: InvoiceService,
    private router: Router
  ) { }

  ngOnInit(): void {
    this.getCart();
  }

  getCart(){
    this.cart_service.getCart().subscribe(
      res => {
        this.cart = res;
        console.log(this.cart);
      },
      err => console.log(err)
    )
  }

  addToCart(cart:Cart){
    this.cart_service.addToCart(cart).subscribe(
      res => {
        Swal.fire({
          position: 'top-end',
              icon: 'success',
              title: 'Se agrego al carrito exitosamente!',
              showConfirmButton: false,
              timer:1500
        })
      },
      err => {
        Swal.fire({
        icon: 'error',
          title: 'Error!',
          text: 'No se puede agregar al carrito',
        })
      }
    )
  }

  removeFromCart(id_cart: number){
    Swal.fire({
      title: 'Deseas eliminar el producto?',
      icon: 'warning',
      showCancelButton: true,
      cancelButtonColor: '#d33',
      confirmButtonText:'Si, eliminar!',
    }).then((result) => {
      if (result.isConfirmed) {
        this.cart_service.removeFromCart(id_cart).subscribe(
          res => {
            this.getCart();
            Swal.fire({
              position: 'top-end',
              icon: 'success',
              title: 'Eliminación exitosa!',
              showConfirmButton: false,
              timer: 1500
            })
          },
          err => {
            console.log(err);
            Swal.fire({
              icon: 'error',
              title: 'Error!',
              text: 'El producto no puede ser eliminado'
            })
          }
        )
      }
    }
    )
  }

  deleteCart(){
    Swal.fire({
      title: 'Deseas eliminar el carrito de compras',
      icon: 'warning',
      showCancelButton: true,
      confirmButtonColor: '#3085d6',
      cancelButtonColor: '#d33',
      confirmButtonText: 'Si, eliminar!',
    }).then((result) =>{
      if(result.isConfirmed){
        this.cart_service.deleteCart(this.rfc).subscribe(
          res => {
            this.getCart();
            Swal.fire({
              position: 'top-end',
              icon: 'success',
              title: 'Eliminación exitosa!',
              showConfirmButton: false,
              timer:1500
            })
          },
          err => {
            console.log(err);
            Swal.fire({
            icon: 'error',
              title: 'Error!',
              text: 'El carrito no puede ser eliminado',
            })
          }
        )
      }
    }
    )
  }

  updateTarjeta(){
    this.formTarjeta.reset();
    $("#tarjeta_modal").modal("show");
  }

  validaNumTarjeta(){
    if(this.formTarjeta.controls['numTarjeta'].value.length != 16){
      Swal.fire({
        icon: 'error',
        title: 'Error!',
        text: ' Error en el numero de tarjeta',
      })
      return;
    }
  }

  validaCcv(){
    if(this.formTarjeta.controls['ccv'].value.legth != 3){
      Swal.fire({
        icon: 'error',
        title: 'Error!',
        text: ' Error en el numero CCV',
      })
      return;
    }
  }

  onSubmitPurchase(){
    this.submitted = true;
    if(this.formTarjeta.invalid){
      Swal.fire({
        icon: 'error',
        title: 'Error!',
        text: 'Error en algun campo',
      })
      return;
    }
        
    this.invoice_service.purchase().subscribe(
      res =>{
        this.invoice_service.getCart();
        this.getCart();
        this.closeModalPurchase();
        Swal.fire({
          position: 'top-end',
          icon: 'success',
          title: 'Compra exitosa',
          showConfirmButton: false,
          timer: 1500
        })
      },
      err => {
        this.invoice_service.getCart();
        console.log(err);
        Swal.fire({
          icon: 'error',
          title: 'Error!',
          text: 'No se pudo realizar la compra',
        })
      }
    )
  }

  closeModalPurchase(){
    $("#tarjeta_modal").modal("hide");
    this.submitted = false;
  }
  
  get f(){
    return this.formTarjeta.controls;
  }
}
