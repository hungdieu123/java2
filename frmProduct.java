/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package assHT;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.ButtonModel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.RowFilter;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;

public final class frmProduct extends javax.swing.JFrame {

    ProductDAO listnv;
    int timerun = 0;

    DefaultTableModel model;
    FileInputStream fis;
    int currentIndex = 0;

    public frmProduct() {

        initComponents();
        setLocationRelativeTo(null);
        thoigian();
        clear();
        this.listnv = new QLProduct();
        buttonGroup1.add(radioConhang);
        buttonGroup1.add(radiohethang);
        radioConhang.setActionCommand("Còn Hàng");
        radiohethang.setActionCommand("Hết Hàng");
    }

    private void showItem(int index) {
        try {
            Product nv = listnv.getItemProduct(index);

            this.txtmaSP.setText(nv.getMaSP());
            this.txtName.setText(nv.getName());
            this.optionDanhMuc.setSelectedIndex(returnIntCombobox(nv.getDanhMuc()));
//            this.buttonGroup1.setSelected((ButtonModel) radioConhang, rootPaneCheckingEnabled);
            this.txtLuong.setText(String.valueOf(nv.getDonGia()));

        } catch (Exception e) {
        }
    }

    private int returnIntCombobox(String s) {
        if (s == "LapTop") {
            return 0;
        } else {
            return 1;
        }
    }

    public void thoigian() {
        new Thread() {
            public void run() {
                while (timerun == 0) {
                    Calendar cal = new GregorianCalendar();
                    int second = cal.get(Calendar.SECOND);
                    int minute = cal.get(Calendar.MINUTE);
                    int hour = cal.get(Calendar.HOUR_OF_DAY);
                    int AM_PM = cal.get(Calendar.AM_PM);
                    String day_night = "";
                    if (AM_PM == 1) {
                        day_night = "PM";
                    } else {
                        day_night = "AM";
                    }
                    String str = hour + ":" + minute + ":" + second + " " + day_night;
                    timelabt.setText(str);
                }

            }
        }.start();

    }

    public String kiemtratrung(String ma) {
        String value = "s";
        for (int i = 0; i < listnv.getArrayProducts().size(); i++) {
            if (listnv.getArrayProducts().get(i).getMaSP().equalsIgnoreCase(ma) != true) {

                return value = "0";
            } else {
                String d = String.valueOf(i);
                return value = d;
            }
        }
        return value;
    }

    public boolean kiemtratrung2(String ma) {
        boolean trave = false;
        for (int i = 0; i < listnv.getArrayProducts().size(); i++) {
            if (listnv.getArrayProducts().get(i).getMaSP().equalsIgnoreCase(ma) == true) {
                trave = true;
            }
        }
        return trave;
    }

    public void add() {
        Product nv = new Product();
        try {

            nv.setMaSP(txtmaSP.getText());

            if (nv.getMaSP().length() == 0 || kiemtratrung2(nv.getMaSP())== true) {
                txtmaSP.setBackground(Color.RED);
                JOptionPane.showMessageDialog(this, "mời nhập lại mã và không được trùng");
                return;
            } else {
                this.txtmaSP.setBackground(Color.WHITE);
            }

        } catch (Exception e) {
        }
        try {
            boolean a = true;
            nv.setName(this.txtName.getText());

            if (nv.getName().length() == 0) {
                this.txtName.setBackground(Color.RED);
                JOptionPane.showConfirmDialog(this, "bạn mời bạn nhập tên SP");
                return;
            } else {
                this.txtName.setBackground(Color.WHITE);
            }

        } catch (Exception e) {
        }

        try {
            int index = optionDanhMuc.getSelectedIndex();
            if (index == 0 || index == 1) {
                if (index <= 0) {
                    nv.setDanhMuc("LapTop");
                } else {
                    nv.setDanhMuc("SmartPhone");
                }
            } else {
                JOptionPane.showMessageDialog(this, "mời bạn nhập lại danh mục");
                return;
            }

        } catch (Exception e) {

        }
        try {

            String text = buttonGroup1.getSelection().getActionCommand();
            if (text != "") {
                if (text == "Còn Hàng") {
                    nv.setTinhTrang("Còn Hàng");
                } else {
                    nv.setTinhTrang("Hết Hàng");
                }
            } else {
                JOptionPane.showMessageDialog(this, "mời bạn nhập lại tình trạng");
                return;
            }

        } catch (Exception e) {
        }
        try {
            nv.setDonGia(Integer.parseInt(this.txtLuong.getText()));

            if (nv.getDonGia()> 0) {
                this.txtLuong.setBackground(Color.WHITE);
            } else {
                this.txtLuong.setBackground(Color.RED);
                this.txtLuong.setText("");
                JOptionPane.showMessageDialog(this, "mời bạn nhập lại đơn giá trên 0 ");
                return;
            }

        } catch (Exception e) {
        }
        listnv.insert(nv);

    }

  

