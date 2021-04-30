package com.dev.careers.domain;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class HttpStatusConstants {
    public final static ResponseEntity<Void> BAD_REQUEST = new ResponseEntity<Void>(
        HttpStatus.BAD_REQUEST);
    public final static ResponseEntity<Void> CREATE = new ResponseEntity<Void>(
        HttpStatus.CREATED);
}
