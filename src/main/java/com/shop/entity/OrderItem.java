package com.shop.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter@Setter
public class OrderItem extends BaseEntity{

    @Id @GeneratedValue
    @Column(name="order_item_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY) //지연로딩
    @JoinColumn(name = "item_id")
    private Item item;

    @ManyToOne(fetch = FetchType.LAZY) //지연로딩
    @JoinColumn(name="order_id")  //외래키, 연관관계의 주인.
    private Order order;

    private int orderPrice; //주문가격

    private int count; //수량

/*    private LocalDateTime regTime;

    private LocalDateTime updateTime;*/ //BaseEntity 상속 이후 삭제


    public static OrderItem createOrderItem(Item item, int count){
        OrderItem orderItem = new OrderItem();
        orderItem.setItem(item);
        orderItem.setCount(count);
        orderItem.setOrderPrice(item.getPrice());

        item.removeStock(count);
        return orderItem;
    }

    public int getTotalPrice(){
        return orderPrice*count;
    }

    public void cancel(){
        this.getItem().addStock(count);
    }
}
