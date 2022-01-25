import { Component, OnInit } from '@angular/core';
import { Product } from 'src/app/modules/product/_model/product';
import { ProductImage } from 'src/app/modules/product/_model/productImage';
import { ProductImageService } from 'src/app/modules/product/_service/product-image.service';
import { ProductService } from 'src/app/modules/product/_service/product.service';
import { CartService } from 'src/app/modules/cart/_service/cart.service';
import { Category } from 'src/app/modules/product/_model/category';
import { CategoryService } from 'src/app/modules/product/_service/category.service';
import { FormBuilder, Validators } from '@angular/forms';
import { Router } from '@angular/router';

  declare var $: any;

import Swal from 'sweetalert2';
import { Cart } from 'src/app/modules/cart/_model/cart';
import { RFCs } from 'src/app/shared/rfcs';


@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent implements OnInit {

  products: Product[] = [];
  product: Product = new Product();

  categories: Category[] = [];
  category: Category = new Category();

  images: ProductImage [] = [];
  image: ProductImage = new ProductImage();
  file: any;
  imageChangedEvent: any;
  base64: any;

  productsWithImages: ProductoWithImage[] = [];

  cart: Cart = new Cart();

  formularioCategory = this.formBuilder.group({
    id_category: ['', Validators.required],
  });

  formularioQuantity =  this.formBuilder.group({
    quantity: ['', Validators.required]
  });

  submitted = false;

  constructor(
    private product_service: ProductService,
    private product_image_service: ProductImageService,
    private category_service: CategoryService,
    private cart_service: CartService,
    private formBuilder: FormBuilder,
    private router: Router
  ) { }

  ngOnInit(): void {
    this.getProductsRandom();
    this.cart.rfc = RFCs.rfc;
  }

  getProductsRandom(){
    this.product_service.getProductsRandom().subscribe(
      res => {
        this.products = res;
        this.products.forEach(
          producto => {
            let productWithImages = new ProductoWithImage();
            this.product_service.getProduct(producto.gtin).subscribe(
              nuevo =>{
                productWithImages.producto = nuevo
              },
              err => console.log(err)
            )
            this.product_image_service.getProductImages(producto.id_product).subscribe(
              res => {
                productWithImages.productImages = res;
                this.productsWithImages.push(productWithImages)
                },
                err => console.log(err)
            )
          }
        )
        console.log(this.products)
      },
      err =>  console.log(err)
    )
  }

  getProduct(gtin: string){
    this.product_service.getProduct(gtin).subscribe(
      res => {
        this.product = res;
        this.getCategory(this.product.id_category);
        this.getProductImages(this.product.id_product);
      },
      err => console.log(err)
    )
  }

  getCategory(id_category: number){
    this.category_service.getCategory(id_category).subscribe(
      res => {
        this.category = res;
      },
      err => console.log(err)
    )
  }

  getProductImages(id_product: number){
    this.product_image_service.getProductImages(id_product).subscribe(
    res => {
        this.images = res;
        console.log(this.images);
      },
      err => console.log(err)
    )
  }

  getProductsCategory(id_category: number){
    this.products =[];
    this.productsWithImages = [];
    this.product_service.getProductsCategory(id_category).subscribe(
      res => {
        this.products=[];
        this.productsWithImages = [];
        this.products = res;
        this.products.forEach(
          producto => {
            let productWithImages = new ProductoWithImage();
            productWithImages.producto = producto
            this.product_image_service.getProductImages(producto.id_product).subscribe(
              res => {
                productWithImages.productImages = res;
                this.productsWithImages.push(productWithImages)
                },
                err => console.log(err)
              )
          }
        )
        console.log(this.products)
      },
      err =>  console.log(err)
    )
  }

  getCategories(){
    this.category_service.getCategories().subscribe(
      res => {
        this.categories = res;
      },
      err => console.log(err)
    )
  }


  updateProductCategory(){
    this.getCategories();
    this.formularioCategory.controls['id_category'].setValue(this.product.id_category);
    $("#category_modal").modal("show");
  }

  onSubmitCategory(){
    this.category = new Category();
    this.category.id_category = this.formularioCategory.controls['id_category'].value;
    this.category_service.getCategories().subscribe(
      res => {
    this.productsWithImages = [];
    this.products =[];
        this.getProductsCategory(this.category.id_category);
        this.closeCategoryModal();
        Swal.fire({
          position: 'top-end',
          icon: 'success',
          showConfirmButton: false,
          timer: 1500
        })
      },
      err => {
        console.log(err);
        Swal.fire({
          icon: 'error',
          title: 'Error!',
          text: 'La categoria no existe'
        })
      }
    )
  }

  closeCategoryModal(){
    $("#category_modal").modal("hide");
    this.submitted = false;
  }

  getDetail(gtin: string){
    this.getProduct(gtin);
    $("#detail_product").modal("show");
  }

  closeDetail(){
    $("#detail_product").modal("hide");
    this.submitted = false;
  }

  cartV(){
    this.router.navigate(['cart/']);
  }

  addCart(product: Product){
    console.log(product)
    this.cart.product = product;
    this.cart.id_product = product.id_product;
    this.cart.quantity = this.formularioQuantity.controls['quantity'].value;
    console.log(this.cart)
    this.cart_service.addToCart(this.cart).subscribe(
      res => {
        Swal.fire({
          position: 'top-end',
              icon: 'success',
              title: 'Se agrego al carrito exitosamente!',
              showConfirmButton: false,
              timer:1500
        })
        this.closeDetail()
      },
      err => {
        console.log(err);
        Swal.fire({
        icon: 'error',
          title: 'Error!',
          text: 'Seleccione una cantidad valida',
        }) 
    }
    )
  }
  get f(){
    return this.formularioQuantity.controls;
  }
}

class ProductoWithImage{
  producto : Product = new Product();
  productImages: ProductImage[]  = [];
}
