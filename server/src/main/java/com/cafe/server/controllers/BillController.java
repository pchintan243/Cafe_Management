package com.cafe.server.controllers;

import java.io.FileNotFoundException;

import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.cafe.server.entities.Bill;
import com.cafe.server.services.BillService;
import com.itextpdf.text.DocumentException;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;

@RestController
@SecurityRequirement(name = "BearerAuth")
public class BillController {

    @Autowired
    private BillService billService;

    @PostMapping("/bill/generateReport")
    public ResponseEntity<Bill> generateReport(@RequestBody Bill bill)
            throws FileNotFoundException, DocumentException, JSONException {
        Bill report = billService.generateReport(bill);
        return ResponseEntity.ok(report);
    }
}
