package work;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;

public class SalesLineDao {
	// データベース接続に必要なデータ
	private static final String DRIVER_NAME = "oracle.jdbc.driver.OracleDriver";
	private static final String JDBC_URL = "jdbc:oracle:thin:@localhost:1521:ORCL";
	private static final String USER_ID = "imuser";
	private static final String USER_PASS = "impass";

	/**
	 * 注文ラインをDBに保存する
	 *
	 * @param line
	 */
	public void insertLine(SalesLineDto line) {

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
			builder.append("INSERT INTO sales_line ( ");
			builder.append("    sales_no,            ");
			builder.append("    sub_no,              ");
			builder.append("    item,                ");
			builder.append("    item_number,         ");
			builder.append("    created_at           ");
			builder.append(") VALUES (               ");
			builder.append("    ?,                   ");
			builder.append("    ?,                   ");
			builder.append("    ?,                   ");
			builder.append("    ?,                   ");
			builder.append("    ?                    ");
			builder.append(")                        ");

			ps = con.prepareStatement(builder.toString());
			ps.setInt(1, line.getSalesNo());
			ps.setInt(2, line.getSubNo());
			ps.setString(3, line.getItem());
			ps.setInt(4, line.getItemNumber());
			ps.setTimestamp(5, new Timestamp(System.currentTimeMillis()));

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
}
