package com.asahotak;

import javax.swing.JTextField;
import javax.swing.JOptionPane;
import java.sql.SQLException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Random;
import com.zaxxer.hikari.HikariDataSource;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class AsahOtak extends javax.swing.JFrame {
    private String kata;
    private List<JTextField> textFields; 

    public AsahOtak() {
        initComponents();
        ambilKataAcak();
        buatJawabanFields(); 
    }

    @SuppressWarnings("unchecked")
    private void initComponents() {
        jPanel1 = new javax.swing.JPanel();
        JPertanyaan = new javax.swing.JTextField();
        jLabelPertanyaan = new javax.swing.JLabel();
        jButtonUlangi = new javax.swing.JButton();
        jButtonSimpan = new javax.swing.JButton();
        jLabelJawaban = new javax.swing.JLabel();
        jawabanPanel = new javax.swing.JPanel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabelPertanyaan.setText("Pertanyaan:");

        jButtonUlangi.setText("Ulangi");
        jButtonUlangi.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                ulangiPermainan();
            }
        });

        jButtonSimpan.setText("Simpan Point");
        jButtonSimpan.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                simpanPoint();
            }
        });

        jLabelJawaban.setText("Masukkan Jawaban:");

        jawabanPanel.setLayout(new GridLayout(1, 5, 10, 10)); 

        GroupLayout jPanel1Layout = new GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                .addGroup(jPanel1Layout.createSequentialGroup()
                    .addContainerGap()
                    .addGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addComponent(JPertanyaan)
                        .addGroup(jPanel1Layout.createSequentialGroup()
                            .addComponent(jButtonUlangi)
                            .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, 236, Short.MAX_VALUE)
                            .addComponent(jButtonSimpan))
                        .addGroup(jPanel1Layout.createSequentialGroup()
                            .addGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                .addComponent(jLabelPertanyaan)
                                .addComponent(jLabelJawaban)
                                .addComponent(jawabanPanel, GroupLayout.PREFERRED_SIZE, 456, GroupLayout.PREFERRED_SIZE))
                            .addGap(0, 0, Short.MAX_VALUE)))
                    .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                .addGroup(jPanel1Layout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(jLabelPertanyaan)
                    .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                    .addComponent(JPertanyaan, GroupLayout.PREFERRED_SIZE, 38, GroupLayout.PREFERRED_SIZE)
                    .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                    .addComponent(jLabelJawaban)
                    .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                    .addComponent(jawabanPanel, GroupLayout.PREFERRED_SIZE, 100, GroupLayout.PREFERRED_SIZE)
                    .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, 20, Short.MAX_VALUE)
                    .addGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                        .addComponent(jButtonUlangi)
                        .addComponent(jButtonSimpan))
                    .addContainerGap())
        );
        add(jPanel1);
        pack();
    }

    private void ambilKataAcak() {
        // mengambil data acak dari database
        String query = "SELECT * FROM master_kata ORDER BY RAND() LIMIT 1";
        try (Connection connection = DbConnection.getDataSource().getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {

            if (resultSet.next()) {
                kata = resultSet.getString("kata");
                JPertanyaan.setText(resultSet.getString("clue")); 
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void buatJawabanFields() {
        jawabanPanel.removeAll();
        textFields = new ArrayList<>();

        for (int i = 0; i < kata.length(); i++) {
            JTextField textField = new JTextField(1);
            if (i == 2 || i == 6) {
                // set huruf ke-3 dan ke-7 sebagai clue
                textField.setText(String.valueOf(kata.charAt(i)));
                textField.setEditable(false); // tdk bisa diubah
            }
            textFields.add(textField);
            jawabanPanel.add(textField); 
        }

        jawabanPanel.revalidate();
        jawabanPanel.repaint();
    }

    private void ulangiPermainan() {
        ambilKataAcak(); 
        buatJawabanFields(); 
    }

    private void simpanPoint() {
        int score = hitungSkor(); 
        String message = "Poin yang Anda dapat adalah " + score;
        JOptionPane.showMessageDialog(this, message);

        String namaUser = JOptionPane.showInputDialog(this, "Masukkan Nama Anda:");
        if (namaUser != null && !namaUser.isEmpty()) {
            // simpan skor ke db
            simpanSkorKeDatabase(namaUser, score);
            ulangiPermainan();
        }
    }

    private int hitungSkor() {
        int skor = 0;
        for (int i = 0; i < kata.length(); i++) {
            if (i == 2 || i == 6) continue;
            String jawaban = textFields.get(i).getText();
            if (jawaban.equalsIgnoreCase(String.valueOf(kata.charAt(i)))) {
                skor += 10; 
            } else {
                skor -= 2;
            }
        }
        return skor;
    }

    private void simpanSkorKeDatabase(String namaUser, int score) {
        String query = "INSERT INTO point_game (nama_user, total_point) VALUES (?, ?)";
        try (Connection connection = DbConnection.getDataSource().getConnection();
             PreparedStatement ps = connection.prepareStatement(query)) {

            ps.setString(1, namaUser);
            ps.setInt(2, score);
            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(() -> new AsahOtak().setVisible(true));
    }

    private javax.swing.JTextField JPertanyaan;
    private javax.swing.JButton jButtonSimpan;
    private javax.swing.JButton jButtonUlangi;
    private javax.swing.JLabel jLabelJawaban;
    private javax.swing.JLabel jLabelPertanyaan;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jawabanPanel;
}







































//ROCHMAN ROMADZON 2024