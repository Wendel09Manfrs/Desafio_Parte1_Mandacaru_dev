package com.mandacarubroker.domain.stock;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Table(name ="stock")
@Entity(name="stock")
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of="id")
public class Stock {

    @Id @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    private String symbol;
    private String companyName;
    private double price;
    private double variation;

    public Stock(RequestStockDTO requestStockDTO){
        this.symbol = requestStockDTO.symbol();
        this.companyName = requestStockDTO.companyName();
        this.price = changePrice(requestStockDTO.price());
    }

    public double changePrice(double amount) {
        if(this.price!=0){
        this.variation = ((amount - this.price)/this.price)*100;}

           return amount;

    }

    public double increasePrice(double amount) {
        return this.price + amount;
    }
    public double decreasePrice(double amount) {
        return this.price - amount;
    }

}