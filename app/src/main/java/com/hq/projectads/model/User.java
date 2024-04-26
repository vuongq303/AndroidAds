package com.hq.projectads.model;

public class User {
    private int id;
    private String tai_khoan;
    private String mat_khau;
    private String trang_thai;
    private int so_tien;

    public User(String tai_khoan, int so_tien) {
        this.tai_khoan = tai_khoan;
        this.so_tien = so_tien;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTai_khoan() {
        return tai_khoan;
    }

    public void setTai_khoan(String tai_khoan) {
        this.tai_khoan = tai_khoan;
    }

    public String getMat_khau() {
        return mat_khau;
    }

    public void setMat_khau(String mat_khau) {
        this.mat_khau = mat_khau;
    }

    public String getTrang_thai() {
        return trang_thai;
    }

    public void setTrang_thai(String trang_thai) {
        this.trang_thai = trang_thai;
    }

    public int getSo_tien() {
        return so_tien;
    }

    public void setSo_tien(int so_tien) {
        this.so_tien = so_tien;
    }

    public User(String tai_khoan, String mat_khau) {
        this.tai_khoan = tai_khoan;
        this.mat_khau = mat_khau;
    }

    public User(int id, String tai_khoan, String mat_khau, String trang_thai, int so_tien) {
        this.id = id;
        this.tai_khoan = tai_khoan;
        this.mat_khau = mat_khau;
        this.trang_thai = trang_thai;
        this.so_tien = so_tien;
    }
}
