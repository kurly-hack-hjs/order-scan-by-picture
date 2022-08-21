package com.hackaton.kurly.domain.scan.dto;

import lombok.Data;

@Data
public class ScanRequest {
private String imageUrl;
private Long orderId;
}
