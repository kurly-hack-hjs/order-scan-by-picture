package com.hackaton.kurly.domain.snapshot;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@IdClass(SnapshotPk.class)
public class Snapshot {
    @Id
    private Long orderId;

    @Id
    private Integer tryCount;

    private String snapshots;

    private String userId;

    private boolean isSatisfied;
}
