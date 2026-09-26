package vn.rikkei.exam.restaurantreservation.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import vn.rikkei.exam.restaurantreservation.model.ReservationRequest;
import vn.rikkei.exam.restaurantreservation.model.ReservationStatus;

import java.util.List;

public interface ReservationRequestRepository extends JpaRepository<ReservationRequest, String> {
    List<ReservationRequest> findAllByStatus(ReservationStatus status);
    long countByStatus(ReservationStatus status);
}
