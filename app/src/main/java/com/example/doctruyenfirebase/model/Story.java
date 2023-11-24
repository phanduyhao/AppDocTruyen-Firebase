package com.example.doctruyenfirebase.model;

public class Story {
    private String anh;
    private String danhmuc;
    private String link;
    private String noidung;
    private String tentruyen;
    public Story(){};
    public Story(String anh, String danhmuc, String link, String noidung, String tentruyen) {
        this.anh = anh;
        this.danhmuc = danhmuc;
        this.link = link;
        this.noidung = noidung;
        this.tentruyen = tentruyen;
    }

    public String getAnh() {
        return anh;
    }

    public void setAnh(String anh) {
        this.anh = anh;
    }

    public String getDanhmuc() {
        return danhmuc;
    }

    public void setDanhmuc(String danhmuc) {
        this.danhmuc = danhmuc;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getNoidung() {
        return noidung;
    }

    public void setNoidung(String noidung) {
        this.noidung = noidung;
    }

    public String getTentruyen() {
        return tentruyen;
    }

    public void setTentruyen(String tentruyen) {
        this.tentruyen = tentruyen;
    }
}
