package com.binary.city.assignmenttest.service.impl;

import com.binary.city.assignmenttest.model.Client;
import com.binary.city.assignmenttest.model.Contact;
import com.binary.city.assignmenttest.repository.ClientRepository;
import com.binary.city.assignmenttest.repository.ContactRepository;
import com.binary.city.assignmenttest.service.ContactService;
import com.binary.city.assignmenttest.utils.EmailValidator;
import com.binary.city.assignmenttest.web.ContactToClientRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import com.binary.city.assignmenttest.web.ContactRequest;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@Transactional
public class ContactServiceImpl  implements ContactService {

    private final ContactRepository contactRepository;
    private final ClientRepository clientRepository;

    private  final EmailValidator emailValidator;
    public ContactServiceImpl(ContactRepository contactRepository,
                              ClientRepository clientRepository,
                              EmailValidator emailValidator) {

        this.contactRepository = contactRepository;
        this.clientRepository = clientRepository;
        this.emailValidator = emailValidator;
    }

    @Override
    public Contact createContact(ContactRequest contactRequest) {
        System.out.println(contactRequest);

        Contact contact = Contact.builder()
                .name(contactRequest.getName())
                .surname(contactRequest.getSurname())
                .email(contactRequest.getEmail())
                .build();
        return  this.contactRepository.save(contact);
    }

    @Override
    public Contact linkContactToClient(ContactToClientRequest contactToClientRequest) {
        if (contactToClientRequest.getContact()==0 && contactToClientRequest.getClient()==0){
            throw  new RuntimeException("");
        }
        Client client = clientRepository.findById(contactToClientRequest.getClient()).get();
        Contact contact = contactRepository.findById(contactToClientRequest.getContact()).get();
        List<Client> clientList=new ArrayList<>();
        clientList.add(client);
        contact.setClient(clientList);
        client.setContact(contact);
        client.setNumberOfLinked(numberOfLinked());
        this.clientRepository.save(client);
        return null;
    }

    @Override
    public Page<Contact> findAllContacts(String sort, Integer page, Integer size) {
        if (size == null || size == 0) {
            size = 20;
        }

        if (page == null || page == 0) {
            page = 0;
        }

        Pageable pageable;

        if (sort == null) {
            pageable = PageRequest.of(page, size);
        } else {
            Sort.Order order;

            try {
                String[] split = sort.split(",");

                Sort.Direction sortDirection = Sort.Direction.fromString(split[1]);
                order = new Sort.Order(sortDirection, split[0]).ignoreCase();
                pageable = PageRequest.of(page, size, Sort.by(order));

            } catch (Exception e) {
                throw new RuntimeException("Not a valid sort value, It should be 'fieldName,direction");
            }

        }

        Page<Contact> all = this.contactRepository.findAll(pageable);;
        return all;
    }


    public int numberOfLinked(){
        Optional<Integer> lastClientCode = this.clientRepository.findLastClientCode();
        return lastClientCode.map(integer -> integer + 1).orElse(1);
    }


}
