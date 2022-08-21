package com.hackaton.kurly.domain.Item.repository;

import com.hackaton.kurly.domain.Item.DetailItem;
import com.hackaton.kurly.domain.Item.ItemCart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface DetailItemRepository extends JpaRepository<DetailItem, Integer> {

}