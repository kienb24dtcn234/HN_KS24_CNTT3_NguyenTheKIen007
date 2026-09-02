package vn.rikkei.exam.restaurantreservation.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import vn.rikkei.exam.restaurantreservation.model.ResourceInventory;
public interface ResourceInventoryRepository extends JpaRepository<ResourceInventory, Long> { }
