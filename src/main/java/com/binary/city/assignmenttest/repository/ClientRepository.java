package com.binary.city.assignmenttest.repository;

import com.binary.city.assignmenttest.model.Client;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@Transactional
public interface ClientRepository extends JpaRepository<Client,Integer> {

    @Query("SELECT MAX(clientId) FROM Client")
    Optional<Integer> findLastClientCode();

    @Override
    @Query("SELECT c FROM  Client c ORDER BY c.Name")
    Page<Client> findAll(Pageable pageable);

    @Query("SELECT MAX(numberOfLinked) FROM Client")
    Optional<Integer> findNoOfLink();
}
