package com.hq.projectads.model;

public class Request {
    private int id;
    private String tai_khoan;
    private int so_tien;
    private String thong_tin1;
    private String thong_tin2;
    private int trang_thai;

    public Request(String tai_khoan, int so_tien, String thong_tin1, String thong_tin2) {
        this.tai_khoan = tai_khoan;
        this.so_tien = so_tien;
        this.thong_tin1 = thong_tin1;
        this.thong_tin2 = thong_tin2;
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

    public int getSo_tien() {
        return so_tien;
    }

    public void setSo_tien(int so_tien) {
        this.so_tien = so_tien;
    }

    public String getThong_tin1() {
        return thong_tin1;
    }

    public void setThong_tin1(String thong_tin1) {
        this.thong_tin1 = thong_tin1;
    }

    public String getThong_tin2() {
        return thong_tin2;
    }

    public void setThong_tin2(String thong_tin2) {
        this.thong_tin2 = thong_tin2;
    }

    public int getTrang_thai() {
        return trang_thai;
    }

    public void setTrang_thai(int trang_thai) {
        this.trang_thai = trang_thai;
    }
}