    public void clear() {
        this.txtmaSP.setText("");
        this.txtName.setText("");
        this.txtLuong.setText("");
    }

    public void save() {
        FileOutputStream fos = null;
        ObjectOutputStream oos = null;
        try {
            fos = new FileOutputStream("data.txt");
            oos = new ObjectOutputStream(fos);
            oos.writeObject(listnv.getArrayProducts());
            oos.close();
            fos.close();
        } catch (Exception e) {

        }
    }

    public void open() {
        try {
            fis = new FileInputStream("data.txt");
            ObjectInputStream ois = new ObjectInputStream(fis);
            Object data = ois.readObject();
            listnv.setArrayProduct((ArrayList<Product>) data);
            fis.close();
            ois.close();
            JOptionPane.showMessageDialog(this, "đọc file thành công");
        } catch (Exception e) {

        }

    }

    public void delete() {
        try {
            if (txtmaSP.getText().length() == 0) {
                JOptionPane.showMessageDialog(this, "Vui lòng nhập mã nhân viên");

            } else {
                int xoa = this.tblProduct.getSelectedRow();
                listnv.getArrayProducts().remove(xoa);
            }

        } catch (Exception e) {
        }
    }

    public void exit() {
        try {

            btnexit.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent ae) {
                    System.exit(0);
                }
            });
        } catch (Exception e) {
        }
    }

    public void hienthi() {
        try {

            model = (DefaultTableModel) this.tblProduct.getModel();
            model.setRowCount(0);
            for (Product nv : listnv.getArrayProducts()) {
                Object[] row = new Object[]{
                    nv.getName(), nv.getMaSP(), nv.getDanhMuc(), nv.getTinhTrang(), nv.getDonGia()
                };
                model.addRow(row);
            }
        } catch (Exception e) {
        }
    }

   

    

    private void txtegmailActionPerformed(java.awt.event.ActionEvent evt) {
        // TODO add your handling code here
    }

    private void phongbanActionPerformed(java.awt.event.ActionEvent evt) {
        // TODO add your handling code here:
    }

    private void txthovatenActionPerformed(java.awt.event.ActionEvent evt) {
        // TODO add your handling code here:
    }

    private void btnexitActionPerformed(java.awt.event.ActionEvent evt) {
        this.exit();
    }


    private void btnOpenActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnOpenActionPerformed
        open();
        hienthi();
    }//GEN-LAST:event_btnOpenActionPerformed

    private void btnDeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeleteActionPerformed
        delete();
        hienthi();
        clear();
    }//GEN-LAST:event_btnDeleteActionPerformed

    private void btnnewActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnnewActionPerformed
        clear();

    }//GEN-LAST:event_btnnewActionPerformed

    private void btnUpdateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUpdateActionPerformed

        update();
        hienthi();

    }//GEN-LAST:event_btnUpdateActionPerformed

    private void txtmaSPActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtmaSPActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtmaSPActionPerformed

    private void txtNameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNameActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtNameActionPerformed

    private void tblProductMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblProductMouseClicked

        int row = this.tblProduct.getSelectedRow();
        showItem(row);


    }//GEN-LAST:event_tblProductMouseClicked

    private void optionDanhMucActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_optionDanhMucActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_optionDanhMucActionPerformed

    private void btnsave1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnsave1ActionPerformed
        add();
        hienthi();
    }//GEN-LAST:event_btnsave1ActionPerformed

    private void btnOpen1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnOpen1ActionPerformed
        // TODO add your handling code here:
        save();
        JOptionPane.showMessageDialog(this, "ghi file thành công");
    }//GEN-LAST:event_btnOpen1ActionPerformed

    
