package com.hackaton.kurly.domain.snapshot;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SnapshotRepository extends JpaRepository<Snapshot, SnapshotPk> {
    List<Snapshot> findByOrderId(Long orderId);
}
