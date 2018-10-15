package kz.edu.nu.cs;

import java.util.ArrayList;

public class MenuComposite<T> implements Menu {
	
	private String name = "";
	private ArrayList<T> composites = new ArrayList<>();
	
	public MenuComposite(String compName) {
		this.name = compName;
	}
	

//	public void addComposite(String compName) {
//		
//		MenuComposite mc = new MenuComposite(compName);
//		composites.add((T) mc);
//	}
//	
	public ArrayList<T> getComposites() {
		return composites;
	}
	

	@Override
	public String getName() {
		return name;
	}

	@Override
	public void setName(String name) {
		this.name = name;
	}
	

	@Override
	public String createMenu() {
		
		String html = "<ul>";
		
		for(T element: composites) {
			
			html += "<li>" +  ((Menu) element).getName() + ((Menu) element).createMenu()  + "</li>";
			
		}
		
		html += "</ul>";
		
		return html;
	}

}
