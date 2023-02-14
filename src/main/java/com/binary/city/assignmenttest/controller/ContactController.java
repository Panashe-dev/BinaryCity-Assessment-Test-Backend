package com.binary.city.assignmenttest.controller;

import com.binary.city.assignmenttest.model.Contact;
import com.binary.city.assignmenttest.service.ContactService;
import com.binary.city.assignmenttest.utils.Response;
import com.binary.city.assignmenttest.utils.ResponseBuild;
import com.binary.city.assignmenttest.web.ContactPagedResponse;
import com.binary.city.assignmenttest.web.ContactRequest;
import com.binary.city.assignmenttest.web.ContactToClientRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@Slf4j
@RestController
@RequestMapping(value = "/contact")
@CrossOrigin(origins = "*")
@Tag(name = "contact", description = "controller for contact")
public class ContactController {

    private  final ContactService  contactService;

    private final ResponseBuild<Contact> contactResponseBuild;


    public ContactController(ContactService contactService,
                             ResponseBuild<Contact> contactResponseBuild
                            )
    {
        this.contactService = contactService;
        this.contactResponseBuild = contactResponseBuild;
    }

    @PostMapping
    @Operation(
            description = "request to create new contact.",
            responses = {
                    @ApiResponse(responseCode = "400",ref = "BadRequestAPI"),
                    @ApiResponse(responseCode = "500",ref = "InternalServerErrorAPI"),
                    @ApiResponse(responseCode = "200", description = "successfully create account",
                            content = @Content(mediaType = "application/json", examples = @ExampleObject(value = ""))),
            })
    public ResponseEntity<Response> createContact(@RequestBody ContactRequest contactRequest){
        return new ResponseEntity<>(contactResponseBuild.successResponse
                .apply(contactService.createContact(contactRequest),null), HttpStatus.CREATED);
    }

    @PostMapping("/link")
    @Operation(
            description = "request to link contact to client",
            responses = {
                    @ApiResponse(responseCode = "400",ref = "BadRequestAPI"),
                    @ApiResponse(responseCode = "500",ref = "InternalServerErrorAPI"),
                    @ApiResponse(responseCode = "200", description = "successfully create account",
                            content = @Content(mediaType = "application/json", examples = @ExampleObject(value = ""))),
            })
    public ResponseEntity<Response> linkContactToClient(@RequestBody ContactToClientRequest contactToClientRequest){
        return new ResponseEntity<>(contactResponseBuild.successResponse
                .apply(contactService.linkContactToClient(contactToClientRequest),null),HttpStatus.OK);
    }

    @GetMapping(value = "/all",produces = "application/json")
    @Operation(
            description = "request to fetch all contacts order by name.",
            responses = {
                    @ApiResponse(responseCode = "400",ref = "BadRequestAPI"),
                    @ApiResponse(responseCode = "500",ref = "InternalServerErrorAPI"),
                    @ApiResponse(responseCode = "200", description = "contacts fetched successfully.",
                            content = @Content(mediaType = "application/json", examples = @ExampleObject(value = ""))),
            })

    public ResponseEntity<ContactPagedResponse> findAllContacts(
            @RequestParam(value = "sort", required = false) String sort,
            @RequestParam(value = "page", required = false) Integer page,
            @RequestParam(value = "size", required = false) Integer size,
            PagedResourcesAssembler<Contact> assembler){

        Page<Contact> list = this.contactService.findAllContacts(sort,page,size);

        String link = ServletUriComponentsBuilder.fromCurrentRequest()
                .build()
                .toUriString();

        PagedModel<EntityModel<Contact>> resource = assembler.toModel(list, Link.of(link));

        ContactPagedResponse contactPagedResponse=new ContactPagedResponse();
        contactPagedResponse.setPage(list);

        if (resource.getLink("first").isPresent()) {
            contactPagedResponse.get_links().put("first", resource.getLink("first").get().getHref());
        }

        if (resource.getLink("prev").isPresent()) {
             contactPagedResponse.get_links().put("prev", resource.getLink("prev").get().getHref());
        }

        if (resource.getLink("self").isPresent()) {
            contactPagedResponse.get_links().put("self", resource.getLink("self").get().getHref());
        }

        if (resource.getLink("next").isPresent()) {
            contactPagedResponse.get_links().put("next", resource.getLink("next").get().getHref());
        }

        if (resource.getLink("last").isPresent()) {
            contactPagedResponse.get_links().put("last", resource.getLink("last").get().getHref());
        }
        return ResponseEntity.ok(contactPagedResponse);
    }
}
