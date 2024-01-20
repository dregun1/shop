package com.shop.entity;

import com.shop.constant.OrderStatus;
import lombok.Getter;
import lombok.Setter;
import org.apache.tomcat.jni.Local;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="orders")
@Getter@Setter
public class Order extends BaseEntity{

    @Id@GeneratedValue
    @Column(name="order_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="member_id")
    private Member member;

    private LocalDateTime orderDate; //주문일

    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus; //주문상태

    //mappedBy = "order" >> OrderItem테이블의 order에 의해 관리된다는 의미. 현재 테이블 이름 Order 가 아님X.
    //orphanRemoval = true >> 고아객체제거 기능
    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<OrderItem> orderItems = new ArrayList<>();

    //private LocalDateTime regTime;

    //private LocalDateTime updateTime;

    public void addOrderItem(OrderItem orderItem){
        orderItems.add(orderItem);
        orderItem.setOrder(this);
    }

    //아이템담으면 생성하는 주문서인듯?
    public static Order createOrder(Member member, List<OrderItem> orderItemList){
        Order order = new Order();
        order.setMember(member);
        for(OrderItem orderItem : orderItemList){      //근데 orderItemList에  orderItem객체 하나만 담겨있는 거아냐? 나중에 여러 상품체크가구현되나?
            order.addOrderItem(orderItem);
        }
        order.setOrderStatus(OrderStatus.ORDER);
        order.setOrderDate(LocalDateTime.now());
        return order;
    }

    public int getTotalPrice(){
        int totalPrice = 0;
        for(OrderItem orderItem : orderItems){
            totalPrice += orderItem.getTotalPrice();
        }
        return totalPrice;
    }


    public void cancelOrder(){
        this.orderStatus = OrderStatus.CANCEL;

        for(OrderItem orderItem : orderItems){
            orderItem.cancel();
        }
    }


}
