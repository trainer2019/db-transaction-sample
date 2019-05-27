package work;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SalesHeadDao {

	// データベース接続に必要なデータ
	private static final String DRIVER_NAME = "oracle.jdbc.driver.OracleDriver";
	private static final String JDBC_URL = "jdbc:oracle:thin:@localhost:1521:ORCL";
	private static final String USER_ID = "imuser";
	private static final String USER_PASS = "impass";

	public void insertHead(SalesHeadDto head) {

		try {
			Class.forName(DRIVER_NAME);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}

		Connection con = null;
		PreparedStatement ps = null;

		try {
			con = DriverManager.getConnection(JDBC_URL, USER_ID, USER_PASS);

			StringBuilder builder = new StringBuilder();
			builder.append("INSERT INTO sales_head ( ");
			builder.append("    sales_no,            ");
			builder.append("    customer_name,       ");
			builder.append("    created_at           ");
			builder.append(") VALUES (               ");
			builder.append("    ?,                   ");
			builder.append("    ?,                   ");
			builder.append("    ?                    ");
			builder.append(")                        ");

			ps = con.prepareStatement(builder.toString());
			ps.setInt(1, head.getSalesNo());
			ps.setString(2, head.getCustomerName());
			ps.setTimestamp(3, head.getCreatedAt());

			ps.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (ps != null) {
					ps.close();
				}
				if (con != null) {
					con.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	public int getNextSalesNo() {

		try {
			Class.forName(DRIVER_NAME);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}

		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		int nextNo = 0;
		try {
			con = DriverManager.getConnection(JDBC_URL, USER_ID, USER_PASS);

			StringBuilder builder = new StringBuilder();
			builder.append("SELECT                    ");
			builder.append("  MAX(sales_no) AS max_no ");
			builder.append("FROM                      ");
			builder.append("  sales_head              ");

			ps = con.prepareStatement(builder.toString());
			rs = ps.executeQuery();

			if (rs.next()) {
				nextNo = rs.getInt("max_no") + 1;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (ps != null) {
					ps.close();
				}
				if (rs != null) {
					rs.close();
				}
				if (con != null) {
					con.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return nextNo;
	}

}
