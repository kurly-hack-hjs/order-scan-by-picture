package com.hackaton.kurly.domain.snapshot;

import lombok.AllArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;

@Entity
@IdClass(SnapshotPk.class)
public class Snapshot {
    @Id
    private Long OrderId;

    @Id
    private Integer tryCount;

    private String snapshots;
}
