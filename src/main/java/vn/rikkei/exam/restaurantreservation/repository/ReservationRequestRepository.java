package vn.rikkei.exam.restaurantreservation.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import vn.rikkei.exam.restaurantreservation.model.ReservationRequest;
public interface ReservationRequestRepository extends JpaRepository<ReservationRequest, String> { }
