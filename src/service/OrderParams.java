package service;

/**
 * @author dark_fahad 02/06/2015
 *
 */
public class OrderParams {
	private String userid;
	private String orderItems;
	private double orderPrice;
	/**
	 * @return the userid
	 */
	public String getUserid() {
		return userid;
	}
	/**
	 * @param userid the userid to set
	 */
	public void setUserid(String userid) {
		this.userid = userid;
	}
	/**
	 * @return the orderItems
	 */
	public String getOrderItems() {
		return orderItems;
	}
	/**
	 * @param orderItems the orderItems to set
	 */
	public void setOrderItems(String orderItems) {
		this.orderItems = orderItems;
	}
	/**
	 * @return the orderPrice
	 */
	public double getOrderPrice() {
		return orderPrice;
	}
	/**
	 * @param orderPrice the orderPrice to set
	 */
	public void setOrderPrice(double orderPrice) {
		this.orderPrice = orderPrice;
	}
	
	
	

}
