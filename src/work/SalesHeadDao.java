package work;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SalesHeadDao {

	// コネクション
	private Connection con = null;

	// コンストラクタでコネクションを受け取る
	public SalesHeadDao(Connection con) {
		this.con = con;
	}

	/**
	 * 売上ヘッダデータを保存
	 *
	 * @param SalesHeadDto head
	 */
	public void insertHead(SalesHeadDto head) throws SQLException {

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

		PreparedStatement ps = con.prepareStatement(builder.toString());
		ps.setInt(1, head.getSalesNo());
		ps.setString(2, head.getCustomerName());
		ps.setTimestamp(3, head.getCreatedAt());
		ps.executeUpdate();

		if (ps != null) {
			ps.close();
		}

	}

	/**
	 * 次の売上Noを発行する
	 *
	 * @return 次の売上No
	 */
	public int getNextSalesNo() throws SQLException {

		int nextNo = 0;

		StringBuilder builder = new StringBuilder();
		builder.append("SELECT                    ");
		builder.append("  MAX(sales_no) AS max_no ");
		builder.append("FROM                      ");
		builder.append("  sales_head              ");

		PreparedStatement ps = con.prepareStatement(builder.toString());
		ResultSet rs = ps.executeQuery();
		if (rs.next()) {
			nextNo = rs.getInt("max_no");
			nextNo += 1;
		}

		if (ps != null) {
			ps.close();
		}
		if (rs != null) {
			rs.close();
		}

		return nextNo;
	}

}
