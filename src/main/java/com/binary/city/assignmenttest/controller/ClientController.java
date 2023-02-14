package com.binary.city.assignmenttest.controller;

import com.binary.city.assignmenttest.model.Client;
import com.binary.city.assignmenttest.service.ClientService;
import com.binary.city.assignmenttest.utils.Response;
import com.binary.city.assignmenttest.utils.ResponseBuild;
import com.binary.city.assignmenttest.web.ClientPagedResponse;
import com.binary.city.assignmenttest.web.ClientRequest;
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
@RequestMapping(value = "/client")
@CrossOrigin(origins = "*")
@Tag(name = "client", description = "controller for clients")
public class ClientController {


    private  final ClientService clientService;

    private final ResponseBuild<Client> clientResponseBuild;



    public ClientController(ClientService clientService,
                            ResponseBuild<Client> clientResponseBuild
                            )
    {
        this.clientService = clientService;
        this.clientResponseBuild = clientResponseBuild;
    }

    @PostMapping
    @Operation(
            description = "request to create new client.",
            responses = {
                    @ApiResponse(responseCode = "400",ref = "BadRequestAPI"),
                    @ApiResponse(responseCode = "500",ref = "InternalServerErrorAPI"),
                    @ApiResponse(responseCode = "200", description = "successfully create account",
                            content = @Content(mediaType = "application/json", examples = @ExampleObject(value = ""))),
            })
    public ResponseEntity<Response> createClient(@RequestBody ClientRequest clientRequest){
        return new ResponseEntity<>(clientResponseBuild.successResponse
                .apply(clientService.createClient(clientRequest),null), HttpStatus.CREATED);
    }

    @GetMapping("/all")
    @Operation(
            description = "request to fetch all clients order by name.",
            responses = {
                    @ApiResponse(responseCode = "400",ref = "BadRequestAPI"),
                    @ApiResponse(responseCode = "500",ref = "InternalServerErrorAPI"),
                    @ApiResponse(responseCode = "200", description = "clients fetched successfully.",
                            content = @Content(mediaType = "application/json", examples = @ExampleObject(value = ""))),
            })
    public ResponseEntity<?> findAllClients(
            @RequestParam(value = "sort", required = false) String sort,
            @RequestParam(value = "page", required = false) Integer page,
            @RequestParam(value = "size", required = false) Integer size,
            PagedResourcesAssembler<Client> assembler
    ){
        log.info("request to fetch all clients order by name");

        Page<Client> list = this.clientService.findAllClient(sort,page,size) ;

        String link = ServletUriComponentsBuilder.fromCurrentRequest()
                .build()
                .toUriString();

        PagedModel<EntityModel<Client>> resource = assembler.toModel(list, Link.of(link));

        ClientPagedResponse clientPagedResponse=new ClientPagedResponse();
        clientPagedResponse.setPage(list);

        if (resource.getLink("first").isPresent()) {
            clientPagedResponse.get_links().put("first", resource.getLink("first").get().getHref());
        }

        if (resource.getLink("prev").isPresent()) {
            clientPagedResponse.get_links().put("prev", resource.getLink("prev").get().getHref());
        }

        if (resource.getLink("self").isPresent()) {
            clientPagedResponse.get_links().put("self", resource.getLink("self").get().getHref());
        }

        if (resource.getLink("next").isPresent()) {
            clientPagedResponse.get_links().put("next", resource.getLink("next").get().getHref());
        }

        if (resource.getLink("last").isPresent()) {
            clientPagedResponse.get_links().put("last", resource.getLink("last").get().getHref());
        }
        return ResponseEntity.ok(clientPagedResponse);

    }

}
