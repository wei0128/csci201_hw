package servlet;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
//import java.nio.file.Path;
//import java.nio.file.Paths;
import java.nio.file.Paths;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;

import com.google.gson.Gson;

import objects.Collection;

/**
 * Servlet implementation class webServlet
 */
@WebServlet("/webServlet")
@MultipartConfig
public class webServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	//When receiving submission
	//Put file into input stream and pass to parser
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		Part filePart = request.getPart("myFile");
		String styleSelection = request.getParameter("style");
		String fileName = Paths.get(filePart.getSubmittedFileName()).toString();
		//Only redirect to home page is pass validation
		if(validate(fileName)) {
			InputStream fileContent = filePart.getInputStream();
			Collection Schools = parser(fileContent);
			session.setAttribute("Schools", Schools);
			session.setAttribute("style", styleSelection);
			session.setMaxInactiveInterval(60);
			response.sendRedirect("home.jsp");
		}
		else {
			response.sendRedirect("index.jsp");
		}
		
	}
	
	
	
	//Validate
	//Takes in file name as String
	//Check if file name is empty or not end with .json
	//return false if fail to reach criteria, else return true;
	private boolean validate(String fileName) {
		if(fileName.isEmpty() || !fileName.substring(fileName.length()-5, fileName.length()).equals(".json")) {
			return false;
		}
		else {
			return true;
		}
	}
	
	//Parser
	//Takes in inputstream
	//Parse input file using Gson
	//Return the data structure
	private Collection parser(InputStream inputStream) {
		BufferedReader br = null;
		try {
			br = new BufferedReader(new InputStreamReader(inputStream));
		} catch(Exception e) {
			System.out.println("Error in file");
		}
		Gson gson = new Gson();
		Collection myCollection = gson.fromJson(br, Collection.class);
		
		try {
			br.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return myCollection;
	}

}
