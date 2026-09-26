package vn.rikkei.exam.restaurantreservation.tool;

import lombok.RequiredArgsConstructor;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.ai.tool.annotation.ToolParam;
import org.springframework.stereotype.Service;
import vn.rikkei.exam.restaurantreservation.model.ReservationRequest;
import vn.rikkei.exam.restaurantreservation.model.ReservationStatus;
import vn.rikkei.exam.restaurantreservation.model.ResourceInventory;
import vn.rikkei.exam.restaurantreservation.service.RestaurantReservationService;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AgentTools {

    private final RestaurantReservationService service;

    @Tool(name = "create_reservation",
          description = "Tao yeu cau dat ban nha hang (trang thai PENDING). Kiem tra nguoi dung, "
                      + "loai ban con hoat dong, so khach khong vuot suc chua, con ban trong theo ngay, "
                      + "muc dich 10-200 ky tu. Tra ve ma yeu cau va trang thai.")
    public String createReservation(
            @ToolParam(description = "Ma nguoi dung, vi du USR-001") String userId,
            @ToolParam(description = "Ma loai ban, vi du STD hoac VIP") String resourceCode,
            @ToolParam(description = "Ngay dat, dinh dang yyyy-MM-dd") String date,
            @ToolParam(description = "So khach") int participantCount,
            @ToolParam(description = "Muc dich, 10-200 ky tu") String purpose) {
        try {
            ReservationRequest r = service.create(userId, resourceCode,
                    LocalDate.parse(date), participantCount, purpose);
            return "Da tao yeu cau " + r.getRequestId() + " - trang thai " + r.getStatus();
        } catch (Exception e) {
            return "Lỗi: " + e.getMessage();
        }
    }

    @Tool(name = "check_table_availability",
          description = "Kiem tra con ban trong theo ngay yyyy-MM-dd. Tra ve danh sach loai ban con cho.")
    public String checkAvailability(@ToolParam(description = "Ngay yyyy-MM-dd") String date) {
        try {
            List<ResourceInventory> inv = service.availableOn(LocalDate.parse(date));
            if (inv.isEmpty()) return "Ngay " + date + " khong con ban trong.";
            StringBuilder sb = new StringBuilder("Ban con trong ngay " + date + ":\n");
            for (ResourceInventory i : inv) {
                sb.append("- ").append(i.getResourceType().getResourceCode())
                  .append(" (").append(i.getResourceType().getDisplayName()).append("): ")
                  .append(i.getAvailableSlots()).append(" ban\n");
            }
            return sb.toString();
        } catch (Exception e) {
            return "Lỗi: " + e.getMessage();
        }
    }

    @Tool(name = "get_reservation", description = "Lay chi tiet 1 yeu cau dat ban theo ma requestId.")
    public String getReservation(@ToolParam(description = "Ma yeu cau") String id) {
        try {
            ReservationRequest r = service.getById(id);
            return "Yeu cau " + r.getRequestId() + ": khach " + r.getRequester().getFullName()
                    + ", ban " + r.getResourceType().getResourceCode()
                    + ", ngay " + r.getStartDate() + ", " + r.getParticipantCount() + " nguoi, trang thai "
                    + r.getStatus();
        } catch (Exception e) {
            return "Lỗi: " + e.getMessage();
        }
    }

    @Tool(name = "list_reservations_by_status",
          description = "Liet ke yeu cau dat ban theo trang thai: PENDING, APPROVED, REJECTED, CANCELLED, DRAFT.")
    public String byStatus(@ToolParam(description = "Trang thai") String status) {
        try {
            List<ReservationRequest> list = service.byStatus(ReservationStatus.valueOf(status.toUpperCase()));
            if (list.isEmpty()) return "Khong co yeu cau nao o trang thai " + status;
            StringBuilder sb = new StringBuilder();
            for (ReservationRequest r : list) {
                sb.append("- ").append(r.getRequestId()).append(" | ")
                  .append(r.getResourceType().getResourceCode()).append(" | ")
                  .append(r.getStartDate()).append(" | ").append(r.getParticipantCount()).append(" nguoi\n");
            }
            return sb.toString();
        } catch (IllegalArgumentException e) {
            return "Lỗi: trang thai khong hop le (PENDING/APPROVED/REJECTED/CANCELLED/DRAFT)";
        } catch (Exception e) {
            return "Lỗi: " + e.getMessage();
        }
    }

    @Tool(name = "count_reservations_by_status", description = "Dem so yeu cau theo trang thai.")
    public String countByStatus(@ToolParam(description = "Trang thai") String status) {
        try {
            long n = service.countByStatus(ReservationStatus.valueOf(status.toUpperCase()));
            return "Co " + n + " yeu cau o trang thai " + status.toUpperCase();
        } catch (Exception e) {
            return "Lỗi: " + e.getMessage();
        }
    }
}
