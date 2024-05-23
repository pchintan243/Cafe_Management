package com.cafe.server.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cafe.server.entities.Bill;

public interface BillRepository extends JpaRepository<Bill, Integer> {

}
