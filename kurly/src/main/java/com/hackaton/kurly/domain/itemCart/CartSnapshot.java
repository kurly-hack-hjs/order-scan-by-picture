package com.hackaton.kurly.domain.itemCart;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Setter
@Table(name = "item_cart")
public class CartSnapshot {
    @Id
    private Long orderId;

    @Column(name = "order_list")
    private String orderList;

    private String snapshot;

    private String scanStock;

    @Column(name = "try_count")
    private Integer tryCount;

    public void updateByScanResult(String snapShot, String scanStock, int tryCount) {
        this.snapshot = snapShot;
        this.scanStock = scanStock;
        this.tryCount = tryCount;
    }
}
