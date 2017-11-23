package driver;
import java.sql.*;
import java.util.ArrayList;

public class JDBCDriver {
	private static Connection conn = null;
	private static ResultSet rs = null;
	private static PreparedStatement ps = null;
	
	public static void connect(){
		try {
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection("jdbc:mysql://localhost/Lab10?user=root&password=root&useSSL=false");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void close(){
		try{
			if (rs!=null){
				rs.close();
				rs = null;
			}
			if(conn != null){
				conn.close();
				conn = null;
			}
			if(ps != null ){
				ps = null;
			}
		}catch(SQLException sqle){
			System.out.println("connection close error");
			sqle.printStackTrace();
		}
	}
	
	
	public static boolean validate(String usr, String pwd){
		connect();
		try {
			ps = conn.prepareStatement("SELECT password FROM User WHERE username=?");
			ps.setString(1, usr);
			rs = ps.executeQuery();
			System.out.println(rs);
			if(rs.next()){
				if(pwd.equals(rs.getString("password")) ){
					return true;
				}
			}
		} catch (SQLException e) {
			System.out.println("SQLException in function \"validate\"");
			e.printStackTrace();
		}finally{
			close();
		}
		return false;		
	}
	
	
	public static void increment(String usr, String page){
		connect();
		try{
			ps = conn.prepareStatement("SELECT userID FROM User WHERE username =?");
			ps.setString(1, usr);
			rs = ps.executeQuery();
			int userID = -1;
			if(rs.next()){
				userID = rs.getInt("userID");
			}
			rs.close();
			ps = conn.prepareStatement("SELECT pageID FROM Page WHERE name= ?");
			ps.setString(1, page);
			rs = ps.executeQuery();
			int pageID = -1;
			
			if(rs.next()){
				pageID = rs.getInt("pageID");
			}
			rs.close();
			ps = conn.prepareStatement("SELECT count FROM PageVisited WHERE userID = '"+userID+"' AND pageID='"+pageID+"'");
			rs = ps.executeQuery();
			if(rs.equals(null)){
				System.out.println("nothing returned");
			}
			int count = -1;
			if (rs.next()){
				count = rs.getInt("count");
			}
			rs.close();
			if(count >0){
				ps = conn.prepareStatement("UPDATE pageVisited pv SET pv.count = "+(count+1)+" WHERE userID = "+userID+" AND pageID = "+pageID);
			}else{
				ps = conn.prepareStatement("INSERT INTO pageVisited (userID, pageID, count) VALUES ('"+userID+"', '"+pageID+"', 1) ");
			}
			ps.executeUpdate();
		}catch(SQLException sqle){
			System.out.println("SQLException in function \"increment\"");
			sqle.printStackTrace();
		}
		finally{
			close();
		}
	}
	public static ArrayList<ArrayList<String> >getData(){
		ArrayList<ArrayList<String>>  stat = new ArrayList<ArrayList<String>>();
		connect();
		try {
			conn = DriverManager.getConnection("jdbc:mysql://localhost/Lab10?user=root&password=root&useSSL=false");
			ps = conn.prepareStatement("SELECT u.username, p.name, pv.count FROM User u, Page p, PageVisited pv "
					+ "WHERE u.userID = pv.userID AND p.pageID = pv.pageID ORDER BY u.userID, p.pageID");
			rs = ps.executeQuery();
			while(rs.next()){
				ArrayList<String> row = new ArrayList<String>();
				row.add(rs.getString("u.username"));
				row.add(rs.getString("p.name"));
				row.add(rs.getString("pv.count"));
				stat.add(row);
			}
		}catch(SQLException sqle){
			System.out.println("SQLException in function \" getData\": ");
			sqle.printStackTrace();
		}finally{
			close();
		}
		return stat;
	}
}
