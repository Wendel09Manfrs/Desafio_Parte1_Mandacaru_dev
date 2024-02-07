package com.mandacarubroker.domain.stock;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

//@Table(name ="stock")
@Entity(name="stock")
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of="id")
public class Stock {

    @Id @GeneratedValue(strategy = GenerationType.UUID)
   //@Column(name = "id" )
    private String id;
    @Column(unique= true )
    private String symbol;
    //@Column(name = "companyName" )
    private String companyName;
    //@Column(name = "price" )
    private double price;

    public Stock(RequestStockDTO requestStockDTO){
        this.symbol = requestStockDTO.symbol();
        this.companyName = requestStockDTO.companyName();
        this.price = changePrice(requestStockDTO.price(), true);
    }

    public double changePrice(double amount, boolean increase) {
        if (increase) {
            if (amount < this.price) {
                return increasePrice(amount);
            } else {
                return decreasePrice(amount);
            }
        } else {
            if (amount > this.price) {
                return increasePrice(amount);
            } else {
                //erro sem sentido
//                return this.decreasePrice(amount);
                return decreasePrice(amount);
            }
        }
    }

    public double increasePrice(double amount) {
        return this.price + amount;
    }

    public double decreasePrice(double amount) {
        return this.price - amount;
    }

}