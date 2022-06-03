package com.prgrms.test.domain;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Embeddable
public class Orders {

    @OneToMany(mappedBy = "member", orphanRemoval = true)
    private List<Order> orderList = new ArrayList<>();

    public Orders() {

    }

    public void add(Order order){
        orderList.add(order);
    }

    public void remove(Order order){
        orderList.remove(order);
    }

    public int size(){
        return orderList.size();
    }

}
