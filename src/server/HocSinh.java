package server;

public class HocSinh {
    private int id;
    private String hoTen;
    private String gioiTinh;
    private String queQuan;
    private String lop;

    public HocSinh(int id, String hoTen, String gioiTinh,
                   String queQuan, String lop) {
        this.id = id;
        this.hoTen = hoTen;
        this.gioiTinh = gioiTinh;
        this.queQuan = queQuan;
        this.lop = lop;
    }

    @Override
    public String toString() {
        String thongTin = "";

        thongTin += "ID: " +  id + "\n";
        thongTin += "Họ và tên: " + hoTen + "\n";
        thongTin += "Giới tính: " + gioiTinh + "\n";
        thongTin += "Quê quán: " + queQuan + "\n";
        thongTin += "Lớp: " + lop + "\n";
        thongTin += "-------------------------------";

        return thongTin;
    }
}