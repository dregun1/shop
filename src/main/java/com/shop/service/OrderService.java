package com.shop.service;


import com.shop.dto.OrderDto;
import com.shop.dto.OrderHistDto;
import com.shop.dto.OrderItemDto;
import com.shop.entity.*;
import com.shop.repository.ItemImgRepository;
import com.shop.repository.ItemRepository;
import com.shop.repository.MemberRepository;
import com.shop.repository.OrderRepositoy;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.thymeleaf.util.StringUtils;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class OrderService {

    private final ItemRepository itemRepository;
    private final MemberRepository memberRepository;
    private final OrderRepositoy orderRepositoy;
    private final ItemImgRepository itemImgRepository;

    public Long order(OrderDto orderDto, String email){
        Item item = itemRepository.findById(orderDto.getItemId())
                .orElseThrow(EntityNotFoundException::new);
        Member member = memberRepository.findByEmail(email);

        List<OrderItem> orderItemList = new ArrayList<>();
        OrderItem orderItem = OrderItem.createOrderItem(item, orderDto.getCount());
        orderItemList.add(orderItem);

        Order order = Order.createOrder(member, orderItemList);
        orderRepositoy.save(order);

        return order.getId();
    }

    @Transactional(readOnly = true)
    public Page<OrderHistDto> getOrderList(String email, Pageable pageable){

        List<Order> orders = orderRepositoy.findOrders(email, pageable);
        Long totalCount = orderRepositoy.countOrder(email);

        List<OrderHistDto> orderHistDtos = new ArrayList<>();

        for(Order order : orders) {
            OrderHistDto orderHistDto = new OrderHistDto(order);
            List<OrderItem> orderItems = order.getOrderItems();  //아니 이메일로 검색을하기 때문에 orders은 여러개 있을 수 있는 거 알겠는데 order에는 어차피 상품 한 개만담겨있는 거 아냐?
            for(OrderItem orderItem : orderItems){
                ItemImg itemImg = itemImgRepository.findByItemIdAndRepimgYn(orderItem.getItem().getId(), "Y");
                OrderItemDto orderItemDto = new OrderItemDto(orderItem, itemImg.getImgUrl());
                orderHistDto.addOrderItemDto(orderItemDto);
            }
            orderHistDtos.add(orderHistDto);
        }
        return new PageImpl<OrderHistDto>(orderHistDtos, pageable, totalCount);
    }

    @Transactional(readOnly = true)
    public boolean validateOrder(Long orderId, String email){
        Member curMember = memberRepository.findByEmail(email);
        Order order = orderRepositoy.findById(orderId).orElseThrow(EntityNotFoundException::new);
        Member savedMember = order.getMember();

        if(!StringUtils.equals(curMember.getEmail(),savedMember.getEmail())){
            return false;
        }
        return true;
    }

    public void cancelOrder(Long orderId){
        Order order = orderRepositoy.findById(orderId).orElseThrow(EntityNotFoundException::new);
        order.cancelOrder();
    }

    public Long orders(List<OrderDto> orderDtoList, String email){
        Member member = memberRepository.findByEmail(email);
        List<OrderItem> orderItemList = new ArrayList<>();

        for(OrderDto orderDto : orderDtoList){
            Item item = itemRepository.findById(orderDto.getItemId()).orElseThrow(EntityNotFoundException::new);

            OrderItem orderItem = OrderItem.createOrderItem(item, orderDto.getCount());
            orderItemList.add(orderItem);
        }

        Order order = Order.createOrder(member, orderItemList);
        orderRepositoy.save(order);
        return order.getId();
    }
}
