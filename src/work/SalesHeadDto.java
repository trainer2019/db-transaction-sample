package work;

import java.sql.Timestamp;

public class SalesHeadDto {
	// 売上No
	private int salesNo;
	// お客名
	private String customerName;
	// 登録日時
	private Timestamp createdAt;

	public int getSalesNo() {
		return salesNo;
	}

	public void setSalesNo(int salesNo) {
		this.salesNo = salesNo;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public Timestamp getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Timestamp createdAt) {
		this.createdAt = createdAt;
	}

}
