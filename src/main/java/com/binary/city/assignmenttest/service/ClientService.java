package com.binary.city.assignmenttest.service;

import com.binary.city.assignmenttest.model.Client;
import com.binary.city.assignmenttest.web.ClientRequest;
import org.springframework.data.domain.Page;

import java.util.List;

public interface ClientService {
    Client createClient(ClientRequest clientRequest);
    Page<Client> findAllClient(String sort, Integer page, Integer size);



}
