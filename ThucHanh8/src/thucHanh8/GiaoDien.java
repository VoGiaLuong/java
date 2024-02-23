package thucHanh8;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

public class GiaoDien extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private JTextField textFieldMaSV;
    private JTextField textFieldTenSV;
    private JTextField textFieldDiaChi;
    private JTable table;
    private DefaultTableModel model;
    private Connection con;

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    GiaoDien frame = new GiaoDien();
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public GiaoDien() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 784, 427);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

        setContentPane(contentPane);
        contentPane.setLayout(null);

        JLabel lblNewLabel = new JLabel("Mã SV");
        lblNewLabel.setBounds(10, 10, 67, 28);
        contentPane.add(lblNewLabel);

        JLabel lblNewLabel_1 = new JLabel("Họ Tên:");
        lblNewLabel_1.setBounds(10, 48, 67, 28);
        contentPane.add(lblNewLabel_1);

        JLabel lblNewLabel_1_1 = new JLabel("Địa Chỉ:");
        lblNewLabel_1_1.setBounds(10, 86, 67, 28);
        contentPane.add(lblNewLabel_1_1);

        textFieldMaSV = new JTextField();
        textFieldMaSV.setBounds(87, 15, 134, 19);
        contentPane.add(textFieldMaSV);
        textFieldMaSV.setColumns(10);

        textFieldTenSV = new JTextField();
        textFieldTenSV.setColumns(10);
        textFieldTenSV.setBounds(87, 53, 134, 19);
        contentPane.add(textFieldTenSV);

        textFieldDiaChi = new JTextField();
        textFieldDiaChi.setColumns(10);
        textFieldDiaChi.setBounds(87, 91, 134, 19);
        contentPane.add(textFieldDiaChi);

        JButton btnThem = new JButton("Thêm");
        btnThem.setBounds(10, 151, 85, 21);
        contentPane.add(btnThem);

        JButton btnXoa = new JButton("Xóa");
        btnXoa.setBounds(125, 151, 85, 21);
        contentPane.add(btnXoa);

        JButton btnSua = new JButton("Sửa");
        btnSua.setBounds(10, 197, 85, 21);
        contentPane.add(btnSua);

        JButton btnTim = new JButton("Tìm");
        btnTim.setBounds(125, 197, 85, 21);
        contentPane.add(btnTim);

        JButton btnClean = new JButton("Clean");
        btnClean.setBounds(10, 250, 85, 21);
        contentPane.add(btnClean);

        JButton btnThoat = new JButton("Thoát");
        btnThoat.setBounds(125, 250, 85, 21);
        contentPane.add(btnThoat);

        // Khởi tạo bảng và model
        model = new DefaultTableModel();
        table = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBounds(250, 10, 500, 350);
        contentPane.add(scrollPane);

        // Kết nối cơ sở dữ liệu và load dữ liệu vào bảng
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            con = DriverManager.getConnection("jdbc:sqlserver://localhost:1433;databaseName=THUCHANH8;user=sa;password=12345;encrypt=false;");
            loadData();
        } catch (ClassNotFoundException | SQLException ex) {
            ex.printStackTrace();
        }

        // Đăng ký sự kiện cho nút Thêm
        btnThem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                themSinhVien();
            }
        });

        // Đăng ký sự kiện cho nút Xóa
        btnXoa.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                xoaSinhVien();
            }
        });

        // Đăng ký sự kiện cho nút Sửa
        btnSua.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                suaSinhVien();
            }
        });

        // Đăng ký sự kiện cho nút Tìm
        btnTim.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                timSinhVien();
            }
        });

        // Đăng ký sự kiện cho nút Clean
        btnClean.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                cleanFields();
            }
        });

        // Đăng ký sự kiện cho nút Thoát
        btnThoat.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                thoatChuongTrinh();
            }
        });
    }

    private void loadData() {
        try {
            model.setColumnIdentifiers(new String[]{"Mã sinh viên", "Họ và tên", "Địa chỉ"});
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT ma_sinh_vien, ho_ten, dia_chi FROM SV");
            while (rs.next()) {
                String maSV = rs.getString("ma_sinh_vien");
                String tenSV = rs.getString("ho_ten");
                String diaChi = rs.getString("dia_chi");
                model.addRow(new Object[]{maSV, tenSV, diaChi});
            }
            stmt.close();
            rs.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    private void themSinhVien() {
        String maSV = textFieldMaSV.getText();
        String tenSV = textFieldTenSV.getText();
        String diaChi = textFieldDiaChi.getText();
        try {
            PreparedStatement ps = con.prepareStatement("INSERT INTO SV (ma_sinh_vien, ho_ten, dia_chi) VALUES (?, ?, ?)");
            ps.setString(1, maSV);
            ps.setString(2, tenSV);
            ps.setString(3, diaChi);
            int rowsAffected = ps.executeUpdate();
            if (rowsAffected > 0) {
                loadData();
                cleanFields();
            }
            ps.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    private void xoaSinhVien() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow != -1) {
            String maSV = (String) table.getValueAt(selectedRow, 0);
            try {
                PreparedStatement ps = con.prepareStatement("DELETE FROM SV WHERE ma_sinh_vien = ?");
                ps.setString(1, maSV);
                int rowsAffected = ps.executeUpdate();
                if (rowsAffected > 0) {
                    loadData();
                }
                ps.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }

    private void suaSinhVien() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow != -1) {
            String maSV = (String) table.getValueAt(selectedRow, 0);
            String tenSV = textFieldTenSV.getText();
            String diaChi = textFieldDiaChi.getText();
            try {
                PreparedStatement ps = con.prepareStatement("UPDATE SV SET ho_ten = ?, dia_chi = ? WHERE ma_sinh_vien = ?");
                ps.setString(1, tenSV);
                ps.setString(2, diaChi);
                ps.setString(3, maSV);
                int rowsAffected = ps.executeUpdate();
                if (rowsAffected > 0) {
                    loadData();
                }
                ps.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }

    private void timSinhVien() {
        String maSV = textFieldMaSV.getText();
        try {
            PreparedStatement ps = con.prepareStatement("SELECT * FROM SV WHERE ma_sinh_vien = ?");
            ps.setString(1, maSV);
            ResultSet rs = ps.executeQuery();
            model.setRowCount(0); // Xóa dữ liệu cũ trong bảng
            while (rs.next()) {
                String maSinhVien = rs.getString("ma_sinh_vien");
                String hoTen = rs.getString("ho_ten");
                String diaChi = rs.getString("dia_chi");
                model.addRow(new Object[]{maSinhVien, hoTen, diaChi});
            }
            rs.close();
            ps.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    private void cleanFields() {
        textFieldMaSV.setText("");
        textFieldTenSV.setText("");
        textFieldDiaChi.setText("");
    }

    private void thoatChuongTrinh() {
        try {
            con.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        System.exit(0);
    }
}


