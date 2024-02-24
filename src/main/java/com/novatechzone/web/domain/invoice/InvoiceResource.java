package com.novatechzone.web.domain.invoice;

import com.novatechzone.web.dto.ApplicationResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/invoice")
public class InvoiceResource {
    private final InvoiceService invoiceService;

    @PostMapping("/create/{plan-id}")
    public ResponseEntity<ApplicationResponseDTO> createInvoice(@PathVariable("plan-id") Long planId) {
        return ResponseEntity.ok(invoiceService.createInvoice(planId));
    }

    @GetMapping("/get")
    public ResponseEntity<List<Invoice>> getAllInvoices() {
        return ResponseEntity.ok(invoiceService.getAllInvoices());
    }

    @GetMapping("/get-logged-user")
    public ResponseEntity<List<Invoice>> getAllLoggedUserInvoices() {
        return ResponseEntity.ok(invoiceService.getAllLoggedUserInvoices());
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<Invoice> getInvoiceById(@PathVariable("id") Long id) {
        return ResponseEntity.ok(invoiceService.getInvoiceById(id));
    }

    @PutMapping("/update/{id}/{plan-id}")
    public ResponseEntity<ApplicationResponseDTO> updateInvoice(@PathVariable("id") Long id, @PathVariable("plan-id") Long planId) {
        return ResponseEntity.ok(invoiceService.updateInvoice(id, planId));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ApplicationResponseDTO> deleteInvoice(@PathVariable("id") Long id) {
        return ResponseEntity.ok(invoiceService.deleteInvoice(id));
    }
}