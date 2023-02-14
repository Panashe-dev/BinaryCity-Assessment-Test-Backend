package com.binary.city.assignmenttest.service.impl;

import com.binary.city.assignmenttest.exception.InvalidFormatException;
import com.binary.city.assignmenttest.model.Client;
import com.binary.city.assignmenttest.repository.ClientRepository;
import com.binary.city.assignmenttest.service.ClientService;
import com.binary.city.assignmenttest.web.ClientRequest;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Random;

@Service
@Slf4j
@Transactional
public class ClientServiceImpl  implements ClientService {


    private final ClientRepository clientRepository;

    public ClientServiceImpl(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    @Override
    public Client createClient(ClientRequest clientRequest) {
         System.out.println(clientRequest.getName());
        String clientCode = "";
        String codeNumber = generateUniqueCode();
        Random r = new Random();
        char codeLetters = (char) (r.nextInt(26) + 'A');
        StringBuilder code = new StringBuilder();
        String str=clientRequest.getName().trim();
        char letter = 0;
        for (int i = 0; i < str.length(); i++) {
            if(Character.isUpperCase(str.charAt(i))){
                letter = str.charAt(i);
                code.append(letter);
            }}
        System.out.println(code);
         if (str.length()<3){
             clientCode=str.toUpperCase().concat(String.valueOf(codeLetters)).concat(String.valueOf(codeNumber)).toUpperCase();
         }else if (code.length()>=3){
            clientCode=code.toString().concat(String.valueOf(codeNumber)).toUpperCase().toUpperCase();
         }else if ((code.length()<=2) && (str.length()>4)){
             clientCode=str.substring(0,3).concat(String.valueOf(codeNumber)).toUpperCase();
         }else {
            throw  new InvalidFormatException("Invalid Format Exception");
         }
         System.out.println(clientCode);

        Client client = Client.builder()
                .Name(clientRequest.getName())
                .code(clientCode)
                .build();
        return this.clientRepository.save(client);
    }

    @Override
    public Page<Client> findAllClient(String sort, Integer page, Integer size) {
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
        return this.clientRepository.findAll(pageable);
    }

    public String generateUniqueCode(){
        String code = "";
        Optional<Integer> lastClientCode = this.clientRepository.findLastClientCode();
        int i = lastClientCode.map(integer -> integer + 1).orElse(1);
        if (i<=9){
           return code="00".concat(String.valueOf(i));
        }else  if (i<=99){
           return   code="0".concat(String.valueOf(i));
        }else {
            return code;
        }
    }
}
