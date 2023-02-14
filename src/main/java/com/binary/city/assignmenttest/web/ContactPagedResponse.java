package com.binary.city.assignmenttest.web;

import com.binary.city.assignmenttest.model.Contact;
import lombok.Data;
import org.springframework.data.domain.Page;

import java.util.HashMap;
import java.util.Map;

@Data
public class ContactPagedResponse {
    Page<Contact> page;
    Map<String, String> _links = new HashMap<>();
}
