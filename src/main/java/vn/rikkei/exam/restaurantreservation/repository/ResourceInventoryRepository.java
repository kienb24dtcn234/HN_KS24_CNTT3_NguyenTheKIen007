package vn.rikkei.exam.restaurantreservation.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import vn.rikkei.exam.restaurantreservation.model.ResourceInventory;

import java.time.LocalDate;
import java.util.List;

public interface ResourceInventoryRepository extends JpaRepository<ResourceInventory, Long> {
    List<ResourceInventory> findByResourceType_ResourceCodeAndAvailableDate(String resourceCode, LocalDate availableDate);
    List<ResourceInventory> findByAvailableDateAndAvailableSlotsGreaterThan(LocalDate availableDate, int min);
}
