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

    public void update() {
        Product nv = new Product();
        try {

            nv.setMaSP(txtmaSP.getText());

            if (nv.getMaSP().length() == 0) {
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

        int position = Integer.parseInt(kiemtratrung(kiemtratrung(this.txtmaSP.getText())));
        listnv.update(position, nv);

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

   

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        timelabt = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        txtmaSP = new javax.swing.JTextField();
        txtName = new javax.swing.JTextField();
        optionDanhMuc = new javax.swing.JComboBox<>();
        btnnew = new javax.swing.JButton();
        btnUpdate = new javax.swing.JButton();
        btnsave1 = new javax.swing.JButton();
        btnDelete = new javax.swing.JButton();
        btnOpen = new javax.swing.JButton();
        btnexit = new javax.swing.JButton();
        btnOpen1 = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        radioConhang = new javax.swing.JRadioButton();
        radiohethang = new javax.swing.JRadioButton();
        jLabel8 = new javax.swing.JLabel();
        txtLuong = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblProduct = new javax.swing.JTable();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setBackground(new java.awt.Color(102, 255, 204));
        setMinimumSize(new java.awt.Dimension(760, 651));
        getContentPane().setLayout(null);

        timelabt.setBackground(new java.awt.Color(51, 51, 0));
        timelabt.setFont(new java.awt.Font("Times New Roman", 1, 24)); // NOI18N
        timelabt.setForeground(new java.awt.Color(255, 0, 0));
        timelabt.setText("time");
        getContentPane().add(timelabt);
        timelabt.setBounds(560, 10, 134, 44);

        jLabel1.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel1.setText("Mã SP");

        jLabel3.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel3.setText("Danh Mục");

        jLabel5.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel5.setText("Tên SP");

        txtmaSP.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtmaSPActionPerformed(evt);
            }
        });

        txtName.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNameActionPerformed(evt);
            }
        });

        optionDanhMuc.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "LapTop", "SmartPhone", " ", " " }));
        optionDanhMuc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                optionDanhMucActionPerformed(evt);
            }
        });

        btnnew.setBackground(new java.awt.Color(102, 102, 102));
        btnnew.setFont(new java.awt.Font("Times New Roman", 1, 24)); // NOI18N
        btnnew.setText("Xoá From");
        btnnew.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(240, 240, 240)));
        btnnew.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnnewActionPerformed(evt);
            }
        });

        btnUpdate.setBackground(new java.awt.Color(102, 102, 102));
        btnUpdate.setFont(new java.awt.Font("Times New Roman", 1, 24)); // NOI18N
        btnUpdate.setText("Sửa");
        btnUpdate.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));
        btnUpdate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUpdateActionPerformed(evt);
            }
        });

        btnsave1.setBackground(new java.awt.Color(102, 102, 102));
        btnsave1.setFont(new java.awt.Font("Times New Roman", 1, 24)); // NOI18N
        btnsave1.setText("Thêm");
        btnsave1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));
        btnsave1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnsave1ActionPerformed(evt);
            }
        });

        btnDelete.setBackground(new java.awt.Color(102, 102, 102));
        btnDelete.setFont(new java.awt.Font("Times New Roman", 1, 24)); // NOI18N
        btnDelete.setText("Delete");
        btnDelete.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));
        btnDelete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDeleteActionPerformed(evt);
            }
        });

        btnOpen.setBackground(new java.awt.Color(102, 102, 102));
        btnOpen.setFont(new java.awt.Font("Times New Roman", 1, 24)); // NOI18N
        btnOpen.setText("Đọc File");
        btnOpen.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 1, 1, 1, new java.awt.Color(255, 255, 255)));
        btnOpen.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnOpenActionPerformed(evt);
            }
        });

        btnexit.setBackground(new java.awt.Color(102, 102, 102));
        btnexit.setFont(new java.awt.Font("Times New Roman", 1, 24)); // NOI18N
        btnexit.setText("Exit");
        btnexit.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));
        btnexit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnexitActionPerformed(evt);
            }
        });

        btnOpen1.setBackground(new java.awt.Color(102, 102, 102));
        btnOpen1.setFont(new java.awt.Font("Times New Roman", 1, 24)); // NOI18N
        btnOpen1.setText(" Ghi File");
        btnOpen1.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 1, 1, 1, new java.awt.Color(255, 255, 255)));
        btnOpen1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnOpen1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(txtmaSP, javax.swing.GroupLayout.PREFERRED_SIZE, 149, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(100, 100, 100)
                                .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(optionDanhMuc, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtName, javax.swing.GroupLayout.PREFERRED_SIZE, 178, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(btnOpen1, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnsave1, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(9, 9, 9)
                                .addComponent(btnUpdate, javax.swing.GroupLayout.PREFERRED_SIZE, 82, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(btnDelete, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnnew, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(10, 10, 10)
                                .addComponent(btnOpen, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(btnexit, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addGap(0, 0, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtName, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(20, 20, 20)
                        .addComponent(jLabel4))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtmaSP, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(optionDanhMuc, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(32, 32, 32)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnUpdate, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnsave1, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnnew)
                    .addComponent(btnDelete, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnOpen)
                    .addComponent(btnexit, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnOpen1))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        getContentPane().add(jPanel1);
        jPanel1.setBounds(10, 30, 440, 260);

        buttonGroup1.add(radioConhang);
        radioConhang.setText("Còn hàng");

        radiohethang.setText("hết hàng");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(radioConhang, javax.swing.GroupLayout.PREFERRED_SIZE, 94, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(radiohethang, javax.swing.GroupLayout.PREFERRED_SIZE, 86, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(radioConhang, javax.swing.GroupLayout.DEFAULT_SIZE, 36, Short.MAX_VALUE)
                    .addComponent(radiohethang, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        getContentPane().add(jPanel3);
        jPanel3.setBounds(540, 100, 190, 50);

        jLabel8.setText("Tình trạng");
        getContentPane().add(jLabel8);
        jLabel8.setBounds(480, 110, 60, 30);
        getContentPane().add(txtLuong);
        txtLuong.setBounds(540, 160, 180, 30);

        jLabel6.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel6.setText("Đơn Giá");
        getContentPane().add(jLabel6);
        jLabel6.setBounds(480, 160, 101, 28);

        tblProduct.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Tên SP", "Mã SP", "Danh Mục", "Tình Trạng", "Đơn Giá"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblProduct.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblProductMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tblProduct);

        getContentPane().add(jScrollPane1);
        jScrollPane1.setBounds(20, 305, 700, 240);

        pack();
    }// </editor-fold>//GEN-END:initComponents

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

    /**/
    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(frmProduct.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(frmProduct.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(frmProduct.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(frmProduct.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new frmProduct().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnDelete;
    private javax.swing.JButton btnOpen;
    private javax.swing.JButton btnOpen1;
    private javax.swing.JButton btnUpdate;
    private javax.swing.JButton btnexit;
    private javax.swing.JButton btnnew;
    private javax.swing.JButton btnsave1;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JComboBox<String> optionDanhMuc;
    private javax.swing.JRadioButton radioConhang;
    private javax.swing.JRadioButton radiohethang;
    private javax.swing.JTable tblProduct;
    private javax.swing.JLabel timelabt;
    private javax.swing.JTextField txtLuong;
    private javax.swing.JTextField txtName;
    private javax.swing.JTextField txtmaSP;
    // End of variables declaration//GEN-END:variables

}
