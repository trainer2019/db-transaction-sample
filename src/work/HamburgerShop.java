package work;

import java.sql.Timestamp;
import java.util.Scanner;

public class HamburgerShop {

	// 商品一覧
	private final static String[] ITEMS = { "ハンバーガー", "ポテト", "コーラ" };

	public static void main(String[] args) {

		// 売上No
		int salesNo = 0;
		// 売上詳細No
		int subNo = 0;
		// 顧客名
		String name = null;
		// 商品購入数
		int itemNumber = 0;

		// 標準入力の用意
		Scanner scan = new Scanner(System.in);

		// 注文ヘッダの登録
		System.out.println("お客様のお名前を教えて下さい。");
		name = scan.nextLine();
		if (name.isEmpty()) {
			System.out.println("不正な値が入力されました。");
			System.exit(0);
		}
		

		SalesHeadDao hDao = new SalesHeadDao();
		salesNo = hDao.getNextSalesNo();
		
		SalesHeadDto hDto = new SalesHeadDto();
		hDto.setSalesNo(salesNo);
		hDto.setCustomerName(name);
		hDto.setCreatedAt(new Timestamp(System.currentTimeMillis()));
		hDao.insertHead(hDto);

		// 注文ラインの登録
		System.out.println(name + " 様、ご注文をどうぞ。");
		SalesLineDao lDao = new SalesLineDao();
		for (String item : ITEMS) {
			itemNumber = 0;

			System.out.println(item + " はいくつ注文しますか？");
			itemNumber = Integer.parseInt(scan.nextLine());

			// ラインの登録
			if (itemNumber > 0) {
				SalesLineDto lDto = new SalesLineDto();
				lDto.setSalesNo(salesNo);
				lDto.setSubNo(++subNo);
				lDto.setItem(item);
				lDto.setItemNumber(itemNumber);
				lDao.insertLine(lDto);
			}
		}

		System.out.println(name + " 様、ご注文ありがとうございました。");

	}

}
