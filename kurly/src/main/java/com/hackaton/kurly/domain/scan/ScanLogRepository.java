package com.hackaton.kurly.domain.scan;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ScanLogRepository extends JpaRepository<ScanLog, Long> {
    List<ScanLog> findByOrderIdOrderByTryCountAsc(Long id);
}
