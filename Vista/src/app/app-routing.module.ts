import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { RegionComponent } from './modules/customer/component/region/region.component';
import { ExchangeRateComponent } from './modules/exchange-rate/component/exchange-rate/exchange-rate.component';
import { CategoryComponent } from './modules/product/component/category/category.component';
import { HomeComponent } from './modules/home/component/home/home.component';
import { CartComponent } from './modules/cart/component/cart/cart.component';
import { InvoiceComponent } from './modules/invoice/component/invoice/invoice.component';


const routes: Routes = [
  {path:'exchange-rate', component: ExchangeRateComponent},
  {path:'region', component: RegionComponent},
  {path:'category', component: CategoryComponent},
  {path: 'cart', component: CartComponent},
  {path:'home', component: HomeComponent},
  {path: "facturas", component: InvoiceComponent},
  {path: '', redirectTo: 'home' , pathMatch: 'full'}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
