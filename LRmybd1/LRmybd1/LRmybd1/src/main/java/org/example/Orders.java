package org.example;

import javax.persistence.*;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "orders")
public class Orders {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "orders_id")
    private Long orders_id;

    @Column(name = "orders_date", nullable = false)
    private Instant orders_date;

    @Column(name = "orders_amount", nullable = false)
    private Double orders_amount;

    @ManyToOne
    @JoinColumn(name = "client_id", nullable = false)
    private Client client;

    @OneToMany(mappedBy = "orders")
    private List<Dishes> dishes;

    public Orders(){
    }

    public Orders(Instant orders_date, Double orders_amount, Client client) {
        this.orders_date = orders_date;
        this.orders_amount = orders_amount;
        this.client = client;
        this.dishes = new ArrayList<>();;
    }

    @Override
    public String toString() {
        return "Заказ(" + orders_id + ", " + orders_date + ", " + orders_amount + ")";
    }

    public Long getOrdersId() {
        return orders_id;
    }

    public Instant getOrdersDate() { return orders_date; }

    public Double getOrdersAmount() {
        return orders_amount;
    }

    public Client getclient() {
        return client;
    }

    public void setOrdersId(Long orders_id) {
        this.orders_id = orders_id;
    }

    public void setOrdersDate(Instant orders_date) {
        this.orders_date = orders_date;
    }

    public void setOrdersAmount(Double orders_amount) {
        this.orders_amount = orders_amount;
    }

    public List<Dishes> getDishes() {
        return dishes;
    }

}
