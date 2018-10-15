package kz.edu.nu.cs;

public class MenuItem implements Menu {
	
	private String item = "";
	
	public MenuItem(String item) {
		this.item = item;
	}

	@Override
	public String getName() {
		return item;
	}

	@Override
	public void setName(String name) {
		this.item = name;
	}

	@Override
	public String createMenu() {
		return "";
	}
}
