package work;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Scanner;

public class HamburgerShop {

	// 商品一覧
	private final static String[] ITEMS = { "ハンバーガー", "ポテト", "コーラ" };

	// データベース接続用
	private static final String DRIVER_NAME = "oracle.jdbc.driver.OracleDriver";
	private static final String JDBC_URL = "jdbc:oracle:thin:@localhost:1521:ORCL";
	private static final String USER_ID = "imuser";
	private static final String USER_PASS = "impass";

	/*
	 * ハンバーガー購入プログラム
	 * ( トランザクション制御 実装済み )
	 */
	public static void main(String[] args) {

		// 標準入力の用意
		Scanner scan = new Scanner(System.in);

		// 売上No
		int salesNo = 0;
		// 売上詳細No
		int subNo = 0;
		// 顧客名
		String name = null;
		// 商品購入数
		int itemNumber = 0;

		// お客氏名の入力
		do {
			System.out.println("お客様のお名前を教えて下さい。");
			name = scan.nextLine();
		} while (name.isEmpty());

		// JDBCドライバのロード
		try {
			Class.forName(DRIVER_NAME);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			System.exit(0);
		}

		Connection con = null;
		try {
			con = DriverManager.getConnection(JDBC_URL, USER_ID, USER_PASS);
			con.setAutoCommit(false);

			// 注文ヘッダの登録
			SalesHeadDao hDao = new SalesHeadDao(con);
			SalesHeadDto hDto = new SalesHeadDto();
			salesNo = hDao.getNextSalesNo();
			hDto.setSalesNo(salesNo);
			hDto.setCustomerName(name);
			hDto.setCreatedAt(new Timestamp(System.currentTimeMillis()));
			hDao.insertHead(hDto);

			System.out.println(name + " 様、ご注文をどうぞ。");

			// 注文ラインの登録
			SalesLineDao lDao = new SalesLineDao(con);

			for (String item : ITEMS) {
				System.out.println(item + " はいくつ注文しますか？");

				itemNumber = Integer.parseInt(scan.nextLine());
				if (itemNumber > 0) {
					SalesLineDto lDto = new SalesLineDto();
					lDto.setSalesNo(salesNo);
					lDto.setSubNo(++subNo);
					lDto.setItem(item);
					lDto.setItemNumber(itemNumber);
					lDto.setCreatedAt(new Timestamp(System.currentTimeMillis()));
					lDao.insertLine(lDto);
				}
			}
			con.commit();
			System.out.println(name + " 様、ご注文ありがとうございました。");

		} catch (NumberFormatException ne) {
			ne.printStackTrace();
			try {
				con.rollback();
			} catch (SQLException e) {
				e.printStackTrace();
			}

		} catch (SQLException se) {
			se.printStackTrace();
			try {
				con.rollback();
			} catch (SQLException e) {
				e.printStackTrace();
			}

		} finally {
			try {
				if (con != null) {
					con.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
}
