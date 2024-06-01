package com.willian.financial_organizer.controllers;


import com.willian.financial_organizer.services.ReleasesServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;

@RestController
public class UserController {

    @Autowired
    private ReleasesServices releasesServices;

    @GetMapping(value = "/{id}/balance", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<BigDecimal> getBalance(@PathVariable("id") Long id) {
        BigDecimal balance = releasesServices.getBalanceByUser(id);
        return ResponseEntity.ok().body(balance);
    }
}
