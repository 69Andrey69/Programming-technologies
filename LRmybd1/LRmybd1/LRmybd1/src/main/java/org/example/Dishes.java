package org.example;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "dishes")
public class Dishes {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "dishes_id")
    private Long dishes_id;

    @Column(name = "dishes_title", nullable = false)
    private String dishes_title;

    @Column(name = "dishes_description", nullable = false)
    private String dishes_description;
    @Column(name = "dishes_price", nullable = false)
    private Double dishes_price;


    @ManyToOne
    @JoinColumn(name = "orders_id", nullable = false)
    private Orders orders;

    public Dishes(){
    }

    public Dishes(String dishes_title, String dishes_description, Double dishes_price, Orders orders) {
        this.dishes_title = dishes_title;
        this.dishes_description = dishes_description;
        this.orders = orders;
        this.dishes_price = dishes_price;
    }

    @Override
    public String toString() {
        return "Блюдо(" + dishes_id + ", " + dishes_title + ", " + dishes_description + ")";
    }

    public Long getDishesId() {
        return dishes_id;
    }

    public String getDishesTitle() {
        return dishes_title;
    }

    public String getDishesDescription() {
        return dishes_description;
    }
    public Double getDishesPrice() {
        return dishes_price;
    }

    public void setDishesId(Long dishes_id) {
        this.dishes_id = dishes_id;
    }

    public void setDishesTitle(String dishes_title) {
        this.dishes_title = dishes_title;
    }

    public void setDishesDescription(String dishes_description) {
        this.dishes_description = dishes_description;
    }
    public void setDishesPrice(Double dishes_price) {
        this.dishes_price = dishes_price;
    }

    public Orders getOrders() {
        return orders;
    }

}
