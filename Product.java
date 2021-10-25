/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package assHT;

import java.io.Serializable;
import javax.swing.JOptionPane;

public class Product implements Serializable{

    public void setMaSP(String maNhanVien) {
        this.maSP = maNhanVien;
    }

    public void setName(String hoVaTen) {
        this.Name = hoVaTen;
    }

    public void setDanhMuc(String danhMuc) {
        this.danhMuc = danhMuc;
    }

    public void setTinhTrang(String tinhTrang) {
        this.tinhTrang = tinhTrang;
    }

    public void setDonGia(int donGia) {
        this.donGia = donGia;
    }
    

  private  String maSP;
   private String Name;
   private String danhMuc;
   private String tinhTrang;
    private int donGia;

    public Product() {
    }

    public String getMaSP() {
        return maSP;
    }

    public String getName() {
        return Name;
    }

    public String getDanhMuc() {
        return danhMuc;
    }

    public String getTinhTrang() {
        return tinhTrang;
    }

    public int getDonGia() {
        return donGia;
    }

    
   

    public Product(String maSP, String Name, String danhMuc, String TinhTrang, int donGia) {
        this.maSP = maSP;
        this.Name = Name;
        this.danhMuc = danhMuc;
        this.tinhTrang = TinhTrang;
        this.donGia = donGia;
       
    }
 
}
