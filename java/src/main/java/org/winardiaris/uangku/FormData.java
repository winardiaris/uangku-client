/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools + Templates
 * and open the template in the editor.
 */
package org.winardiaris.uangku;

import java.awt.Component;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import org.json.simple.parser.JSONParser;
import java.net.URLEncoder;

/**
 *
 * @author arst
 */
public class FormData extends javax.swing.JFrame {

    /**
     * Creates new form formDatas
     */
    public FormData() {
        initComponents();
    }
    public void setTableData(){
        getDataURL dataurl = new getDataURL();
        String base_url = dataurl.getUrlBase();
        JSONParser parser = new JSONParser();
        DefaultTableModel model = (DefaultTableModel) Tbldata.getModel(); 
            
             
            String UID = Luid.getText();
            String url = base_url+"?op=viewdata&uid="+UID;
        try {
            model.setRowCount(0);
            String datajson = dataurl.getData(url);

            
            Object obj=JSONValue.parse(datajson);
            JSONArray array=(JSONArray)obj;
            
             int banyak = array.size();
             System.out.println(banyak); 
                for(int i=0;i<banyak;i++){
                    JSONObject data=(JSONObject)array.get(i);
                    Object did = data.get("did"); 
                    Object uid = data.get("uid"); 
                    Object dates = data.get("date"); 
                    Object token = data.get("token"); 
                    Object type = data.get("type"); 
                    Object value = data.get("value"); 
                    Object desc = data.get("desc"); 
                    Object status = data.get("status"); 
                    Object c_at = data.get("c_at"); 
                    Object u_at = data.get("u_at");
                    
                    if("in".equals(type.toString())){
                        Object[] row = { i+1 ,token,dates ,desc ,value ,"-" ,did };
                        model.addRow(row);
                    }
                    else{
                        Object[] row = { i+1 ,token,dates ,desc ,"-" ,value ,did };
                        model.addRow(row);
                    }
                    System.out.println("did:"+did); 
                    System.out.println("uid:"+uid); 
                    System.out.println("date:"+dates); 
                    System.out.println("token:"+token); 
                    System.out.println("type:"+type); 
                    System.out.println("value:"+value); 
                    System.out.println("desc:"+desc); 
                    System.out.println("status:"+status); 
                    System.out.println("c_at:"+c_at); 
                    System.out.println("u_at:"+u_at);
                    System.out.println("----------------------------");
                }
                String saldo = base_url+"?op=saldodata&uid="+UID;
                String datasaldo = dataurl.getData(saldo);
                Object[] row1 = { "" ,"","" ,"" ,"" ,"" ,"" };
                Object[] row = { "" ,"","" ,"Saldo" ,"" ,datasaldo ,"" };
                model.addRow(row1);
                model.addRow(row);
                
            FTambahBersih();
        } catch (IOException ex) {
            Logger.getLogger(FormData.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public void FTambahBersih(){
        Tvalue.setText("");
        Tdesc.setText("");
        Ttoken.setText("");
                
        Ttype.enable(true);
        Tdate.setEnabled(true);
        Tvalue.setEnabled(true);
        Tdesc.setEnabled(true);
        Ttoken.setEnabled(true);
    }
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        Lrealname = new javax.swing.JLabel();
        Lusername = new javax.swing.JLabel();
        Luid = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        Bsignout = new javax.swing.JButton();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        Ttype = new javax.swing.JComboBox();
        Ttoken = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        Tdesc = new javax.swing.JTextArea();
        Tdate = new org.jdesktop.swingx.JXDatePicker();
        Ljumlah = new javax.swing.JLabel();
        Tvalue = new javax.swing.JTextField();
        Bsave = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        Brefresh = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        Tbldata = new javax.swing.JTable();
        Tdatefrom = new org.jdesktop.swingx.JXDatePicker();
        Tdateto = new org.jdesktop.swingx.JXDatePicker();
        Bfind = new javax.swing.JButton();
        Tsearch = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        Bdelete = new javax.swing.JButton();
        Bedit = new javax.swing.JButton();
        Buserpreference = new javax.swing.JButton();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowActivated(java.awt.event.WindowEvent evt) {
                formWindowActivated(evt);
            }
        });

        Lrealname.setText("realname");

        Lusername.setText("username");

        Luid.setText("UID");

        Bsignout.setText("Keluar");
        Bsignout.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                BsignoutMouseClicked(evt);
            }
        });

        jTabbedPane1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTabbedPane1MouseClicked(evt);
            }
        });

        jLabel1.setText("Jenis Keuangan");

        jLabel3.setText("Tanggal*");

        Ttype.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Debet", "Kredit" }));
        Ttype.setName("Ftype"); // NOI18N
        Ttype.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                TtypeActionPerformed(evt);
            }
        });

        Ttoken.setName("Ftoken"); // NOI18N
        Ttoken.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                TtokenActionPerformed(evt);
            }
        });

        jLabel4.setText("Keterangan*");

        jLabel2.setText("No. Bukti*");

        Tdesc.setColumns(20);
        Tdesc.setRows(5);
        jScrollPane1.setViewportView(Tdesc);

        Ljumlah.setText("Jumlah*");

        Tvalue.setFont(new java.awt.Font("Dialog", 0, 18)); // NOI18N

        Bsave.setText("Simpan");
        Bsave.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                BsaveMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(Tvalue)
                    .addComponent(Ttoken)
                    .addComponent(jScrollPane1)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(Bsave))
                    .addComponent(jLabel2)
                    .addComponent(Ljumlah)
                    .addComponent(jLabel4)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel1)
                            .addComponent(Ttype, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(64, 64, 64)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel3)
                            .addComponent(Tdate, javax.swing.GroupLayout.PREFERRED_SIZE, 202, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jLabel3))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(Tdate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(Ttype, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(12, 12, 12)
                .addComponent(Ljumlah)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(Tvalue, javax.swing.GroupLayout.PREFERRED_SIZE, 57, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 15, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(Ttoken, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 114, Short.MAX_VALUE)
                .addGap(18, 18, 18)
                .addComponent(Bsave)
                .addContainerGap())
        );

        jTabbedPane1.addTab("Tambah", jPanel1);

        Brefresh.setText("Segarkan");
        Brefresh.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                BrefreshMouseClicked(evt);
            }
        });
        Brefresh.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BrefreshActionPerformed(evt);
            }
        });

        Tbldata.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null}
            },
            new String [] {
                "No", "No. Bukti", "Tanggal", "Keterangan", "Debet", "Kredit", "did"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, true, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        Tbldata.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                TbldataMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(Tbldata);
        Tbldata.getColumnModel().getSelectionModel().setSelectionMode(javax.swing.ListSelectionModel.SINGLE_INTERVAL_SELECTION);

        Bfind.setText("Cari");
        Bfind.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                BfindMouseClicked(evt);
            }
        });

        jLabel5.setText("Dari");

        jLabel6.setText("Sampai");

        jLabel7.setText("Cari");

        Bdelete.setText("Hapus");
        Bdelete.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                BdeleteMouseClicked(evt);
            }
        });

        Bedit.setText("Perbaharui");
        Bedit.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                BeditMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 827, Short.MAX_VALUE)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(Tdatefrom, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel5))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(Tdateto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel6))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(jLabel7)
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(Tsearch, javax.swing.GroupLayout.PREFERRED_SIZE, 187, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(Bfind)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(Brefresh))))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(Bedit)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(Bdelete)))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(jLabel6)
                    .addComponent(jLabel7))
                .addGap(7, 7, 7)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(Tdatefrom, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(Tdateto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(Bfind)
                    .addComponent(Tsearch, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(Brefresh))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 276, Short.MAX_VALUE)
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(Bdelete)
                    .addComponent(Bedit))
                .addContainerGap())
        );

        jTabbedPane1.addTab("Data", jPanel2);

        Buserpreference.setText("Pengaturan");
        Buserpreference.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                BuserpreferenceMouseClicked(evt);
            }
        });

        jLabel8.setFont(new java.awt.Font("Dialog", 1, 24)); // NOI18N
        jLabel8.setText("Uangku");

        jLabel9.setText("Pencatatan Keuangan Pribadimu");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jTabbedPane1)
                            .addComponent(jSeparator1)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(Luid)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(Lusername)
                                .addGap(18, 18, 18)
                                .addComponent(Lrealname)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(Buserpreference)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(Bsignout))))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(270, 270, 270)
                                .addComponent(jLabel9))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(329, 329, 329)
                                .addComponent(jLabel8)))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(6, 6, 6)
                .addComponent(jLabel8)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel9)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTabbedPane1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 11, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(Luid)
                    .addComponent(Lusername)
                    .addComponent(Lrealname)
                    .addComponent(Bsignout)
                    .addComponent(Buserpreference))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void formWindowActivated(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowActivated
        try {
            getDataURL dataurl = new getDataURL();
            String base_url = dataurl.getUrlBase();
            String uid = this.getTitle();
            
            String url1 = base_url+"?op=get&from_data=uid&value_data="+uid+"&select_field=username&from_table=user";
            String username = dataurl.getData(url1);
            String url2 = base_url+"?op=get&from_data=uid&value_data="+uid+"&select_field=realname&from_table=user";
            String realname = dataurl.getData(url2);
            
            System.out.println("----------------------------------------");
            System.out.println("UID: "+uid);
            System.out.println("username: "+username);
            System.out.println("realname: "+realname);
            System.out.println("----------------------------------------");
           
            Luid.setText(uid);
            Lusername.setText(username);
            Lrealname.setText(realname);
          
//            setTableData();
            
            //this.setTitle("Uangku - "+realname);
        } catch (IOException ex) {
            Logger.getLogger(FormData.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_formWindowActivated

    private void BrefreshMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_BrefreshMouseClicked
           setTableData();
    }//GEN-LAST:event_BrefreshMouseClicked

    private void BsignoutMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_BsignoutMouseClicked
        int dialogButton = JOptionPane.YES_NO_OPTION;
        int dialogResult;
        dialogResult = JOptionPane.showConfirmDialog(this, "Yakin mau keluar? ", "Konfirmasi", dialogButton);
        if(dialogResult == 0) {
            System.out.println("Quit");
            this.dispose();
        } else {
            System.out.println("cancel");
        } 
    }//GEN-LAST:event_BsignoutMouseClicked

    private void TtypeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_TtypeActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_TtypeActionPerformed

    private void TtokenActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_TtokenActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_TtokenActionPerformed

    private void BsaveMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_BsaveMouseClicked
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        getDataURL dataurl = new getDataURL();
        String base_url = dataurl.getUrlBase();
        Date date = Tdate.getDate();
        
        String UID = Luid.getText();
        String type = Ttype.getSelectedItem().toString();
        String converted_type;
        
        String token = Ttoken.getText();
        String tokens = null;
        try {
            tokens = URLEncoder.encode(token, "UTF-8");
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(FormData.class.getName()).log(Level.SEVERE, null, ex);
        }
        String value = Tvalue.getText();
        String desc =  Tdesc.getText();
        String descs = null;
        try {
            descs = URLEncoder.encode(desc, "UTF-8");
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(FormData.class.getName()).log(Level.SEVERE, null, ex);
        }
        
      
        
        
        if(date == null){
            JOptionPane.showMessageDialog(this,"Isi tanggal terlebih dahulu","Informasi",JOptionPane.ERROR_MESSAGE);
            Tdate.requestFocus(true);
        }
        else if("".equals(value)){
            JOptionPane.showMessageDialog(this,"Isi jumlah terlebih dahulu","Informasi",JOptionPane.ERROR_MESSAGE);
            Tvalue.requestFocus(true);
        }
        else if("".equals(token)){
            JOptionPane.showMessageDialog(this,"Isi No.bukti terlebih dahulu","Informasi",JOptionPane.ERROR_MESSAGE);
            Ttoken.requestFocus(true);
        }
        else if("".equals(desc)){
            JOptionPane.showMessageDialog(this,"Isi deskripsi terlebih dahulu","Informasi",JOptionPane.ERROR_MESSAGE);
            Tdesc.requestFocus(true);
        }
        else{
            if("Debet".equals(type)){converted_type = "in";}
            else{converted_type = "out";}
            
            String converted_date = formatter.format(date);
            
            System.out.println("type : "+converted_type);
            System.out.println("date : "+converted_date);
            System.out.println("value : "+value);
            System.out.println("token : "+token);
            System.out.println("desc : "+desc);

            String url = base_url+"?op=newdata&uid="+UID+"&date="+converted_date+"&token="+tokens+"&type="+converted_type+"&value="+value+"&desc="+descs;
            System.out.println(url);
            String data;

            try {
                data = dataurl.getData(url);
                System.out.println(data);

                if("1".equals(data)){
                    JOptionPane.showMessageDialog(this,"Data berhasi disimpan","Informasi",JOptionPane.INFORMATION_MESSAGE);

                    FTambahBersih();
                }
                else{
                    JOptionPane.showMessageDialog(this,"data gagal disimpan","Informasi",JOptionPane.ERROR_MESSAGE);
                    Tdate.setFocusable(true);
                }
            } catch (IOException ex) {
                Logger.getLogger(FormData.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }//GEN-LAST:event_BsaveMouseClicked

    private void BuserpreferenceMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_BuserpreferenceMouseClicked
       FormUserPreference fpref = new FormUserPreference();
       fpref.setTitle(this.Luid.getText());
       fpref.setLocationRelativeTo(null);
       fpref.setVisible(true);
       this.dispose();
    }//GEN-LAST:event_BuserpreferenceMouseClicked

    private void TbldataMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_TbldataMouseClicked
       int row = Tbldata.getSelectedRow();
       String did_from_click = (Tbldata.getModel().getValueAt(row,6).toString());
       System.out.println("did yang diklik: "+did_from_click);
       
    }//GEN-LAST:event_TbldataMouseClicked

    private void BrefreshActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BrefreshActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_BrefreshActionPerformed

    private void BfindMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_BfindMouseClicked
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        getDataURL dataurl = new getDataURL();
        String base_url = dataurl.getUrlBase();
        JSONParser parser = new JSONParser();
        DefaultTableModel model = (DefaultTableModel) Tbldata.getModel(); 
        
        Date datefrom = Tdatefrom.getDate();
        Date dateto = Tdateto.getDate();
        
        
        
        String search = Tsearch.getText();
        String UID = Luid.getText();
        String url = null;
       
        if(!"".equals(search)){
         url = base_url+"?op=viewdata&uid="+UID+"&search="+search;
        }
        else if(datefrom != null || dateto != null){
         String cdatefrom = formatter.format(datefrom);
         String cdateto = formatter.format(dateto);
         url = base_url+"?op=viewdata&uid="+UID+"&from="+cdatefrom+"&to="+cdateto; 
        }
        else if(datefrom==null && dateto==null && "".equals(search)){
         url = base_url+"?op=viewdata&uid="+UID;
        }
        else{
          String cdatefrom = formatter.format(datefrom);
          String cdateto = formatter.format(dateto);
          url = base_url+"?op=viewdata&uid="+UID+"&search="+search+"&from="+cdatefrom+"&to="+cdateto;  
        }
        
        System.out.println(url);
        
        try {
            model.setRowCount(0);
            String datajson = dataurl.getData(url);
            
            Object obj=JSONValue.parse(datajson);
            JSONArray array=(JSONArray)obj;
            
             int banyak = array.size();
             System.out.println(banyak); 
                for(int i=0;i<banyak;i++){
                    JSONObject data=(JSONObject)array.get(i);
                    Object did = data.get("did"); 
                    Object uid = data.get("uid"); 
                    Object dates = data.get("date"); 
                    Object token = data.get("token"); 
                    Object type = data.get("type"); 
                    Object value = data.get("value"); 
                    Object desc = data.get("desc"); 
                    Object status = data.get("status"); 
                    Object c_at = data.get("c_at"); 
                    Object u_at = data.get("u_at");
                    
                    if("in".equals(type.toString())){
                        Object[] row = { i+1 ,token,dates ,desc ,value ,"-" ,did };
                        model.addRow(row);
                    }
                    else{
                        Object[] row = { i+1 ,token,dates ,desc ,"-" ,value ,did };
                        model.addRow(row);
                    }
                    System.out.println("did:"+did); 
                    System.out.println("uid:"+uid); 
                    System.out.println("date:"+dates); 
                    System.out.println("token:"+token); 
                    System.out.println("type:"+type); 
                    System.out.println("value:"+value); 
                    System.out.println("desc:"+desc); 
                    System.out.println("status:"+status); 
                    System.out.println("c_at:"+c_at); 
                    System.out.println("u_at:"+u_at);
                    System.out.println("----------------------------");
                }
                           
            
            
            Ttype.enable(true);
            Tdate.setEnabled(true);
            Tvalue.setEnabled(true);
            Tdesc.setEnabled(true);
            Ttoken.setEnabled(true);

        } catch (IOException ex) {
            Logger.getLogger(FormData.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }//GEN-LAST:event_BfindMouseClicked

    private void BeditMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_BeditMouseClicked
        int row = Tbldata.getSelectedRow();
        String uid = Luid.getText();
        String did = (Tbldata.getModel().getValueAt(row,6).toString());
                
        if(!"".equals(did)){
            
            String value;
            String type;
            
            System.out.println("uid: "+uid);
            System.out.println("did: "+did);
            
            
           FormEdit fedit = new FormEdit();
           fedit.setTitle(uid+"/"+did);
           fedit.setLocationRelativeTo(null);
           fedit.setVisible(true);
           this.dispose();
           
        }
        else{
          JOptionPane.showMessageDialog(this,"Pilih terlebih dahulu datanya","Informasi",JOptionPane.ERROR_MESSAGE);  
        }
        
        
    }//GEN-LAST:event_BeditMouseClicked

    private void BdeleteMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_BdeleteMouseClicked
        String uid = Luid.getText();
        int row = Tbldata.getSelectedRow();
        String did = (Tbldata.getModel().getValueAt(row,6).toString());
        if(!"".equals(did)){
            int result = JOptionPane.showConfirmDialog((Component) null, "Yakin mau di hapus?","konfirmasi", JOptionPane.OK_CANCEL_OPTION);
        
            if(result==JOptionPane.OK_OPTION){
            
                this.dispose();
                FormDelete del = new FormDelete();
                del.setTitle(uid+"/"+did);
                del.setLocationRelativeTo(null);
                del.setVisible(true);
            }
        }
        else{
          JOptionPane.showMessageDialog(this,"Pilih terlebih dahulu datanya","Informasi",JOptionPane.ERROR_MESSAGE);  
        }

    }//GEN-LAST:event_BdeleteMouseClicked

    private void jTabbedPane1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTabbedPane1MouseClicked
       setTableData();
    }//GEN-LAST:event_jTabbedPane1MouseClicked
    
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
            java.util.logging.Logger.getLogger(FormData.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(FormData.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(FormData.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(FormData.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new FormData().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton Bdelete;
    private javax.swing.JButton Bedit;
    private javax.swing.JButton Bfind;
    private javax.swing.JButton Brefresh;
    private javax.swing.JButton Bsave;
    private javax.swing.JButton Bsignout;
    private javax.swing.JButton Buserpreference;
    private javax.swing.JLabel Ljumlah;
    private javax.swing.JLabel Lrealname;
    private javax.swing.JLabel Luid;
    private javax.swing.JLabel Lusername;
    private javax.swing.JTable Tbldata;
    private org.jdesktop.swingx.JXDatePicker Tdate;
    private org.jdesktop.swingx.JXDatePicker Tdatefrom;
    private org.jdesktop.swingx.JXDatePicker Tdateto;
    private javax.swing.JTextArea Tdesc;
    private javax.swing.JTextField Tsearch;
    private javax.swing.JTextField Ttoken;
    private javax.swing.JComboBox Ttype;
    private javax.swing.JTextField Tvalue;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JTabbedPane jTabbedPane1;
    // End of variables declaration//GEN-END:variables

   
}
