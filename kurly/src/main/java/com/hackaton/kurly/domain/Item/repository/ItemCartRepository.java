package com.hackaton.kurly.domain.Item.repository;


import com.hackaton.kurly.domain.Item.ItemCart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ItemCartRepository extends JpaRepository<ItemCart, Long> {
}
