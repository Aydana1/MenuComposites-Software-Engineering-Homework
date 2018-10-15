package kz.edu.nu.cs;

import java.sql.*;
import javax.sql.*;
import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.util.*;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;

import com.google.gson.Gson;

@WebServlet(urlPatterns = { "/todo" })

public class todoservlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private static ArrayList<String> list = new ArrayList<String>();
	
	MenuComposite primaryMenu = new MenuComposite("Menu");
	
	private static Set<String> set = new HashSet<>();
       
    public todoservlet() {
        super();
    }

    public void service(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    	
    	//todo?composite=Soups&item=Ramen&method=add
    	
    	if(primaryMenu.getComposites().isEmpty()) {
    		// initially there're 4 composites
	    	 primaryMenu.getComposites().add(new MenuComposite("Soups"));
	    	 primaryMenu.getComposites().add(new MenuComposite("Drinks"));
	    	 primaryMenu.getComposites().add(new MenuComposite("Appetizers"));
	    	 primaryMenu.getComposites().add(new MenuComposite("Main Course"));
    	}
    	//in each composite there 3 items
    	for(Object i: primaryMenu.getComposites())  {
    		MenuComposite obj = (MenuComposite) i;
  
    		if(obj.getName().equals("Soups")) {	
    			if(obj.getComposites().isEmpty()) {
	    			obj.getComposites().add(new MenuItem("Chicken Soup"));
	    			obj.getComposites().add(new MenuItem("Ramen"));
	    			obj.getComposites().add(new MenuItem("Beef Soup"));
    			}
    		} else if (obj.getName().equals("Drinks")) {
    			if(obj.getComposites().isEmpty()) {
	    			obj.getComposites().add(new MenuItem("Water"));
	    			obj.getComposites().add(new MenuItem("Orange Juice"));
	    			obj.getComposites().add(new MenuItem("Apple Juice"));
    			}
    		} else if (obj.getName().equals("Appetizers")) {
    			if(obj.getComposites().isEmpty()) {
	    			obj.getComposites().add(new MenuItem("American Cheesecake"));
	    			obj.getComposites().add(new MenuItem("Cezar salad"));
	    			obj.getComposites().add(new MenuItem("Pancakes with jam"));
    			}
    		} else if (obj.getName().equals("Main Course")) {
    			if(obj.getComposites().isEmpty()) {
	    			obj.getComposites().add(new MenuItem("Manty"));
	    			obj.getComposites().add(new MenuItem("Beshbarmak"));
	    			obj.getComposites().add(new MenuItem("Lagman"));
    			}
    		}		
    	}
    	
	
    	Map<String, String[]> map = request.getParameterMap();
    	
    	if(!map.isEmpty()) {
    	
    		String[] param1 = map.get("composite");
    		String[] param2 = map.get("item");
    		String[] param3 = map.get("method");
    		
    		boolean doesExist = false;
    		
    		// loop through existing composites 
    		for(Object i: primaryMenu.getComposites()) {
    	      	MenuComposite obj = (MenuComposite) i;
    	      	// if composite name matches with the one in the primaryMenu 
    	      	if(param1[0].equals(obj.getName())) {
    	      		// if it is add method
    	      		if(param3[0].equals("add")) {
    	      			// if there're no items yet in current composite
    	      			if(obj.getComposites().isEmpty()) {
    	      				// add a new one
        	      			obj.getComposites().add(new MenuItem(param2[0]));
    	      			} else {
    	      				// else
    	      				//check for duplicate: loop through existing items of the current composite
    	      				 for(Object j: obj.getComposites()) {
    	      					 MenuItem childObj = (MenuItem) j;
    	      					 // if such item name already exists
    	      					 if(childObj.getName().equals(param2[0])) {
    	      						 // don't add it
    	      						 response.getWriter().write(
    	      					            "<html><body>This item already exists!</body></html>");
    	      						 doesExist = true;
    	      						 break;
    	      					 }	      			
    	      					 
    	      				 }
    	      				 // else add it
    	      				 if(doesExist == false) {
    	      					obj.getComposites().add(new MenuItem(param2[0]));
    	      				 }
	      						
    	      			}
    	      			
    	      		} 
    	      				// delete method
    	      		else if(param3[0].equals("delete")) {	
    	      			if(obj.getComposites().isEmpty()) {	
    	      				 response.getWriter().write(
	      					            "<html><body>There're no items to delete!</body></html>");
    	      			} else {
    	      				   
    	      				for(Object j: obj.getComposites()) {
	   	      					 MenuItem childObj = (MenuItem) j;
	   	      					 // item name to delete
	   	      					 if(childObj.getName().equals(param2[0])) {
	   	      						// delete
	   	      						 obj.getComposites().remove(childObj);
		   	      					 doesExist = true;
		      						 break;
	   	      					 }	      			
   	      				 	}
    	      				
    	      				 if(doesExist == false) {
    	      					response.getWriter().write(
	      					            "<html><body>Such item doesn't exist!</body></html>");
     	      				 }
    	      			}
    	      			
    	      		}
    	      	}
    		}		
    		
    	}
    			 
    	response.getWriter().write(
              "<html><body><h1>Menu:</h1>"
    		  + primaryMenu.createMenu() 
              + "</body></html>");
		
      	Gson gson = new Gson();   
		System.out.println(gson.toJson(primaryMenu.getComposites()));

    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		PrintWriter out = response.getWriter(); 
		response.setContentType("text/plain");
		
		Gson gson = new Gson();   
		out.append(gson.toJson(list));   // convert list to JSON format

	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String myText = request.getParameter("texttosend");
		InputStream targetStream = IOUtils.toInputStream(myText);  // convert String to InputStream
		InputStream is = new MyInputStream(targetStream);  // send this stream to the specified class to receive back stream
		byte[] bytes = new byte[100];
		is.read(bytes);   // read from inputstream to bytes
		is.close();
		String new_text = new String(bytes);  //convert bytes to String
		list.add(new_text);     // update  the list
		

		PrintWriter out = response.getWriter(); 
		response.setContentType("text/plain");
		
		
		try {
			//testdatabase361
			Class.forName("com.mysql.jdbc.Driver");
			String jdbcUrl = "jdbc:mysq://localhost:3306/test";
			String username = "user361";
			String password = "secret";
			Connection connection = null;
			
			connection = DriverManager.getConnection(jdbcUrl, username, password);
			Statement statement = connection.createStatement();
			
			String fname = request.getParameter("fname");
			String lname = request.getParameter("lname");
			
			String sql = "insert into info values ('" + fname + "','" + lname + "')";
			statement.executeUpdate(sql);
			
			out.println("<h1> Record inserted </h1>");
			RequestDispatcher rd = request.getRequestDispatcher("/form.html");
			rd.include(request, response);
			
		} catch (ClassNotFoundException cnfe) {
			out.println("class not found");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
		
		
//		String myText = request.getParameter("texttosend");
//	    int i;
//	    boolean doesExist = false; 
//	    
//	    if (myText != null) {
//	    	Pattern pattern = Pattern.compile(myText);
//		    for(i=0; i<list.size(); ++i) {
//		    	Matcher m = pattern.matcher(list.get(i)); 
//		    	if (m.matches() == true) {
//		    		doesExist = true;
//		    		break;
//		    	} 
//		    }
//		    if (doesExist == false) {
//		    	list.add(myText);
//		    }
//		    
//	    }
//	    
//	    String index = request.getParameter("texttoremove"); 
//	    System.out.println(index);
//	    
//	    if (index != null) {
//	    	list.remove(Integer.parseInt(index));
//	    }
//	    
//	    String toremove = request.getParameter("toedit");
//
//	    String newvalue = request.getParameter("resultval");
//	    
//	    if(toremove != null) {
//	    	list.remove(Integer.parseInt(toremove));
//	    	list.add(Integer.parseInt(toremove), newvalue);
//	    	
//	    }

	}

}
