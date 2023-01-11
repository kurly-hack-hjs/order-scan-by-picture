package com.hackaton.kurly.domain.snapshot;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Service
@RestController
@RequiredArgsConstructor
public class SnapshotService {
    private final SnapshotRepository snapshotRepository;

    public List<Snapshot> findBySnapshot(Long orderId){
        return snapshotRepository.findByOrderId(orderId);
    }

    public void save(Snapshot snapshot){
        snapshotRepository.save(snapshot);
    }
}
