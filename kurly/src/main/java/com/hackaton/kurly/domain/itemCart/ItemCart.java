package com.hackaton.kurly.domain.itemCart;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "item_cart")
public class ItemCart {

    @Id
    private Long orderId;

    @Column(name = "order_list")
    private String orderList;

}
