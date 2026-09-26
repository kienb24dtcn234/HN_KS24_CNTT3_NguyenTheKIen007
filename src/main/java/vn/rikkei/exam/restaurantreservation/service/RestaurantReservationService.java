package vn.rikkei.exam.restaurantreservation.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import vn.rikkei.exam.restaurantreservation.exception.BusinessException;
import vn.rikkei.exam.restaurantreservation.model.*;
import vn.rikkei.exam.restaurantreservation.repository.AppUserRepository;
import vn.rikkei.exam.restaurantreservation.repository.ReservationRequestRepository;
import vn.rikkei.exam.restaurantreservation.repository.ResourceInventoryRepository;
import vn.rikkei.exam.restaurantreservation.repository.ResourceTypeRepository;

import java.time.Instant;
import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RestaurantReservationService {

    private final ReservationRequestRepository requestRepo;
    private final ResourceInventoryRepository inventoryRepo;
    private final AppUserRepository appUserRepo;
    private final ResourceTypeRepository resourceTypeRepo;
    public ReservationRequest create(String userId, String resourceCode, LocalDate date,
                                     int participantCount, String purpose) {
        if (purpose == null || purpose.length() < 10 || purpose.length() > 200)
            throw new BusinessException("Muc dich phai tu 10 den 200 ky tu");

        AppUser user = appUserRepo.findById(userId)
                .orElseThrow(() -> new BusinessException("Khong tim thay nguoi dung " + userId));
        ResourceType type = resourceTypeRepo.findById(resourceCode)
                .orElseThrow(() -> new BusinessException("Khong tim thay loai ban " + resourceCode));

        if (Boolean.FALSE.equals(type.getActive()))
            throw new BusinessException("Loai ban " + resourceCode + " dang ngung phuc vu");
        if (participantCount < 1 || participantCount > type.getMaxParticipants())
            throw new BusinessException("So khach phai tu 1 den " + type.getMaxParticipants());

        List<ResourceInventory> inv = inventoryRepo
                .findByResourceType_ResourceCodeAndAvailableDate(resourceCode, date);
        if (inv.isEmpty() || inv.get(0).getAvailableSlots() <= 0)
            throw new BusinessException("Ngay " + date + " khong con ban trong cho loai " + resourceCode);

        ReservationRequest r = new ReservationRequest();
        r.setRequestId("RES-" + System.currentTimeMillis());
        r.setRequester(user);
        r.setResourceType(type);
        r.setStartDate(date);
        r.setEndDate(date);
        r.setParticipantCount(participantCount);
        r.setPurpose(purpose);
        r.setStatus(ReservationStatus.PENDING);
        r.setCreatedAt(Instant.now());
        r.setUpdatedAt(Instant.now());
        return requestRepo.save(r);
    }

    // Duyệt yêu cầu → APPROVED
    public ReservationRequest approve(String id) {
        ReservationRequest r = requestRepo.findById(id)
                .orElseThrow(() -> new BusinessException("Khong tim thay yeu cau " + id));
        r.setStatus(ReservationStatus.APPROVED);
        r.setUpdatedAt(Instant.now());
        return requestRepo.save(r);
    }

    // Từ chối
    public ReservationRequest reject(String id, String note) {
        ReservationRequest r = requestRepo.findById(id)
                .orElseThrow(() -> new BusinessException("Khong tim thay yeu cau " + id));
        r.setStatus(ReservationStatus.REJECTED);
        r.setDecisionNote(note);
        r.setUpdatedAt(Instant.now());
        return requestRepo.save(r);
    }

    public ReservationRequest getById(String id) {
        return requestRepo.findById(id)
                .orElseThrow(() -> new BusinessException("Khong tim thay yeu cau " + id));
    }

    public List<ReservationRequest> byStatus(ReservationStatus status) {
        return requestRepo.findAllByStatus(status);
    }

    public long countByStatus(ReservationStatus status) {
        return requestRepo.countByStatus(status);
    }

    public List<ResourceInventory> availableOn(LocalDate date) {
        return inventoryRepo.findByAvailableDateAndAvailableSlotsGreaterThan(date, 0);
    }
}
