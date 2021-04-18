package com.dev.careers.domain;

import org.springframework.http.ResponseEntity;

public class HttpStatusConstants {
    public final static ResponseEntity<Void> BAD_REQUEST = new ResponseEntity<Void>(
        org.springframework.http.HttpStatus.BAD_REQUEST);
    public final static ResponseEntity<Void> CREATE = new ResponseEntity<Void>(
        org.springframework.http.HttpStatus.CREATED);
}
