package com.awnish.repository;

import com.awnish.modal.Inventory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface InventoryRepository extends JpaRepository<Inventory,Integer> {


    Optional<Inventory> findByProductId(Integer id);


}
