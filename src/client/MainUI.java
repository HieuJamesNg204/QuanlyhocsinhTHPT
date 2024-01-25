package client;

import server.DatabaseUtil;
import server.HocSinh;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class MainUI extends JFrame {
    private JTextField idTextField, gioiTinhTextField, queQuanTextField, lopTextField;
    private JTextArea studentListTextArea;
    DatabaseUtil util;

    public MainUI() {
        super("Quản lý học sinh");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1000, 700);
        setLocationRelativeTo(null);

        util = new DatabaseUtil();

        // Create components
        idTextField = new JTextField();
        gioiTinhTextField = new JTextField();
        queQuanTextField = new JTextField();
        lopTextField = new JTextField();
        studentListTextArea = new JTextArea();

        JScrollPane scrollPane = new JScrollPane(studentListTextArea);

        JButton showButton = new JButton("Hiển thị");
        JButton listButton = new JButton("Liệt kê");
        JButton addButton = new JButton("Thêm học sinh");
        JButton removeButton = new JButton("Xóa học sinh");
        JButton updateButton = new JButton("Cập nhật thông tin học sinh");

        setJButtonProperties(showButton, e -> {
            try {
                studentListTextArea.setText("");
                int id = Integer.parseInt(idTextField.getText());
                if (id > 0) {
                    HocSinh hocSinh = util.truyXuatHocSinhTheoId(id);
                    studentListTextArea.append(hocSinh.toString() + "\n");
                } else {
                    JOptionPane.showMessageDialog(null, "ID phải là số nguyên dương",
                            "Quản lý học sinh", JOptionPane.ERROR_MESSAGE);
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null,
                        "Lỗi hiển thị thông tin học sinh: " + ex.getMessage(), "Quản lý học sinh",
                        JOptionPane.ERROR_MESSAGE);
            }
        });

        setJButtonProperties(listButton, e -> {
            studentListTextArea.setText("");
            String gioiTinh = gioiTinhTextField.getText();
            String queQuan = queQuanTextField.getText();
            String lop = lopTextField.getText();

            ArrayList<HocSinh> danhSach = util.lietKeHocSinhTheoCacTruong(gioiTinh, queQuan, lop);

            for (HocSinh hocSinh: danhSach) {
                studentListTextArea.append(hocSinh.toString() + "\n");
            }
        });

        setJButtonProperties(addButton, e -> {
            new ThemHocSinhForm();
        });

        setJButtonProperties(removeButton, e -> {
            try {
                String idStr = JOptionPane.showInputDialog("Nhập mã số học sinh muốn xóa:");
                int id = Integer.parseInt(idStr);

                if (id > 0) {
                    HocSinh hocSinh = util.truyXuatHocSinhTheoId(id);

                    if (hocSinh == null) {
                        JOptionPane.showMessageDialog(null, "Không tìm thấy học sinh",
                                "Quản lý học sinh", JOptionPane.ERROR_MESSAGE);
                    } else {
                        util.xoaHocSinhTheoId(id);
                        JOptionPane.showMessageDialog(null, "Xóa học sinh thành công",
                                "Quản lý học sinh", JOptionPane.INFORMATION_MESSAGE);
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "ID phải là số nguyên dương",
                            "Quản lý học sinh", JOptionPane.ERROR_MESSAGE);
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, "Lỗi khi xóa học sinh: " + ex.getMessage(),
                        "Quản lý học sinh", JOptionPane.ERROR_MESSAGE);
            }
        });

        setJButtonProperties(updateButton, e -> {
            new CapNhatThongTinForm();
        });

        // Set layout
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);

        // Row 1
        gbc.gridy = 0;

        gbc.gridx = 0;
        add(new JLabel("Nhập mã số học sinh:"), gbc);

        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1;
        add(idTextField, gbc);

        gbc.gridx = 2;
        gbc.fill = GridBagConstraints.NONE;
        gbc.weightx = 0;
        add(showButton, gbc);

        // Row 2
        gbc.gridy = 1;

        gbc.gridx = 0;
        add(new JLabel("Liệt kê học sinh theo trường thông tin (Không nhập sẽ hiển thị tất cả):"), gbc);

        // Row 3
        gbc.gridy = 2;

        gbc.fill = GridBagConstraints.NONE;
        gbc.weightx = 0;
        add(new JLabel("Giới tính:"), gbc);

        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1;
        add(gioiTinhTextField, gbc);

        // Row 4
        gbc.gridy = 3;

        gbc.gridx = 0;
        gbc.fill = GridBagConstraints.NONE;
        gbc.weightx = 0;
        add(new JLabel("Quê Quán:"), gbc);

        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1;
        add(queQuanTextField, gbc);

        // Row 5
        gbc.gridy = 4;

        gbc.gridx = 0;
        gbc.fill = GridBagConstraints.NONE;
        gbc.weightx = 0;
        add(new JLabel("Lớp:"), gbc);

        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1;
        add(lopTextField, gbc);

        // Row 6
        gbc.gridy = 5;

        gbc.gridx = 0;
        gbc.fill = GridBagConstraints.NONE;
        gbc.weightx = 0;
        add(listButton, gbc);

        // Row 7
        gbc.gridy = 6;

        gbc.gridx = 0;
        add(addButton, gbc);

        gbc.gridx = 1;
        add(removeButton, gbc);

        gbc.gridx = 2;
        add(updateButton, gbc);

        // Row 8: Student List TextArea with Scrollbars
        studentListTextArea.setEditable(false);

        gbc.gridy = 7;

        gbc.gridx = 0;
        gbc.gridwidth = 3;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weighty = 1;
        add(scrollPane, gbc);

        setVisible(true);
    }

    public void setJButtonProperties(JButton button, ActionListener actionListener) {
        button.setBackground(Color.lightGray);
        button.setFocusable(false);
        button.addActionListener(actionListener);
    }
}