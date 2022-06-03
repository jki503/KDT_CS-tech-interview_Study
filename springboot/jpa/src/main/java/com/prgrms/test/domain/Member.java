package com.prgrms.test.domain;

import javax.persistence.*;

@Entity
@Table(name = "members")
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    private String name;

    @Embedded
    private Orders orders = new Orders();

    protected Member(){}

    public Member(String name){
        this.name = name;
    }

    public void addOrder(Order order){
        order.setMember(this);
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Orders getOrders() {
        return orders;
    }
}
