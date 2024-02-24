package com.novatechzone.web.domain.invoice;

import com.novatechzone.web.domain.plan.PlanRepository;
import com.novatechzone.web.domain.security.entity.User;
import com.novatechzone.web.domain.security.repos.UserRepository;
import com.novatechzone.web.domain.security.service.AuthService;
import com.novatechzone.web.dto.ApplicationResponseDTO;
import com.novatechzone.web.exception.ApplicationCustomException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class InvoiceService {
    private final InvoiceRepository invoiceRepository;
    private final UserRepository userRepository;
    private final PlanRepository planRepository;
    private static final String INVALID_INVOICE_ID = "INVALID_INVOICE_ID";
    private static final String INVALID_INVOICE_ID_MESSAGE = "Invalid Invoice Id";

    public ApplicationResponseDTO createInvoice(Long planId) {
        Optional<User> optionalUser = userRepository.findByUsername(AuthService.getCurrentUser());
        if (optionalUser.isEmpty()) {
            throw new ApplicationCustomException(HttpStatus.NOT_FOUND, "NO_USER_FOUND", "No User Found");
        }
        User user = optionalUser.get();
        invoiceRepository.save(new Invoice.InvoiceBuilder().userId(user.getId()).planId(planId).status(true).build());
        return new ApplicationResponseDTO(HttpStatus.CREATED, "INVOICE_CREATED", "Plan Created Successfully");
    }

    public List<Invoice> getAllInvoices() {
        List<Invoice> invoices = invoiceRepository.findAll();
        if (invoices.isEmpty()) {
            throw new ApplicationCustomException(HttpStatus.NOT_FOUND, "NO_INVOICES_FOUND", "No Invoices Found");
        }
        return invoices;
    }

    public List<Invoice> getAllLoggedUserInvoices() {
        Optional<User> optionalUser = userRepository.findByUsername(AuthService.getCurrentUser());
        if (optionalUser.isEmpty()) {
            throw new ApplicationCustomException(HttpStatus.NOT_FOUND, "NO_USER_FOUND", "No User Found");
        }
        User user = optionalUser.get();
        List<Invoice> invoices = invoiceRepository.findAllByUserId(user.getId());
        if (invoices.isEmpty()) {
            throw new ApplicationCustomException(HttpStatus.NOT_FOUND, "NO_INVOICES_FOUND", "No Invoices Found");
        }
        return invoices;
    }

    public Invoice getInvoiceById(Long id) {
        Optional<Invoice> optionalInvoice = invoiceRepository.findById(id);
        if (optionalInvoice.isEmpty()) {
            throw new ApplicationCustomException(HttpStatus.NOT_FOUND, INVALID_INVOICE_ID, INVALID_INVOICE_ID_MESSAGE);
        }
        return optionalInvoice.get();
    }

    public ApplicationResponseDTO updateInvoice(Long id, Long planId) {
        Optional<Invoice> optionalInvoice = invoiceRepository.findById(id);
        if (optionalInvoice.isEmpty()) {
            throw new ApplicationCustomException(HttpStatus.NOT_FOUND, INVALID_INVOICE_ID, INVALID_INVOICE_ID_MESSAGE);
        } else if (planRepository.findById(planId).isEmpty()) {
            throw new ApplicationCustomException(HttpStatus.BAD_REQUEST, "INVALID_PLAN_ID", "Invalid Plan Id");
        } else {
            Invoice invoice = optionalInvoice.get();
            invoice.setPlanId(planId);
            invoiceRepository.save(invoice);
            return new ApplicationResponseDTO(HttpStatus.OK, "INVOICE_UPDATED", "Invoice Updated Successfully");
        }
    }

    public ApplicationResponseDTO deleteInvoice(Long id) {
        Optional<Invoice> optionalInvoice = invoiceRepository.findById(id);
        if (optionalInvoice.isEmpty()) {
            throw new ApplicationCustomException(HttpStatus.NOT_FOUND, INVALID_INVOICE_ID, INVALID_INVOICE_ID_MESSAGE);
        }
        invoiceRepository.deleteById(id);
        return new ApplicationResponseDTO(HttpStatus.OK, "INVOICE_DELETED", "Invoice Deleted Successfully");
    }
}