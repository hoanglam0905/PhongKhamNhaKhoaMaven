package view.dentistPanel;

import javax.swing.*;
import model.Appointment;

import java.awt.*;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class AppointmentTimeline extends JPanel {

    private List<Appointment> appointments;
    private int highlightStartX = -1;
    private int highlightWidth = 0;
    private Color highlightColor = null;
    private LocalTime highlightedStart = null;
    private LocalTime highlightedEnd = null;



    // Hàm getter để lấy slot đã highlight
    public LocalTime getHighlightedStart() {
        return highlightedStart;
    }

    public LocalTime getHighlightedEnd() {
        return highlightedEnd;
    }


    public AppointmentTimeline() {
        setPreferredSize(new Dimension(1000, 200));
        appointments = new ArrayList<>();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        int startHour = 8;
        int endHour = 17;
        int pixelsPer15Min = 20;
        int y = 40;

        // Vẽ các mốc giờ
        for (int hour = startHour; hour <= endHour; hour++) {
            int x = (hour - startHour) * 4 * pixelsPer15Min;
            g.setColor(Color.LIGHT_GRAY);
            g.drawLine(x, 0, x, getHeight());
            g.setColor(Color.BLACK);
            g.drawString(String.format("%02d:00", hour), x + 2, 20);
        }
        
     // Vẽ highlight nếu có
        if (highlightStartX >= 0 && highlightWidth > 0) {
        	g.setColor(highlightColor);
            g.fillRect(highlightStartX, y, highlightWidth, 40);
        }

        // Vẽ các khối lịch hẹn
        for (Appointment appt : appointments) {
            int hour = appt.getTime().toLocalTime().getHour();
            int minute = appt.getTime().toLocalTime().getMinute();
            int startMinutes = hour * 60 + minute;
            int offsetMin = startMinutes - startHour * 60;

            int x = (offsetMin * pixelsPer15Min) / 15;
            int width = (appt.getDuration() * pixelsPer15Min) / 15;
            highlightColor = new Color(144, 238, 144, 128);
            g.setColor(new Color(100, 200, 255));
            g.fillRoundRect(x, y, width, 40, 10, 10);
        }
    }

    public void loadAppointments(List<Appointment> appointments) {
        this.appointments = appointments;
        repaint();
    }
    
 // Hàm tô xanh khoảng slot trống
    public void highlightSlot(java.time.LocalTime startTime, java.time.LocalTime endTime) {
        // Giả sử timeline bắt đầu từ 8h
        int startHour = 8;
        int pixelsPer15Min = 20;
        this.highlightedStart = startTime;
        this.highlightedEnd = endTime;

        int startMinutes = startTime.getHour() * 60 + startTime.getMinute();
        int endMinutes = endTime.getHour() * 60 + endTime.getMinute();
        int offsetStart = startMinutes - startHour * 60;
        int duration = endMinutes - startMinutes;

        highlightStartX = (offsetStart * pixelsPer15Min) / 15;
        highlightWidth = (duration * pixelsPer15Min) / 15;
        
        highlightColor = new Color(144, 238, 144, 128);
        repaint();
    }
    
    

    // Hàm xóa highlights
    public void clearHighlight() {
        highlightStartX = -1;
        highlightWidth = 0;
        repaint();
    }
    
    public static class TimeSlot {
        private final LocalTime start;
        private final LocalTime end;

        public TimeSlot(LocalTime start, LocalTime end) {
            this.start = start;
            this.end = end;
        }

        public LocalTime getStart() {
            return start;
        }

        public LocalTime getEnd() {
            return end;
        }

        @Override
        public String toString() {
            return "Slot: " + start + " - " + end;
        }
    }
    public TimeSlot getHighlightedSlot() {
        if (highlightedStart != null && highlightedEnd != null) {
            return new TimeSlot(highlightedStart, highlightedEnd);
        }
        return null;
    }

    /**
     * Tìm khoảng trống đầu tiên từ thời điểm hiện tại (hoặc giờ bắt đầu làm việc) đủ chứa thời lượng yêu cầu
     */
    public TimeSlot findFirstAvailableSlot(int requiredMinutes, LocalTime startFrom) {
        LocalTime workStart = LocalTime.of(8, 0);
        LocalTime workEnd = LocalTime.of(17, 0);

        // Nếu thời gian truyền vào nhỏ hơn giờ làm việc -> bắt đầu từ giờ làm
        LocalTime pointer = startFrom.isBefore(workStart) ? workStart : startFrom;

        Duration requiredDuration = Duration.ofMinutes(requiredMinutes);

        // Sắp xếp các cuộc hẹn theo thời gian bắt đầu
        appointments.sort(Comparator.comparing(app -> app.getTime().toLocalTime()));

        for (Appointment app : appointments) {
            LocalTime appStart = app.getTime().toLocalTime();
            LocalTime appEnd = appStart.plusMinutes(app.getDuration());

            if (!pointer.isAfter(appStart)) {
                Duration gap = Duration.between(pointer, appStart);
                if (!gap.minus(requiredDuration).isNegative()) {
                    return new TimeSlot(pointer, pointer.plus(requiredDuration));
                }
            }

            if (appEnd.isAfter(pointer)) {
                pointer = appEnd;
            }
        }

        // Kiểm tra khoảng trống cuối ngày
        if (!pointer.plus(requiredDuration).isAfter(workEnd)) {
            return new TimeSlot(pointer, pointer.plus(requiredDuration));
        }

        return null; // Không có khoảng phù hợp
    }

    public void highlightPurpleSlot(int hour, int minute, int durationMinutes) {
        int startHour = 8;
        int pixelsPer15Min = 20;

        int startMinutes = hour * 60 + minute;
        int offsetMin = startMinutes - startHour * 60;

        highlightStartX = (offsetMin * pixelsPer15Min) / 15;
        highlightWidth = (durationMinutes * pixelsPer15Min) / 15;

        highlightColor = new Color(128, 0, 128, 128); // màu tím bán trong suốt

        repaint();
    }
    public static void main(String[] args) {
        // Tạo cửa sổ JFrame
        JFrame frame = new JFrame("Lịch Làm Việc Bác Sĩ");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1100, 300);

        // Tạo AppointmentTimeline
        AppointmentTimeline timeline = new AppointmentTimeline();

        // Tạo danh sách cuộc hẹn mẫu
        List<Appointment> sampleAppointments = new ArrayList<>();

        
        // Nạp dữ liệu vào timeline
        timeline.loadAppointments(sampleAppointments);
        timeline.highlightSlot(LocalTime.of(9, 30), LocalTime.of(10, 0));
        timeline.highlightPurpleSlot(9, 45, 60);
        // Thêm vào frame và hiển thị
        frame.add(timeline);
        frame.setVisible(true);
        
    }
}
