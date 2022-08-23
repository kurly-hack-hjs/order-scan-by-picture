package com.hackaton.kurly.domain.Item.repository;

import com.hackaton.kurly.domain.Item.DetailItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DetailItemRepository extends JpaRepository<DetailItem, Integer> {

}