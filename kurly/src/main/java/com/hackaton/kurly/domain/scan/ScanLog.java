package com.hackaton.kurly.domain.scan;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.ZoneId;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@Table(name = "scan_log")
public class ScanLog {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    private String loginId;
    private Long orderId;

    private LocalDateTime timestamp;

    private String scanResult;

    private String scanUrl;


    public ScanLog(String loginId, Long orderId, String scanResult, String scanUrl) {
        this.loginId = loginId;
        this.orderId = orderId;
        this.timestamp = LocalDateTime.now(ZoneId.of("Asia/Tokyo"));
        this.scanResult = scanResult;
        this.scanUrl = scanUrl;
    }
}
