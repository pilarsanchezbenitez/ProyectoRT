import { Component, OnInit } from '@angular/core';
import { Customer } from '../../_model/customer';
import { CustomerImage } from '../../_model/customerimage';
import { Region } from '../../_model/region';
import { CustomerService} from '../../_service/customer.service';
import { RegionService} from '../../_service/region.service';
import { FormBuilder, Validators} from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { CroppedEvent } from 'ngx-photo-editor';

declare var $: any;

import Swal from 'sweetalert2';

@Component({
  selector: 'app-customer-detail',
  templateUrl: './customer-detail.component.html',
  styleUrls: ['./customer-detail.component.css']
})
export class CustomerDetailComponent implements OnInit {

  customer: Customer = new Customer();
  rfc: any = null;
  regions: Region[] = [];
  region: Region = new Region();
  image: CustomerImage = new CustomerImage();
  file: any;
  imageChangedEvent: any; 
  base64: any;
  formulario = this.formBuilder.group({
    id_customer: [''],
    name: ['', Validators.required],
    surname: ['', Validators.required],
    rfc: ['', Validators.required],
    mail: ['', Validators.required],
    address: ['', Validators.required],
    status: [''],
  });
  formularioRegion = this.formBuilder.group({
    id_region: ['', Validators.required],
  });

  submitted = false;

  constructor(
    private customer_service: CustomerService,
    private region_service: RegionService,
    private formBuilder: FormBuilder,
    private route: ActivatedRoute,
    private router: Router
  ) { }

  ngOnInit(): void {
    this.rfc = this.route.snapshot.paramMap.get('rfc');
    this.getCustomer(this.rfc);
  }

  getCustomer(rfc: string){
    this.customer_service.getCustomer(rfc).subscribe(
      res =>{
        this.customer = res;
        this.getRegion(this.customer.id_region);
      },
      err => console.log(err)
    )
  }

  getRegions(){
    this.region_service.getRegions().subscribe(
      res =>{
        this.regions = res;
        console.log(this.region);
      },
      err => console.log(err)
    )
  }

  getRegion(id_region: number){
    this.region_service.getRegion(id_region).subscribe(
      res => {
        this.region = res;
      },
      err => console.log(err)
    )
  }

  onSubmit(){
    this.submitted = true;
    this.customer_service.updateCustomer(this.formulario.value).subscribe(
      res =>{
        this.rfc = this.formulario.controls['rfc'].value;
        this.getCustomer(this.rfc);
        this.router.navigate(['customer-detail/'+this.rfc]);
        this.closeModal();
        Swal.fire({
          position: 'top-end',
          icon: 'success',
          title: 'Actualización exitosa!!',
          showConfirmButton: false,
          timer: 1500
        })
      },
      err =>{ 
        console.log(err);
        Swal.fire({
          icon: 'error',
          title: 'Error!',
          text: 'El cliente no puede ser actualizado'
        })
      }
    )
  }

  updateCustomer(customer: Customer){
    this.formulario.controls['id_customer'].setValue(customer.id_customer);
    this.formulario.controls['name'].setValue(customer.name);
    this.formulario.controls['surname'].setValue(customer.surname);
    this.formulario.controls['rfc'].setValue(customer.rfc);
    this.formulario.controls['mail'].setValue(customer.mail);
    this.formulario.controls['address'].setValue(customer.address);
    this.formulario.controls['status'].setValue(customer.status);
    $("#customer_modal").modal("show");
  }

  get f(){
    return this.formulario.controls;
  }

  closeModal(){
    $("#customer_modal").modal("hide");
    this.submitted = false;
  }

  closeRegionModal(){
    $("#region_modal").modal("hide");
    this.submitted = false;
  }

  closeImageModal(){
    $("image_modal").modal("hide");
    this.submitted = false;
  }

  updateCustomerRegion(){
    this.getRegions();
    this.formularioRegion.controls['id_region'].setValue(this.customer.id_region);
    $("#region_modal").modal("show");
  }

  updateCustomerImage(){
    $("#image_modal").modal("show");
  }

  onSubmitRegion(){
    this.region = new Region();
    this.region.id_region = this.formularioRegion.controls['id_region'].value;
    this.customer_service.updateCustomerRegion(this.customer.id_customer, this.region).subscribe(
      res =>{
        this.getCustomer(this.rfc);
        this.closeRegionModal();
        Swal.fire({
          position: 'top-end',
          icon: 'success',
          title: 'Actualización exitosa!!',
          showConfirmButton: false,
          timer: 1500
        })
      },
      err =>{ 
        console.log(err);
        Swal.fire({
          icon: 'error',
          title: 'Error!',
          text: 'El cliente no puede ser actualizado'
        })
      }
    )
  }

  onSubmitImage(){
    this.customer_service.updateCustomerImage(this.customer.id_customer, this.image).subscribe(
      res =>{
        this.getCustomer(this.rfc);
        this.closeImageModal();
        Swal.fire({
          position: 'top-end',
          icon: 'success',
          title: 'Actualización exitosa!!',
          showConfirmButton: false,
          timer: 1500
        })
      },
      err =>{ 
        console.log(err);
        Swal.fire({
          icon: 'error',
          title: 'Error!',
          text: 'El cliente no puede ser actualizado'
        })
      }
    )
  }

  fileChangeEvent(event: any){
    this.imageChangedEvent = event;
  }

  imageCropped(event: CroppedEvent){
    this.base64 = event.base64;
    this.image.image = this.base64;
  }

}
