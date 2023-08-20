package com.robsil.merchantservice.controller;

import com.robsil.erommerce.jwtintegration.model.UserAuthenticationToken;
import com.robsil.merchantservice.model.merchant.request.MerchantRequestCreateWithoutTokenDto;
import com.robsil.merchantservice.model.merchant.request.MerchantRequestDecideDto;
import com.robsil.merchantservice.service.MerchantRequestFacadeService;
import com.robsil.merchantservice.service.MerchantRequestService;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/merchantRequests")
@RequiredArgsConstructor
public class MerchantRequestController {

    private final MerchantRequestFacadeService merchantRequestFacadeService;
    private final MerchantRequestService merchantRequestService;

    @PostMapping
    public ResponseEntity<Void> create(@RequestBody MerchantRequestCreateWithoutTokenDto dto, UserAuthenticationToken user) {
        merchantRequestFacadeService.create(dto, user);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping("/confirm")
    public ResponseEntity<Void> confirm(@RequestParam String token) {
        merchantRequestFacadeService.confirm(token);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping("/{id}/decide")
    public ResponseEntity<Void> decideRequest(@RequestBody MerchantRequestDecideDto dto, @PathVariable @Positive Long id, UserAuthenticationToken user) {
        merchantRequestFacadeService.decide(id, dto);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRequest(@PathVariable Long id) {
        merchantRequestService.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
