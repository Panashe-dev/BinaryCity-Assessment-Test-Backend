package com.binary.city.assignmenttest.service;

import com.binary.city.assignmenttest.model.Contact;
import com.binary.city.assignmenttest.web.ContactRequest;
import com.binary.city.assignmenttest.web.ContactToClientRequest;
import org.springframework.data.domain.Page;

import java.util.List;

public interface ContactService {
    Contact createContact(ContactRequest contactRequest);

    Contact linkContactToClient(ContactToClientRequest contactToClientRequest);
    Page<Contact> findAllContacts(String sort, Integer page, Integer size);
}
