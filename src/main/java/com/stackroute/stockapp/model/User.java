package com.stackroute.stockapp.model;

/*
 * create the following fields 
 * emailId,password,userName,mobile
 * use Lombok to generate no-args constructor,All Argument constructor, getters, setters, and toString() method.
 * Use @Document annotation to specify the collection name in mongoDB.
 */

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Document
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {
    @Id
    private String emailId;
    private String password;
    private String userName;
    private String mobile;
}   