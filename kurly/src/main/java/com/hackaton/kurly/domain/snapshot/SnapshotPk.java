package com.hackaton.kurly.domain.snapshot;

import lombok.Data;

import java.io.Serializable;

@Data
public class SnapshotPk implements Serializable {
    private Long orderId;
    private int tryCount;
}
