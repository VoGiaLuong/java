package thucHanh8;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;

public class MyConnection {
	public static void main(String[] args) {
		try {
			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
			Connection con = DriverManager.getConnection("jdbc:sqlserver://localhost:1433;databaseName=THUCHANH8;user=sa;password=12345;encrypt=false;");
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT ma_sinh_vien, ho_ten, dia_chi FROM SV");
			while(rs.next()) {
				System.out.print("ID: " + rs.getString("ma_sinh_vien") + "\t");
				System.out.print("Họ tên: " + rs.getString("ho_ten") + "\t");
				System.out.print("Địa chỉ: " + rs.getString("dia_chi") + "\n");
			}
			con.close();
			rs.close();
		} catch (ClassNotFoundException | SQLException e) {
			System.out.println("Error " + e);
		}
	}

}
