package vn.rikkei.exam.restaurantreservation.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import vn.rikkei.exam.restaurantreservation.model.ReservationRequest;
import vn.rikkei.exam.restaurantreservation.service.RestaurantReservationService;

import java.util.Map;

@RestController
@RequestMapping("/api/v1/reservations")
@RequiredArgsConstructor
public class ReservationController {

    private final RestaurantReservationService service;

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}/approve")
    public ReservationRequest approve(@PathVariable String id) {
        return service.approve(id);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}/reject")
    public ReservationRequest reject(@PathVariable String id, @RequestBody(required = false) Map<String, String> body) {
        String note = body != null ? body.getOrDefault("note", "") : "";
        return service.reject(id, note);
    }

    @GetMapping("/{id}")
    public ReservationRequest get(@PathVariable String id) {
        return service.getById(id);
    }
}
