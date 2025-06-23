package controller.receptionist;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;
import java.sql.Time;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JOptionPane;
import javax.swing.JTextField;

import model.Patient;
import reponsitory.AppointmentRepository;
import reponsitory.DoctorRepository;
import model.Appointment;
import model.Doctor;
import model.Examination;
import service.PatientService;
import service.AppointmentService;
import service.DoctorService;
import view.dentistPanel.AppointmentTimeline;
import view.dentistPanel.AppointmentTimeline.TimeSlot;
import view.receptionistPanel.AddPatientPanel;
import view.receptionistPanel.FollowupPanel;
import Utils.Utils;


public class FollowupController implements ActionListener {
    private PatientService patientService;
    private DoctorService doctorService;
    private FollowupPanel followupPanel;
    private AppointmentService appointmentService;

    public FollowupController(FollowupPanel followupPanel) {
        this.patientService = new PatientService();
        this.doctorService = new DoctorService();
        this.appointmentService = new AppointmentService();

        this.followupPanel = followupPanel;
        
        
        this.followupPanel.getDoctorCombo().addActionListener(e -> {
			try {
				updateTimeline();
			} catch (ClassNotFoundException | SQLException | IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		});
        this.followupPanel.getDateChooser().getDateEditor().addPropertyChangeListener("date", evt -> {
            try {
				updateTimeline();
			} catch (ClassNotFoundException | SQLException | IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} // Gọi phương thức cập nhật timeline khi ngày thay đổi
        });
        this.followupPanel.getDurationCombo().addActionListener(e -> {
        	try {
				updateTimeline();
			} catch (ClassNotFoundException | SQLException | IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
        });
        this.followupPanel.getHourCombo().addActionListener(e -> {
			try {
				updateTimeline();
			} catch (ClassNotFoundException | SQLException | IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		});
        this.followupPanel.getMinuteCombo().addActionListener(e -> {
			try {
				updateTimeline();
			} catch (ClassNotFoundException | SQLException | IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		});
//        this.followupPanel.getBtnDexuat().addActionListener(e -> {
//            AppointmentTimeline timeline = followupPanel.getAppointmentTimelinePanel();
//            // Giả sử timeline có hàm trả về slot highlight là một Appointment
//            TimeSlot highlightedAppointment = timeline.getHighlightedSlot();
//            if (highlightedAppointment == null) {
//                JOptionPane.showMessageDialog(null, "Chưa có khung giờ được chọn.");
//                return;
//            }
//
//            // Lấy thông tin từ slot highlight
//            String doctorName = (String) this.followupPanel.getDoctorCombo().getSelectedItem();
//            int doctorId = DoctorRepository.getDoctorIdByName(doctorName); // cần thêm hàm này
//
//            java.util.Date utilDate = this.followupPanel.getDateChooser().getDate();
//            if (utilDate == null) return;
//
//            Date sqlDate = new Date(utilDate.getTime());
//            List<Appointment> appointments = appointmentService.getAppointmentsForDoctorOnDate(doctorId, sqlDate);
//            this.followupPanel.getAppointmentTimelinePanel().loadAppointments(appointments);
//            
//            int gio=(int) this.followupPanel.getHourCombo().getSelectedItem();
//            int phut=(int) this.followupPanel.getMinuteCombo().getSelectedItem();
//            
//
//            String message = String.format("Thông tin slot được chọn:\n" +
//                                           "- Bác sĩ: %d - %s\n" +
//                                           "- Giờ bắt đầu: %02d:%02d\n" +
//                                           "- Thời gian dự kiến: %d phút",
//                                           doctorId, doctorName,
//                                           highlightedAppointment.getStart().getHour(), highlightedAppointment.getStart().getMinute(),
//                                           this.followupPanel.getDurationCombo().getSelectedItem());
//            System.out.println(message);
//            JOptionPane.showMessageDialog(null, message);
//        });
	}

    
	@Override
	public void actionPerformed(ActionEvent e) {
		String src =e.getActionCommand();
		System.out.println("bạn nhấn nút "+src);
		String name = followupPanel.getTxtName().getText();
        String birth = followupPanel.txtBirthDate().getText();
        String idCard=followupPanel.getTxtIdCard().getText();
        String address = followupPanel.getTxtAdress().getText();
        int gender = followupPanel.getGenderCombo().getSelectedIndex(); // 0: Nam, 1: Nữ
        String phone = followupPanel.getTxtPhone().getText();
		if (src.equals("Thêm Lịch Hẹn")) {
          
//          String selectedService = followupPanel.getServiceCombo().getSelectedItem().toString();
//          String appointmentDate = followupPanel.getTxtDate().getText();  // dd/MM/yyyy
//          String appointmentTime = followupPanel.getTxtTime().getText();  // HH:mm
//          int estimatedDuration = Integer.parseInt(followupPanel.getTxtDuration().getText());


          if (name.isEmpty() || birth.isEmpty() || address.isEmpty() || phone.isEmpty()) {
              JOptionPane.showMessageDialog(null, "Vui lòng nhập đầy đủ thông tin.");
              return;
          }

          if (!Utils.validatePhoneNumber(phone)) {
              JOptionPane.showMessageDialog(null, "Số điện thoại không hợp lệ.");
              return;
          }

          java.sql.Date sqlDate = Utils.parseDate(birth);
          if (sqlDate == null) {
              JOptionPane.showMessageDialog(null, "Ngày sinh không đúng định dạng.");
              return;
          }
          if (!Utils.validateIDCard(idCard)) {
              JOptionPane.showMessageDialog(null, "CCCD không hợp lệ.");
              return;
          } 
          // Lấy bác sĩ được chọn
          Doctor selectedDoctor = followupPanel.getSelectedDoctor();
          if (selectedDoctor == null) {
              JOptionPane.showMessageDialog(null, "Vui lòng chọn bác sĩ.");
              return;
          }
         

 //         Patient newPatient = new Patient(followupPanel.getPatientId(), name, sqlDate, address, gender, phone, idCard);

          try {
              // Thêm bệnh nhân vào DB và lấy id bệnh nhân mới
                  Examination examination = new Examination();
                  examination.setPatientId(followupPanel.getPatientId());
                  examination.setDoctorId(selectedDoctor.getId());
                  examination.setStatus("Chưa khám");

                  boolean addExamSuccess = doctorService.addExamination(examination);
                  if (addExamSuccess) {
                      JOptionPane.showMessageDialog(null, "Thêm lịch hẹn thành công!");
                  } else {
                      JOptionPane.showMessageDialog(null, "Thêm lịch hẹn thất bại!");
                  }

//                  followupPanel.dispose(); // Đóng cửa sổ
          } catch (ClassNotFoundException | SQLException | IOException ex) {
              ex.printStackTrace();
              JOptionPane.showMessageDialog(null, "Có lỗi xảy ra");
      }}
          
          else if (src.equals("Chọn Đề Xuất")) {
        	 AppointmentTimeline timeline = followupPanel.getAppointmentTimelinePanel();
        	 
             TimeSlot highlightedAppointment = timeline.getHighlightedSlot();
             if (highlightedAppointment == null) {
                 JOptionPane.showMessageDialog(null, "Chưa có khung giờ được chọn.");
                 return;
             }
             System.out.println("vô đây3");
             String doctorName = (String) this.followupPanel.getDoctorCombo().getSelectedItem();
             int doctorId = DoctorRepository.getDoctorIdByName(doctorName);

             int patientId = followupPanel.getPatientId();

             String service="null";

             int duKien = (int) this.followupPanel.getDurationCombo().getSelectedItem();

             java.util.Date utilDate = this.followupPanel.getDateChooser().getDate();
             if (utilDate == null) return;

             LocalDate selectedDate = utilDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
             LocalDate today = LocalDate.now();

             LocalTime slotTime = highlightedAppointment.getStart();
             int gio = slotTime.getHour();
             int phut = slotTime.getMinute();

             String status = (selectedDate.equals(today) && gio == 0 && phut == 0) ? "Đã đến" : "Chưa đến";

             Date sqlDate = Date.valueOf(selectedDate);
             Time sqlTime = Time.valueOf(slotTime);
             try {
                     boolean addAppoint = appointmentService.addAppoint(doctorId, patientId, service, sqlDate, sqlTime, duKien, status);
                     if (addAppoint) {
                         JOptionPane.showMessageDialog(null, "Đã lưu thành công!");
                         this.followupPanel.clear();
                     } else {
                         JOptionPane.showMessageDialog(null, "Lưu thất bại!");
                     }

//                     followupPanel.dispose(); // Đóng cửa sổ
             } catch (ClassNotFoundException | SQLException | IOException ex) {
                 ex.printStackTrace();
                 JOptionPane.showMessageDialog(null, "Có lỗi xảy ra");
			}

         }

                     
         }
	private void updateTimeline() throws ClassNotFoundException, SQLException, IOException {
        int selectedIndex = this.followupPanel.getDoctorCombo().getSelectedIndex();
        if (selectedIndex < 0) return;

        String doctorName = (String) this.followupPanel.getDoctorCombo().getSelectedItem();
        int doctorId = DoctorRepository.getDoctorIdByName(doctorName); // cần thêm hàm này

        java.util.Date utilDate = this.followupPanel.getDateChooser().getDate();
        if (utilDate == null) return;

        Date sqlDate = new Date(utilDate.getTime());
        List<Appointment> appointments = appointmentService.getAppointmentsForDoctorOnDate(doctorId, sqlDate);
        this.followupPanel.getAppointmentTimelinePanel().loadAppointments(appointments);
        
        int gio=(int) this.followupPanel.getHourCombo().getSelectedItem();
        int phut=(int) this.followupPanel.getMinuteCombo().getSelectedItem();
        
        int duKien=(int) this.followupPanel.getDurationCombo().getSelectedItem();
         
        if (gio == 0 && phut == 0 && doctorName.equals("Null")) {
            utilDate = this.followupPanel.getDateChooser().getDate();
            if (utilDate == null) return;

            sqlDate = new Date(utilDate.getTime());
            Object durationObj = followupPanel.getDurationCombo().getSelectedItem();
            if (durationObj == null) return;
            int durationMinutes = (int) durationObj;

            LocalTime now = LocalTime.now();
            List<Doctor> allDoctors = doctorService.getAllDoctors();

            Doctor bestDoctor = null;
            LocalTime bestStart = null;
            LocalTime bestEnd = null;
            int bestScore = -1;

            for (Doctor doc : allDoctors) {
                doctorId = doc.getId();
                appointments = appointmentService.getAppointmentsForDoctorOnDate(doctorId, sqlDate);

                LocalTime start = now.withSecond(0).withNano(0);
                LocalTime end = LocalTime.of(17, 0);
                java.time.Duration slotDuration = java.time.Duration.ofMinutes(durationMinutes);
                java.time.Duration step = java.time.Duration.ofMinutes(1);

                outer:
                while (!start.plus(slotDuration).isAfter(end)) {
                    LocalTime slotEnd = start.plus(slotDuration);

                    for (Appointment app : appointments) {
                        LocalTime appStart = app.getTime().toLocalTime();
                        LocalTime appEnd = appStart.plusMinutes(app.getDuration());
                        boolean conflict = !(slotEnd.isBefore(appStart) || start.isAfter(appEnd));
                        if (conflict) {
                            start = start.plus(step);
                            continue outer;
                        }
                    }

                    // Tính điểm chọn slot
                    int score = 0;
                    if (appointments.size() == 0 || start.isBefore(now.plusMinutes(30))) {
                        score += 4;
                    }

                    long diff = Math.abs(java.time.Duration.between(now, start).toMinutes());
                    if (diff < 15) score += 3;

                    if (score > bestScore) {
                        bestDoctor = doc;
                        bestStart = start;
                        bestEnd = slotEnd;
                        bestScore = score;
                    }

                    break; // đã tìm slot phù hợp đầu tiên, không cần xét tiếp với bác sĩ này
                }
            }

            if (bestDoctor != null && bestStart != null && bestEnd != null) {
                AppointmentTimeline timeline = followupPanel.getAppointmentTimelinePanel();
                int docId = bestDoctor.getId();

                timeline.loadAppointments(appointmentService.getAppointmentsForDoctorOnDate(docId, sqlDate));
                timeline.highlightSlot(bestStart, bestEnd);

                // Gán bác sĩ vào combobox nếu cần
                System.out.println(bestDoctor.getName());
                followupPanel.getTxtDoctorAppointmentName().setText(bestDoctor.getName());
            } else {
                JOptionPane.showMessageDialog(null, "Không tìm được slot phù hợp.");
            }
//            this.followupPanel.getAppointmentTimelinePanel().highlightPurpleSlot(gio, phut, duKien);
            return;
        }
//if2
        
        
        else if (gio != 0 && doctorName.equals("Null")) {
            utilDate = this.followupPanel.getDateChooser().getDate();
            if (utilDate == null) return;

            sqlDate = new Date(utilDate.getTime());
            Object durationObj = followupPanel.getDurationCombo().getSelectedItem();
            if (durationObj == null) return;
            int durationMinutes = (int) durationObj;

            LocalTime targetTime = LocalTime.of(gio, phut);
            LocalTime workStart = LocalTime.of(8, 0);
            LocalTime workEnd = LocalTime.of(17, 0);

            LocalDate today = LocalDate.now();
            LocalDate selectedDate = sqlDate.toLocalDate();
            LocalTime now = LocalTime.now();

            List<Doctor> allDoctors = doctorService.getAllDoctors();

            Doctor bestDoctor = null;
            LocalTime bestStart = null;
            LocalTime bestEnd = null;
            int bestScore = -1;

            int minAppointmentCount = Integer.MAX_VALUE;
            Map<Integer, Integer> appointmentCountMap = new HashMap<>();
            Map<Integer, LocalTime> selectedSlotMap = new HashMap<>();

            for (Doctor doc : allDoctors) {
                int docId = doc.getId();
                appointments = appointmentService.getAppointmentsForDoctorOnDate(docId, sqlDate);
                appointmentCountMap.put(docId, appointments.size());
                if (appointments.size() < minAppointmentCount) {
                    minAppointmentCount = appointments.size();
                }

                LocalTime bestSlotForDoctor = null;
                long minTimeDiff = Long.MAX_VALUE;
                java.time.Duration slotDuration = java.time.Duration.ofMinutes(durationMinutes);
                java.time.Duration step = java.time.Duration.ofMinutes(1);

                // Nếu khám hôm nay, bắt đầu từ giờ hiện tại. Ngày khác thì từ 08:00
                LocalTime current = selectedDate.equals(today) ? now.withSecond(0).withNano(0) : workStart;
                if (current.isBefore(workStart)) current = workStart;

                outer:
                while (!current.plus(slotDuration).isAfter(workEnd)) {
                    LocalTime slotEnd = current.plus(slotDuration);
                    boolean conflict = false;

                    for (Appointment app : appointments) {
                        LocalTime appStart = app.getTime().toLocalTime();
                        LocalTime appEnd = appStart.plusMinutes(app.getDuration());
                        if (!(slotEnd.isBefore(appStart) || current.isAfter(appEnd))) {
                            conflict = true;
                            break;
                        }
                    }

                    if (!conflict) {
                        long diff = Math.abs(java.time.Duration.between(current, targetTime).toMinutes());
                        if (diff < minTimeDiff) {
                            minTimeDiff = diff;
                            bestSlotForDoctor = current;
                        }
                    }

                    current = current.plus(step);
                }

                if (bestSlotForDoctor != null) {
                    selectedSlotMap.put(docId, bestSlotForDoctor);
                }
            }

            // Tính điểm chọn bác sĩ tốt nhất
            for (Doctor doc : allDoctors) {
                int docId = doc.getId();
                if (!selectedSlotMap.containsKey(docId)) continue;

                LocalTime slotStart = selectedSlotMap.get(docId);
                long diff = Math.abs(java.time.Duration.between(slotStart, targetTime).toMinutes());

                int score = 0;
                if (appointmentCountMap.get(docId) == minAppointmentCount && diff <= 30) {
                    score += 5;
                }
                if (bestStart == null || diff < Math.abs(java.time.Duration.between(bestStart, targetTime).toMinutes())) {
                    score += 4;
                }

                if (score > bestScore) {
                    bestScore = score;
                    bestDoctor = doc;
                    bestStart = slotStart;
                    bestEnd = slotStart.plusMinutes(durationMinutes);
                }
            }

            if (bestDoctor != null && bestStart != null && bestEnd != null) {
                // Kiểm tra có cuộc hẹn nào kết thúc <15 phút trước bestStart không
                List<Appointment> doctorAppointments = appointmentService.getAppointmentsForDoctorOnDate(bestDoctor.getId(), sqlDate);
                boolean adjusted = false;

                for (Appointment app : doctorAppointments) {
                    LocalTime appEnd = app.getTime().toLocalTime().plusMinutes(app.getDuration());
                    long diffToStart = java.time.Duration.between(appEnd, bestStart).toMinutes();

                    if (diffToStart >= 0 && diffToStart <= 30) {

                        // Có cuộc hẹn gần ngay trước slot => chuyển slot tới sau cuộc hẹn đó
                        LocalTime newStart = appEnd;
                        LocalTime newEnd = newStart.plusMinutes(durationMinutes);
                        System.out.println("Slot đã chọn: " + newStart + " - " + newEnd);
                        bestStart = newStart;
                        bestEnd = newEnd;
                        adjusted = true;
//                        boolean hasConflict = false;
//                        for (Appointment other : doctorAppointments) {
//                            LocalTime otherStart = other.getTime().toLocalTime();
//                            LocalTime otherEnd = otherStart.plusMinutes(other.getDuration());
//                            if (!(newEnd.isBefore(otherStart) || newStart.isAfter(otherEnd))) {
//                                hasConflict = true;
//                                break;
//                            }
//                        }
//
//                        // Nếu slot mới không bị trùng thì dùng
//                        if (!hasConflict && newEnd.isBefore(LocalTime.of(17, 0))) {
//                            bestStart = newStart;
//                            bestEnd = newEnd;
//                            adjusted = true;
//                        	System.out.println("vvoooooooo");
//                        }

                        break; // Chỉ xét slot đầu tiên phù hợp
                    }
                }

                AppointmentTimeline timeline = followupPanel.getAppointmentTimelinePanel();
                int docId = bestDoctor.getId();

                timeline.loadAppointments(doctorAppointments);
                timeline.highlightSlot(bestStart, bestEnd);

                System.out.println((adjusted ? "[ĐÃ ĐIỀU CHỈNH] " : "") + bestDoctor.getName());
                followupPanel.getTxtDoctorAppointmentName().setText(bestDoctor.getName());
            } else {
                JOptionPane.showMessageDialog(null, "Không tìm được khung giờ phù hợp.");
            }


            return;
        }
        
        else if (!doctorName.equals("Null")) {
            utilDate = this.followupPanel.getDateChooser().getDate();
            if (utilDate == null) return;

            sqlDate = new Date(utilDate.getTime());
            Object durationObj = followupPanel.getDurationCombo().getSelectedItem();
            if (durationObj == null) return;
            int durationMinutes = (int) durationObj;

//           if (selectedDoctor == null) return;
            doctorId = DoctorRepository.getDoctorIdByName(doctorName);
            followupPanel.getTxtDoctorAppointmentName().setText(doctorName);
             appointments = appointmentService.getAppointmentsForDoctorOnDate(doctorId, sqlDate);
            AppointmentTimeline timeline = followupPanel.getAppointmentTimelinePanel();
            timeline.loadAppointments(appointments); // chỉ hiện timeline bác sĩ này

            LocalTime workStart = LocalTime.of(8, 0);
            LocalTime workEnd = LocalTime.of(17, 0);
            java.time.Duration slotDuration = java.time.Duration.ofMinutes(durationMinutes);
            java.time.Duration step = java.time.Duration.ofMinutes(1);
            
            LocalTime startSearchTime;
            if (gio == 0) {
                if (sqlDate.toLocalDate().isEqual(LocalDate.now())) {
                    startSearchTime = LocalTime.now().plusMinutes(1); // thời gian thực tế
                } else {
                    startSearchTime = workStart;
                }
            } else if (gio == 8 && phut == 0) {
                startSearchTime = workStart;
            } else {
                startSearchTime = LocalTime.of(gio, phut);
            }

            LocalTime bestStart = null;
            long bestDiff = Long.MAX_VALUE;

            LocalTime current = workStart;
            while (!current.plus(slotDuration).isAfter(workEnd)) {
                LocalTime slotEnd = current.plus(slotDuration);
                boolean conflict = false;
                for (Appointment app : appointments) {
                    LocalTime appStart = app.getTime().toLocalTime();
                    LocalTime appEnd = appStart.plusMinutes(app.getDuration());
                    if (!(slotEnd.isBefore(appStart) || current.isAfter(appEnd))) {
                        conflict = true;
                        break;
                    }
                }

                if (!conflict) {
                    if (gio == 0 || (gio == 8 && phut == 0)) {
                        bestStart = current;
                        break; // chọn slot đầu tiên là được
                    } else {
                        long diff = Duration.between(current, startSearchTime).toMinutes();
                        long absDiff = Math.abs(diff);

                        if (diff <= 0 && absDiff <=15 ) {
                            bestDiff = absDiff;
                            bestStart = current;
                            break; // ưu tiên slot sớm hơn gần nhất
                        } else if (absDiff < bestDiff) {
                            bestDiff = absDiff;
                            bestStart = current;
                        }
                    }
                }

                current = current.plus(step);
            }

            if (bestStart != null) {
                timeline.highlightSlot( bestStart, bestStart.plus(slotDuration));
            } else {
                JOptionPane.showMessageDialog(null, "Không có khung giờ phù hợp.");
            }
//            this.followupPanel.getAppointmentTimelinePanel().highlightPurpleSlot(gio, phut, duKien);

            return;
        }
        AppointmentTimeline timeline = followupPanel.getAppointmentTimelinePanel();
        LocalTime start = timeline.getHighlightedStart();
        LocalTime end = timeline.getHighlightedEnd();

        if (start != null && end != null) {
            System.out.println("Slot đã chọn: " + start + " - " + end);
            // Xử lý tiếp với slot này
        } else {
            System.out.println("Chưa có slot nào được chọn");
        }
}}


//doctorId = DoctorRepository.getDoctorIdByName("Trần Thị Trâm"); // cần thêm hàm này
//List<Appointment> appointments1 = appointmentService.getAppointmentsForDoctorOnDate(doctorId, sqlDate);
//
//this.followupPanel.getAppointmentTimelinePanel1().loadAppointments(appointments1);
//this.followupPanel.getAppointmentTimelinePanel1().highlightSlot(LocalTime.of(15,30), LocalTime.of(16,45));
//
//LocalTime customStart = LocalTime.of(8, 0);
//
//AppointmentTimeline.TimeSlot slot = followupPanel.getAppointmentTimelinePanel().findFirstAvailableSlot(15, customStart);
//if (slot != null) {
//	followupPanel.getAppointmentTimelinePanel().highlightSlot(slot.getStart(), slot.getEnd());
//  System.out.println("Slot hợp lệ: " + slot);
//} else {
//  System.out.println("Không có slot phù hợp");
//}
//AppointmentTimeline timeline = followupPanel.getAppointmentTimelinePanel1();

//slot = followupPanel.getAppointmentTimelinePanel1().findFirstAvailableSlot(15, customStart);
//if (slot != null) {
//	followupPanel.getAppointmentTimelinePanel1().highlightSlot(slot.getStart(), slot.getEnd());
//  System.out.println("Slot hợp lệ: " + slot);
//} else {
//  System.out.println("Không có slot phù hợp");
//}

//java.util.Date utilDate1 = followupPanel.getDateChooser().getDate();
//if (utilDate1 == null) return;
//
//Object selectedItem = followupPanel.getDurationCombo().getSelectedItem();
//if (selectedItem == null) return;
//
//int durationMinutes;
//try {
//  durationMinutes = Integer.parseInt(selectedItem.toString().replaceAll("[^0-9]", ""));
//  if (durationMinutes <= 0) return;
//} catch (NumberFormatException e) {
//  return;
//}
//
//Date sqlDate1 = new Date(utilDate1.getTime());
//
//int[] doctorIds = {
//  DoctorRepository.getDoctorIdByName("Nguyễn Hữu Tín"),
//  DoctorRepository.getDoctorIdByName("Trần Thị Trâm"),
//  DoctorRepository.getDoctorIdByName("Lê Văn Nam")
//};
//
//AppointmentTimeline[] timelines = {
//  followupPanel.getAppointmentTimelinePanel(),
//  followupPanel.getAppointmentTimelinePanel1(),
//  followupPanel.getAppointmentTimelinePanel2()
//};
//
//// Xóa highlight cũ trước khi tìm slot mới
//for (AppointmentTimeline timeline : timelines) {
//  timeline.clearHighlight();
//}
//
//for (int i = 0; i < doctorIds.length; i++) {
//	doctorId = doctorIds[i];
//  List<Appointment> appointments3 = appointmentService.getAppointmentsForDoctorOnDate(doctorId, sqlDate1);
//
//  java.time.LocalTime startTime = java.time.LocalTime.of(8, 0);
//  java.time.LocalTime endTime = java.time.LocalTime.of(17, 0);
//  java.time.Duration slotDuration = java.time.Duration.ofMinutes(durationMinutes);
//  java.time.Duration step = java.time.Duration.ofMinutes(1);
//
//  outer:
//  while (!startTime.plus(slotDuration).isAfter(endTime)) {
//      java.time.LocalTime slotEnd = startTime.plus(slotDuration);
//
//      for (Appointment app : appointments3) {
//          java.time.LocalTime appStart = app.getTime().toLocalTime().minus(step).minus(step);
//          java.time.LocalTime appEnd = appStart.plusMinutes(app.getDuration());
//          
////          startTime=startTime.minus(step);
//          // Kiểm tra trùng lịch
//          boolean conflict = !(slotEnd.isBefore(appStart) || startTime.isAfter(appEnd));
//          if (conflict) {
//              startTime = startTime.plus(step);
//              continue outer;
//          }
//      }
//
//      // Tìm được slot phù hợp, tô xanh trên timeline tương ứng
//      timelines[i].highlightSlot(doctorId, startTime, slotEnd);
//      return;
//  }
//}
//
//// Không tìm được slot phù hợp, xóa highlight tất cả
//for (AppointmentTimeline timeline : timelines) {
//  timeline.clearHighlight();
//  System.out.println("ko hợp");
//}

//this.followupPanel.getAppointmentTimelinePanel1().highlightSlot(doctorId, LocalTime.of(10,30), LocalTime.of(10,45));
//System.out.println("vẽ rồi");
//}

//private void findAvailableSlot() {
//java.util.Date utilDate = followupPanel.getDateChooser().getDate();
//if (utilDate == null) return;
//
//Object selectedItem = followupPanel.getDurationCombo().getSelectedItem();
//if (selectedItem == null) return;
//
//int durationMinutes;
//try {
//  durationMinutes = Integer.parseInt(selectedItem.toString().replaceAll("[^0-9]", ""));
//  if (durationMinutes <= 0) return;
//} catch (NumberFormatException e) {
//  return;
//}
//
//Date sqlDate = new Date(utilDate.getTime());
//
//int[] doctorIds = {
//  DoctorRepository.getDoctorIdByName("Nguyễn Hữu Tín"),
//  DoctorRepository.getDoctorIdByName("Trần Thị Trâm"),
//  DoctorRepository.getDoctorIdByName("Lê Văn Nam")
//};
//
//AppointmentTimeline[] timelines = {
//  followupPanel.getAppointmentTimelinePanel(),
//  followupPanel.getAppointmentTimelinePanel1(),
//  followupPanel.getAppointmentTimelinePanel2()
//};
//
//// Xóa highlight cũ trước khi tìm slot mới
//for (AppointmentTimeline timeline : timelines) {
//  timeline.clearHighlight();
//}
//
//for (int i = 0; i < doctorIds.length; i++) {
//  int doctorId = doctorIds[i];
//  List<Appointment> appointments = appointmentService.getAppointmentsForDoctorOnDate(doctorId, sqlDate);
//
//  java.time.LocalTime startTime = java.time.LocalTime.of(8, 0);
//  java.time.LocalTime endTime = java.time.LocalTime.of(17, 0);
//  java.time.Duration slotDuration = java.time.Duration.ofMinutes(durationMinutes);
//  java.time.Duration step = java.time.Duration.ofMinutes(10);
//
//  outer:
//  while (!startTime.plus(slotDuration).isAfter(endTime)) {
//      java.time.LocalTime slotEnd = startTime.plus(slotDuration);
//
//      for (Appointment app : appointments) {
//          java.time.LocalTime appStart = app.getTime().toLocalTime();
//          java.time.LocalTime appEnd = appStart.plusMinutes(app.getDuration());
//
//          // Kiểm tra trùng lịch
//          boolean conflict = !(slotEnd.isBefore(appStart) || startTime.isAfter(appEnd));
//          if (conflict) {
//              startTime = startTime.plus(step);
//              continue outer;
//          }
//      }
//
//      // Tìm được slot phù hợp, tô xanh trên timeline tương ứng
//      timelines[i].highlightSlot(doctorId, startTime, slotEnd);
//      return;
//  }
//}
//
//// Không tìm được slot phù hợp, xóa highlight tất cả
//for (AppointmentTimeline timeline : timelines) {
//  timeline.clearHighlight();
//}
//}





//public void actionPerformed(ActionEvent e) {
//// xử lý thêm lịch hẹn
//}

//public static void main(String[] args) {
//new AddPatientController(); // Khởi tạo controller
//}

