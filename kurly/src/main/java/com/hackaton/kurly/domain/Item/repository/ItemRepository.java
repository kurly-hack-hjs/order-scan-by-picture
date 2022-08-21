package com.hackaton.kurly.domain.Item.repository;

import com.hackaton.kurly.domain.Item.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.nio.file.LinkOption;
import java.util.UUID;

@Repository
public interface ItemRepository extends JpaRepository<Item, Long> {

}
