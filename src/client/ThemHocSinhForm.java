package client;

import server.DatabaseUtil;

import javax.swing.*;
import java.awt.*;

public class ThemHocSinhForm extends JFrame {
    JPanel centralPanel;
    JTextField hoTenField, gioiTinhField, queQuanField, lopField;
    JButton addButton;

    DatabaseUtil util;

    ThemHocSinhForm() {
        super("Thêm học sinh");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(400, 400);
        setResizable(false);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        setNegativeSpace(50, 50);

        centralPanel = new JPanel(new GridLayout(5, 2, 25, 25));

        hoTenField = new JTextField();
        gioiTinhField = new JTextField();
        queQuanField = new JTextField();
        lopField = new JTextField();

        addButton = new JButton("Thêm");

        util = new DatabaseUtil();

        addButton.setBackground(Color.lightGray);
        addButton.setFocusable(false);
        addButton.addActionListener(e -> {
            String hoTen = hoTenField.getText();
            String gioiTinh = gioiTinhField.getText();
            String queQuan = queQuanField.getText();
            String lop = lopField.getText();

            if (hoTen.isEmpty() || gioiTinh.isEmpty() || queQuan.isEmpty() || lop.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Tất cả các trường đều bắt buộc",
                        "Thêm học sinh", JOptionPane.ERROR_MESSAGE);
            } else {
                util.themHocSinh(hoTen, gioiTinh, queQuan, lop);
                JOptionPane.showMessageDialog(null, "Thêm học sinh thành Công",
                        "Thêm học sinh", JOptionPane.INFORMATION_MESSAGE);
                this.dispose();
            }
        });

        centralPanel.add(new JLabel("Họ tên:"));
        centralPanel.add(hoTenField);
        centralPanel.add(new JLabel("Giới tính:"));
        centralPanel.add(gioiTinhField);
        centralPanel.add(new JLabel("Quê quán:"));
        centralPanel.add(queQuanField);
        centralPanel.add(new JLabel("Lớp:"));
        centralPanel.add(lopField);
        centralPanel.add(new JPanel());
        centralPanel.add(addButton);

        add(centralPanel, BorderLayout.CENTER);
        setVisible(true);
    }

    public void setNegativeSpace(int horizontalSize, int verticalSize) {
        JPanel[] negativeSpace = new JPanel[4];
        String[] negativeSpaceAreas = {
                BorderLayout.EAST,
                BorderLayout.WEST,
                BorderLayout.SOUTH,
                BorderLayout.NORTH
        };

        for (int i = 0; i < 4; i++) {
            negativeSpace[i] = new JPanel();
            negativeSpace[i].setPreferredSize(new Dimension(horizontalSize, verticalSize));

            add(negativeSpace[i], negativeSpaceAreas[i]);
        }
    }
}