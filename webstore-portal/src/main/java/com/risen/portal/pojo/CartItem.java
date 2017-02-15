package com.risen.portal.pojo;

/**
 * 购物车条目对应pojo
 * @author sen
 *
 */
public class CartItem {
	
	//商品id
	private long id;
	//商品标题
	private String title;
	//数量
	private int num;
	//商品价格
	private long price;
	//商品图片
	private String image;
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public int getNum() {
		return num;
	}
	public void setNum(int num) {
		this.num = num;
	}
	public long getPrice() {
		return price;
	}
	public void setPrice(long price) {
		this.price = price;
	}
	public String getImage() {
		return image;
	}
	public void setImage(String image) {
		this.image = image;
	}
	
	
}
