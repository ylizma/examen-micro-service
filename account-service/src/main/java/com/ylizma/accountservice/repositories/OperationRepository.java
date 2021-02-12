package com.ylizma.accountservice.repositories;

import com.ylizma.accountservice.models.Operation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OperationRepository extends JpaRepository<Operation, Long> {
    List<Operation> findOperationsByAccountId(Long accountNumber);
}
