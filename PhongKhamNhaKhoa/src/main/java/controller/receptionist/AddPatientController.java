package controller.receptionist;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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

import model.Patient;
import reponsitory.AppointmentRepository;
import reponsitory.DoctorRepository;
import reponsitory.ExamReponsitory;
import model.Appointment;
import model.Doctor;
import model.Examination;
import service.PatientService;
import service.AppointmentService;
import service.DoctorService;
import view.dentistPanel.AppointmentTimeline;
import view.dentistPanel.AppointmentTimeline.TimeSlot;
import view.receptionistPanel.AddPatientPanel;
import Utils.Utils;

public class AddPatientController implements ActionListener{
    private PatientService patientService;
    private DoctorService doctorService;
    private AddPatientPanel addPatientView;
    private AppointmentService appointmentService;

    public AddPatientController(AddPatientPanel addPatientView) {
        this.patientService = new PatientService();
        this.doctorService = new DoctorService();
        this.addPatientView = addPatientView;
        this.appointmentService = new AppointmentService();

        this.addPatientView.getDoctorCombo().addActionListener(e -> {
			try {
				updateTimeline();
			} catch (ClassNotFoundException | SQLException | IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		});
        this.addPatientView.getDateChooser().getDateEditor().addPropertyChangeListener("date", evt -> {
            try {
				updateTimeline();
			} catch (ClassNotFoundException | SQLException | IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} // Gọi phương thức cập nhật timeline khi ngày thay đổi
        });
        this.addPatientView.getDurationCombo().addActionListener(e -> {
        	try {
				updateTimeline();
			} catch (ClassNotFoundException | SQLException | IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
        });
        this.addPatientView.getHourCombo().addActionListener(e -> {
			try {
				updateTimeline();
			} catch (ClassNotFoundException | SQLException | IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		});
        this.addPatientView.getMinuteCombo().addActionListener(e -> {
			try {
				updateTimeline();
			} catch (ClassNotFoundException | SQLException | IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		});
        

//        // Đăng ký listener cho nút Hủy
//        this.addPatientView.addBtnCancelListener(e -> addPatientView.dispose());
    }
    @Override
	public void actionPerformed(ActionEvent e) {
    	int newPatientId=-1;
		String src =e.getActionCommand();
		System.out.println("bạn nhấn nút "+src);
		if (src.equals("Thêm Bệnh Nhân và Lịch Hẹn")) {
          String name = addPatientView.getTxtName().getText();
          java.util.Date birthUtilDate = addPatientView.getDateChooser().getDate();
          if (birthUtilDate == null) {
              JOptionPane.showMessageDialog(null, "Vui lòng chọn ngày sinh hợp lệ.");
              return;
          }
          java.sql.Date birthDate = new java.sql.Date(birthUtilDate.getTime());
          String idCard=addPatientView.getTxtIdCard().getText();
          String address = addPatientView.getTxtAdress().getText();
          int gender = addPatientView.getGenderCombo().getSelectedIndex(); // 0: Nam, 1: Nữ
          String phone = addPatientView.getTxtPhone().getText();

          if (name.isEmpty()) {
              JOptionPane.showMessageDialog(null, "Vui lòng nhập đầy đủ thông tin.");
              return;
          }

          if (!phone.isEmpty() && !Utils.validatePhoneNumber(phone)) {
        	    JOptionPane.showMessageDialog(null, "Số điện thoại không hợp lệ.");
        	    return;
        	}

         
          if (!idCard.isEmpty() && !Utils.validateIDCard(idCard)) {
        	    JOptionPane.showMessageDialog(null, "CCCD không hợp lệ.");
        	    return;
        	}

          try {
        	    if (!phone.isEmpty() && patientService.isPhoneExists(phone)) {
        	        JOptionPane.showMessageDialog(null, "Số điện thoại đã tồn tại trong hệ thống.");
        	        return;
        	    }
        	    if (!idCard.isEmpty() && patientService.isIdCardExists(idCard)) {
        	        JOptionPane.showMessageDialog(null, "CCCD đã tồn tại trong hệ thống.");
        	        return;
        	    }
        	} catch (Exception ex) {
        	    ex.printStackTrace();
        	    JOptionPane.showMessageDialog(null, "Lỗi khi kiểm tra trùng dữ liệu.");
        	    return;
        	}


          // Tạo bệnh nhân mới
          Patient newPatient = new Patient(1, name, birthDate, address, gender, phone, idCard);

          try {
              // Thêm bệnh nhân vào DB và lấy id bệnh nhân mới
              newPatientId = patientService.addPatientAndReturnId(newPatient);
              if (newPatientId > 0) {
                  System.out.println("Thêm bệnh nhân thành công với ID: " + newPatientId);


//                  addPatientView.dispose(); // Đóng cửa sổ
              } else {
                  JOptionPane.showMessageDialog(null, "Thêm bệnh nhân thất bại, thử lại.");
              }
          } catch (ClassNotFoundException | SQLException | IOException ex) {
              ex.printStackTrace();
              JOptionPane.showMessageDialog(null, "Có lỗi xảy ra khi thêm bệnh nhân.");
          }
          
        //Thêm lịch hẹn
		AppointmentTimeline timeline = addPatientView.getAppointmentTimelinePanel();
   	 
        TimeSlot highlightedAppointment = timeline.getHighlightedSlot();
        if (highlightedAppointment == null) {
            JOptionPane.showMessageDialog(null, "Chưa có khung giờ được chọn.");
            return;
        }
        String doctorName = (String) this.addPatientView.getDoctorCombo().getSelectedItem();
        int doctorId = DoctorRepository.getDoctorIdByName(doctorName);

        int patientId = addPatientView.getPatientId();

        String service="null";

        int duKien = (int) this.addPatientView.getDurationCombo().getSelectedItem();

        java.util.Date utilDate = this.addPatientView.getDateChooser().getDate();
        if (utilDate == null) return;

        LocalDate selectedDate = utilDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        LocalDate today = LocalDate.now();

        LocalTime slotTime = highlightedAppointment.getStart();
        int gio = slotTime.getHour();
        int phut = slotTime.getMinute();

        String status = (selectedDate.equals(today) && gio == 0 && phut == 0) ? "Đã đến" : "Chưa đến";
        int addAppoint=0;
        Date sqlDate = Date.valueOf(selectedDate);
        Time sqlTime = Time.valueOf(slotTime);
        try {
                addAppoint = AppointmentRepository.insertAppointmentAndReturnId(doctorId, newPatientId, service, sqlDate, sqlTime, duKien, status);
                if (addAppoint>0) {
                    JOptionPane.showMessageDialog(null, "Đã lưu thành công!");
                    this.addPatientView.clear();
                    if(status.equals("Đã đến")) {
                    	boolean addExam=ExamReponsitory.insertExamination(addAppoint, doctorId, patientId, "Chưa khám");
                    	if(addExam) {
                    		JOptionPane.showMessageDialog(null, "Đã thêm cuộc khám thành công");
                    	}else {
                    		JOptionPane.showMessageDialog(null, "Lưu cuộc khám thất bại!");
                    	}
                    }

                } else {
                    JOptionPane.showMessageDialog(null, "Lưu thất bại!");
                }

//                followupPanel.dispose(); // Đóng cửa sổ
        } catch (ClassNotFoundException | SQLException | IOException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Có lỗi xảy ra");
		}
	}
    }
    
    private void updateTimeline() throws ClassNotFoundException, SQLException, IOException {
        int selectedIndex = this.addPatientView.getDoctorCombo().getSelectedIndex();
        if (selectedIndex < 0) return;

        String doctorName = (String) this.addPatientView.getDoctorCombo().getSelectedItem();
        int doctorId = DoctorRepository.getDoctorIdByName(doctorName); // cần thêm hàm này

        java.util.Date utilDate = this.addPatientView.getDateChooser().getDate();
        if (utilDate == null) return;

        Date sqlDate = new Date(utilDate.getTime());
        List<Appointment> appointments = appointmentService.getAppointmentsForDoctorOnDate(doctorId, sqlDate);
        this.addPatientView.getAppointmentTimelinePanel().loadAppointments(appointments);
        
        int gio=(int) this.addPatientView.getHourCombo().getSelectedItem();
        int phut=(int) this.addPatientView.getMinuteCombo().getSelectedItem();
        
        int duKien=(int) this.addPatientView.getDurationCombo().getSelectedItem();
         
        if (gio == 0 && phut == 0 && doctorName.equals("Null")) {
            utilDate = this.addPatientView.getDateChooser().getDate();
            if (utilDate == null) return;

            sqlDate = new Date(utilDate.getTime());
            Object durationObj = addPatientView.getDurationCombo().getSelectedItem();
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
                AppointmentTimeline timeline = addPatientView.getAppointmentTimelinePanel();
                int docId = bestDoctor.getId();

                timeline.loadAppointments(appointmentService.getAppointmentsForDoctorOnDate(docId, sqlDate));
                timeline.highlightSlot(bestStart, bestEnd);

                // Gán bác sĩ vào combobox nếu cần
                System.out.println(bestDoctor.getName());
                addPatientView.getTxtDoctorAppointmentName().setText(bestDoctor.getName());
            } else {
                JOptionPane.showMessageDialog(null, "Không tìm được slot phù hợp.");
            }
//            this.followupPanel.getAppointmentTimelinePanel().highlightPurpleSlot(gio, phut, duKien);
            return;
        }

        else if (gio != 0 && doctorName.equals("Null")) {
            utilDate = this.addPatientView.getDateChooser().getDate();
            if (utilDate == null) return;

            sqlDate = new Date(utilDate.getTime());
            Object durationObj = addPatientView.getDurationCombo().getSelectedItem();
            if (durationObj == null) return;
            int durationMinutes = (int) durationObj;

            LocalTime targetTime = LocalTime.of(gio, phut);
            LocalTime workStart = LocalTime.of(8, 0);
            LocalTime workEnd = LocalTime.of(17, 0);

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
                LocalTime current = workStart;

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

            // Tính điểm và chọn bác sĩ tốt nhất
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
                AppointmentTimeline timeline = addPatientView.getAppointmentTimelinePanel();
                int docId = bestDoctor.getId();

                timeline.loadAppointments(appointmentService.getAppointmentsForDoctorOnDate(docId, sqlDate));
                timeline.highlightSlot(bestStart, bestEnd);

                System.out.println(bestDoctor.getName());
                addPatientView.getTxtDoctorAppointmentName().setText(bestDoctor.getName());
            } else {
                JOptionPane.showMessageDialog(null, "Không tìm được khung giờ phù hợp.");
            }
//            this.followupPanel.getAppointmentTimelinePanel().highlightPurpleSlot(gio, phut, duKien);

            return;
        }
        if (!doctorName.equals("Null")) {
            utilDate = this.addPatientView.getDateChooser().getDate();
            if (utilDate == null) return;

            sqlDate = new Date(utilDate.getTime());
            Object durationObj = addPatientView.getDurationCombo().getSelectedItem();
            if (durationObj == null) return;
            int durationMinutes = (int) durationObj;

//           if (selectedDoctor == null) return;
            doctorId = DoctorRepository.getDoctorIdByName(doctorName);
            addPatientView.getTxtDoctorAppointmentName().setText(doctorName);
             appointments = appointmentService.getAppointmentsForDoctorOnDate(doctorId, sqlDate);
            AppointmentTimeline timeline = addPatientView.getAppointmentTimelinePanel();
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
        AppointmentTimeline timeline = addPatientView.getAppointmentTimelinePanel();
        LocalTime start = timeline.getHighlightedStart();
        LocalTime end = timeline.getHighlightedEnd();

        if (start != null && end != null) {
            System.out.println("Slot đã chọn: " + start + " - " + end);
            // Xử lý tiếp với slot này
        } else {
            System.out.println("Chưa có slot nào được chọn");
        }
    }

//    public static void main(String[] args) {
//        new AddPatientController(); // Khởi tạo controller
//    }
}

//Thêm lịch khám (Examination)
//Examination examination = new Examination();
//examination.setPatientId(newPatientId);
//examination.setDoctorId(selectedDoctor.getId());
//examination.setStatus("Chưa khám");
//
//boolean addExamSuccess = doctorService.addExamination(examination);
//if (addExamSuccess) {
//  JOptionPane.showMessageDialog(null, "Thêm bệnh nhân và lịch hẹn thành công!");
//} else {
//  JOptionPane.showMessageDialog(null, "Thêm bệnh nhân thành công nhưng thêm lịch hẹn thất bại!");
//}