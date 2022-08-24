package com.hackaton.kurly.domain.scan;

import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ScanLogService {
    private final ScanLogRepository scanLogRepository;

    public void saveScanLogs(ScanLog scanLog){
        scanLogRepository.save(scanLog);
    }

    public List<ScanLog> findByOrderId (Long orderId ){
        return scanLogRepository.findByOrderIdOrderByTryCountAsc(orderId);
    }

    public Page<ScanLog> getScanLogs(Pageable pageable){
        return scanLogRepository.findAll(pageable);
    }
}
