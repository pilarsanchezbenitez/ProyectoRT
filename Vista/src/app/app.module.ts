import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';

import { HttpClientModule } from '@angular/common/http';
import { FormsModule } from '@angular/forms';
import { LayoutModule } from './modules/layout/layout.module';
import { ExchangeRateModule} from './modules/exchange-rate/exchange-rate.module';
import { CustomerModule } from './modules/customer/customer.module';
import { ReactiveFormsModule } from '@angular/forms';
import { ProductModule } from './modules/product/product.module';
import { HomeModule } from './modules/home/home.module';
import { CartModule } from './modules/cart/cart.module';
import { InvoiceModule } from './modules/invoice/invoice.module';

@NgModule({
  declarations: [
    AppComponent,
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    LayoutModule,
    HttpClientModule,
    ExchangeRateModule,
    CustomerModule,
    FormsModule,
    ReactiveFormsModule,
    ProductModule,
    HomeModule,
    CartModule, 
    InvoiceModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
