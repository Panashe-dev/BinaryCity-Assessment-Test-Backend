package com.binary.city.assignmenttest.repository;

import com.binary.city.assignmenttest.model.Contact;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Transactional
public interface ContactRepository  extends JpaRepository<Contact,Integer> {
    @Override
    @Query("SELECT c FROM Contact c ORDER BY c.name")
    Page<Contact> findAll(Pageable pageable);
}
