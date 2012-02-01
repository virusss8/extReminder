package edu.virusss8.extreminder.android.sloveniainfo;

public class WebPage {
	
	private String address;
	private String image_addr;
	private String title;
//	private String[] info;
	private String info;
	private String[] description;
	
	public WebPage(String address, String image_addr, String title,
			String[] description, String info) {
		this.address = address;
		this.image_addr = image_addr;
		this.title = title;
		this.description = description;
		this.info = info;
	}
	
	public WebPage() {
	}
	
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getImage_addr() {
		return image_addr;
	}
	public void setImage_addr(String image_addr) {
		this.image_addr = image_addr;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
//	public String[] getInfo() {
//		return info;
//	}
//	public void setInfo(String[] info) {
//		this.info = info;
//	}
	public String getInfo() {
		return info;
	}
	public void setInfo(String info) {
		this.info = info;
	}
	public String[] getDescription() {
		return description;
	}
	public void setDescription(String[] description) {
		this.description = description;
	}
}
