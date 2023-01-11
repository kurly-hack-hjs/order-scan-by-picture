package com.hackaton.kurly.domain.itemCart.repository;

import com.hackaton.kurly.domain.itemCart.CartSnapshot;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartSnapshotRepository extends JpaRepository<CartSnapshot, Long> {

}
