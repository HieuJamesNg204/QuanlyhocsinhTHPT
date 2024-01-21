package server;

import javax.swing.*;
import java.sql.*;
import java.util.ArrayList;

public class DatabaseUtil {
    private final String JDBC_URL = "jdbc:postgresql://localhost:5432/hoc_sinh_database";
    private final String USER = "postgres";
    private final String PASSWORD = "ViDNm04-003065";

    public HocSinh truyXuatHocSinhTheoId(int id) {
        HocSinh hocSinh = null;

        try (Connection connection = DriverManager.getConnection(JDBC_URL, USER, PASSWORD)) {
            if (connection != null) {
                String sqlQuery = "SELECT * FROM hoc_sinh WHERE id=?;";
                PreparedStatement statement = connection.prepareStatement(sqlQuery);
                statement.setInt(1, id);

                ResultSet resultSet = statement.executeQuery();

                if (resultSet.next()) {
                    int idHocSinh = resultSet.getInt("id");
                    String hoTen = resultSet.getString("ho_ten");
                    String gioiTinh = resultSet.getString("gioi_tinh");
                    String queQuan = resultSet.getString("que_quan");
                    String lop = resultSet.getString("lop");

                    hocSinh = new HocSinh(idHocSinh, hoTen, gioiTinh, queQuan, lop);
                }

                resultSet.close();
                statement.close();
            } else {
                JOptionPane.showMessageDialog(null, "Không thể kết nối tới server",
                        "Quản lý học sinh", JOptionPane.ERROR_MESSAGE);
            }
        } catch (SQLException e) {
            String message = "Đã xảy ra lỗi server: " + e.getMessage();
            JOptionPane.showMessageDialog(null, message,
                    "Quản lý học sinh", JOptionPane.ERROR_MESSAGE);
        }

        return hocSinh;
    }

    public ArrayList<HocSinh> lietKeHocSinhTheoCacTruong(String gioiTinh, String queQuan, String lop) {
        ArrayList<HocSinh> danhSach = new ArrayList<>();

        try (Connection connection = DriverManager.getConnection(JDBC_URL, USER, PASSWORD)) {
            if (connection != null) {
                String sqlQuery = "SELECT * FROM hoc_sinh";

                PreparedStatement statement;
                if (gioiTinh.isEmpty() && queQuan.isEmpty() && lop.isEmpty()) {
                    sqlQuery += " ORDER BY id;";
                    statement = connection.prepareStatement(sqlQuery);
                } else {
                    sqlQuery += " WHERE ";

                    if (!gioiTinh.isEmpty()) {
                        sqlQuery += "gioi_tinh=?" + ((!queQuan.isEmpty() || !lop.isEmpty())? " AND " : " ");
                    }

                    if (!queQuan.isEmpty()) {
                        sqlQuery += "que_quan=?" + ((!lop.isEmpty())? " AND " : " ");
                    }

                    if (!lop.isEmpty()) {
                        sqlQuery += "lop=? ";
                    }

                    sqlQuery += "ORDER BY id;";

                    statement = connection.prepareStatement(sqlQuery);

                    int paramIndex = 1;
                    if (!gioiTinh.isEmpty()) {
                        statement.setString(paramIndex++, gioiTinh);
                    }

                    if (!queQuan.isEmpty()) {
                        statement.setString(paramIndex++, queQuan);
                    }

                    if (!lop.isEmpty()) {
                        statement.setString(paramIndex, lop);
                    }
                }

                ResultSet resultSet = statement.executeQuery();

                while (resultSet.next()) {
                    int id = resultSet.getInt("id");
                    String hoTen = resultSet.getString("ho_ten");
                    String gioiTinhHocSinh = resultSet.getString("gioi_tinh");
                    String que = resultSet.getString("que_quan");
                    String lopHoc = resultSet.getString("lop");

                    danhSach.add(new HocSinh(id, hoTen, gioiTinhHocSinh, que, lopHoc));
                }

                resultSet.close();
                statement.close();
            } else {
                JOptionPane.showMessageDialog(null, "Không thể kết nối tới server",
                        "Quản lý học sinh", JOptionPane.ERROR_MESSAGE);
            }
        } catch (SQLException e) {
            String message = "Đã xảy ra lỗi server: " + e.getMessage();
            JOptionPane.showMessageDialog(null, message,
                    "Quản lý học sinh", JOptionPane.ERROR_MESSAGE);
        }

        return danhSach;
    }

    public void themHocSinh(String hoTen, String gioiTinh, String queQuan, String lop) {
        try (Connection connection = DriverManager.getConnection(JDBC_URL, USER, PASSWORD)) {
            if (connection != null) {
                String sqlQuery = "INSERT INTO hoc_sinh (ho_ten, gioi_tinh, que_quan, lop) VALUES (?, ?, ?, ?);";
                PreparedStatement statement = connection.prepareStatement(sqlQuery);

                statement.setString(1, hoTen);
                statement.setString(2, gioiTinh);
                statement.setString(3, queQuan);
                statement.setString(4, lop);

                statement.executeUpdate();

                statement.close();
            } else {
                JOptionPane.showMessageDialog(null, "Không thể kết nối tới server",
                        "Quản lý học sinh", JOptionPane.ERROR_MESSAGE);
            }
        } catch (SQLException e) {
            String message = "Đã xảy ra lỗi server: " + e.getMessage();
            JOptionPane.showMessageDialog(null, message,
                    "Quản lý học sinh", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void xoaHocSinhTheoId(int id) {
        try (Connection connection = DriverManager.getConnection(JDBC_URL, USER, PASSWORD)) {
            if (connection != null) {
                String sqlQuery = "DELETE FROM hoc_sinh WHERE id=?;";
                PreparedStatement statement = connection.prepareStatement(sqlQuery);
                statement.setInt(1, id);

                statement.executeUpdate();

                statement.close();
            } else {
                JOptionPane.showMessageDialog(null, "Không thể kết nối tới server",
                        "Quản lý học sinh", JOptionPane.ERROR_MESSAGE);
            }
        } catch (SQLException e) {
            String message = "Đã xảy ra lỗi server: " + e.getMessage();
            JOptionPane.showMessageDialog(null, message,
                    "Quản lý học sinh", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void capNhatHocSinhTheoId(int id, String lopMoi) {
        try (Connection connection = DriverManager.getConnection(JDBC_URL, USER, PASSWORD)) {
            if (connection != null) {
                Statement statement = connection.createStatement();

                String sqlQuery = "UPDATE hoc_sinh SET lop='" + lopMoi + "' WHERE id=" + id + ";";
                statement.executeUpdate(sqlQuery);

                statement.close();
            } else {
                JOptionPane.showMessageDialog(null, "Không thể kết nối tới server",
                        "Quản lý học sinh", JOptionPane.ERROR_MESSAGE);
            }
        } catch (SQLException e) {
            String message = "Đã xảy ra lỗi server: " + e.getMessage();
            JOptionPane.showMessageDialog(null, message,
                    "Quản lý học sinh", JOptionPane.ERROR_MESSAGE);
        }
    }
}