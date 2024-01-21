package client;

import server.DatabaseUtil;
import server.HocSinh;

import javax.swing.*;
import java.awt.*;

public class CapNhatThongTinForm extends JFrame {
    JPanel centralPanel;
    JTextField idField, lopField;
    JButton updateButton;

    DatabaseUtil util;

    CapNhatThongTinForm() {
        super("Cập nhật thông tin học sinh");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(400, 300);
        setResizable(false);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        setNegativeSpace(50, 50);

        centralPanel = new JPanel(new GridLayout(3, 2, 25, 25));

        idField = new JTextField();
        lopField = new JTextField();

        updateButton = new JButton("Cập nhật");

        util = new DatabaseUtil();

        updateButton.setBackground(Color.lightGray);
        updateButton.setFocusable(false);
        updateButton.addActionListener(e -> {
            try {
                int id = Integer.parseInt(idField.getText());
                String lopMoi = lopField.getText();

                if (id > 0) {
                    HocSinh hocSinh = util.truyXuatHocSinhTheoId(id);

                    if (hocSinh == null) {
                        JOptionPane.showMessageDialog(null, "Không tìm thấy học sinh",
                                "Quản lý học sinh", JOptionPane.ERROR_MESSAGE);
                    } else {
                        if (lopMoi.isEmpty()) {
                            JOptionPane.showMessageDialog(null, "Vui lòng nhập lớp mới",
                                    "Quản lý học sinh", JOptionPane.ERROR_MESSAGE);
                        } else {
                            util.capNhatHocSinhTheoId(id, lopMoi);
                            JOptionPane.showMessageDialog(null, "Cập nhật thông tin thành công",
                                    "Cập nhật thông tin học sinh", JOptionPane.INFORMATION_MESSAGE);
                            this.dispose();
                        }
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "ID phải là số nguyên dương",
                            "Quản lý học sinh", JOptionPane.ERROR_MESSAGE);
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null,
                        "Lỗi khi cập nhật thông tin học sinh: " + ex.getMessage(),
                        "Cập nhật thông tin học sinh", JOptionPane.ERROR_MESSAGE);
            }
        });

        centralPanel.add(new JLabel("Mã số học sinh:"));
        centralPanel.add(idField);
        centralPanel.add(new JLabel("Lớp mới:"));
        centralPanel.add(lopField);
        centralPanel.add(new JPanel());
        centralPanel.add(updateButton);

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