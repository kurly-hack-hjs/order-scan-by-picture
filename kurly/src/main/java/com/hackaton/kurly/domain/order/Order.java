package com.hackaton.kurly.domain.order;

import com.hackaton.kurly.domain.order.dto.PatchOrderRequest;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "orders")

public class Order {

    @javax.persistence.Id
    @Column(name = "order_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private ScanStatus scanStatus;

    private LocalDate orderDate;

    private String updatedUserId;

    private String memo;

    private int quantity;

    private LocalDateTime updatedTimestamp;

    public Order(  int quantity) {
        this.scanStatus = ScanStatus.STANDBY;
        this.orderDate = LocalDate.now(ZoneId.of("Asia/Tokyo"));
        this.quantity = quantity;
        this.tryCount = 0;
    }

    public void setTryCount(int tryCount) {
        this.tryCount = tryCount;
        this.updatedTimestamp =  LocalDateTime.now(ZoneId.of("Asia/Tokyo"));
    }

    private int tryCount;

    public void updateStatus(PatchOrderRequest request){
        if(request.getNewOrderScanStatus().equals(ScanStatus.RESET)){
            this.tryCount = 0;
        }
        this.scanStatus = request.getNewOrderScanStatus();
        this.updatedUserId = request.getLoginId();
        this.updatedTimestamp =  LocalDateTime.now(ZoneId.of("Asia/Tokyo"));
        this.memo = request.getMemo();
    }

}
