package model;

import java.util.ArrayList;
import java.util.List;

public class Patient_management implements IPatient_management {
    private List<Patient> danhSachBenhNhan;

    public Patient_management() {
        danhSachBenhNhan = new ArrayList<>();
    }

    @Override
    public void addPatient(Patient bn) {
        danhSachBenhNhan.add(bn);
        System.out.println("Đã thêm bệnh nhân.");
    }

    @Override
    public void fixPatient(String id, Patient bnMoi) {
        for (int i = 0; i < danhSachBenhNhan.size(); i++) {
            if (danhSachBenhNhan.get(i).getId().equalsIgnoreCase(id)) {
                danhSachBenhNhan.set(i, bnMoi);
                System.out.println("Cập nhật thông tin bệnh nhân thành công.");
                return;
            }
        }
        System.out.println("Không tìm thấy bệnh nhân có ID: " + id);
    }

    @Override
    public void delPatient(String id) {
        boolean removed = danhSachBenhNhan.removeIf(bn -> bn.getId().equalsIgnoreCase(id));
        if (removed) {
            System.out.println("Đã xóa bệnh nhân.");
        } else {
            System.out.println("Không tìm thấy bệnh nhân có ID: " + id);
        }
    }

    @Override
    public boolean checkIfPatientHasVisited(String sdtOrCCCD) {
        for (Patient bn : danhSachBenhNhan) {
            if (bn.getPhoneNumber().equals(sdtOrCCCD) || bn.getCccd().equals(sdtOrCCCD)) {
                return true;
            }
        }
        return false;
    }
    public void hienThiDanhSach() {
        if (danhSachBenhNhan.isEmpty()) {
            System.out.println("Danh sách bệnh nhân trống.");
        } else {
            for (Patient bn : danhSachBenhNhan) {
                bn.displayInfo();
                System.out.println("------------------------------");
            }
        }
    }
}

