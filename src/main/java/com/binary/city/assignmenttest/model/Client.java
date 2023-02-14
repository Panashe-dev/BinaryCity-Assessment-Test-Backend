package com.binary.city.assignmenttest.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonMerge;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "CLIENTS")
@Builder
public class Client {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", updatable = false, nullable = false)
    private int clientId;

    @Column(name = "NAME", nullable = false)
    private String Name;

    @Column(name = "CODE", nullable = false,unique = true)
    private  String code;

    @Column(name = "LINK", nullable = false)
    private int numberOfLinked;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CONTACT_CLIENT_ID")
    @JsonBackReference
    private Contact contact;

}
