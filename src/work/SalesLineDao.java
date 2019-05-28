package work;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class SalesLineDao {

	// コネクション
	private Connection con = null;

	// コンストラクタでコネクションを受け取る
	public SalesLineDao(Connection con) {
		this.con = con;
	}

	/**
	 * 注文ラインをDBに保存する
	 *
	 * @param line
	 */
	public void insertLine(SalesLineDto line) throws SQLException {

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

		PreparedStatement ps = this.con.prepareStatement(builder.toString());
		ps.setInt(1, line.getSalesNo());
		ps.setInt(2, line.getSubNo());
		ps.setString(3, line.getItem());
		ps.setInt(4, line.getItemNumber());
		ps.setTimestamp(5, line.getCreatedAt());
		ps.executeUpdate();

		if (ps != null) {
			ps.close();
		}
	}
}
