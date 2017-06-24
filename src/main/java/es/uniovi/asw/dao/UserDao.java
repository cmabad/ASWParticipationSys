package es.uniovi.asw.dao;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import es.uniovi.asw.PropReader;
import es.uniovi.asw.model.User;

public class UserDao {
	private static Connection conn;
	
	public UserDao() {
		try {
			openConn();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	private static void openConn() throws SQLException {
		try {
		if(conn == null) {
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection(PropReader.get("DATABASE_URL"),
					PropReader.get("DATABASE_USER"), PropReader.get("DATABASE_PASS"));
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
		
	}
	
	public static User getUserByName(String userName) {
		try {
			PreparedStatement pstmt = conn.prepareStatement(PropReader.get("USER_BY_NAME"));
			pstmt.setString(1, userName);
			ResultSet rs = pstmt.executeQuery();
			
			if (rs.next()){
				User res = new User(rs.getString("FName"), rs.getString("LName"), rs.getInt("ID"), rs.getString("Email"), 
						rs.getString("DOB"), rs.getInt("Gender") == 0 ? false : true, rs.getString("Password"), rs.getString("Address"));
				return res;
			}
		} catch (SQLException e) {
			return null;
		}
		return null;
	}
	
	
	public static User getUserByID(int ID) {
		try {
			PreparedStatement pstmt = conn.prepareStatement(PropReader.get("USER_BY_ID"));
			pstmt.setInt(1, ID);
			ResultSet rs = pstmt.executeQuery();
			
			if (rs.next()){
				User res = new User(rs.getString("FName"), rs.getString("LName"), rs.getInt("ID"), rs.getString("Email"), 
						rs.getString("DOB"), rs.getInt("Gender") == 0 ? false : true, rs.getString("Password"), rs.getString("Address"));
				return res;
			}
		} catch (SQLException e) {

			return null;
		}
		return null;
	}

	public static User getUserLog(int UserID, String password) {
		try {
			PreparedStatement pstmt = conn.prepareStatement(PropReader.get("USER_LOG"));
			pstmt.setInt(1, UserID);
			pstmt.setString(2, password);
			ResultSet rs = pstmt.executeQuery();
			
			if (rs.next()){
				User res = new User(rs.getString("FName"), rs.getString("LName"), rs.getInt("ID"), rs.getString("Email"), 
						rs.getString("DOB"), rs.getInt("Gender") == 0 ? false : true, rs.getString("Password"), rs.getString("Address"));
				return res;
			}
		} catch (SQLException e) {
			return null;
		}
		return null;
	}
	
	public void saveUser(User user) {
		
		if((getUserByID(user.getId()) != null ) || userWithDifferentData(user))
		{
			File log = new File("Logs/log.txt");
			
			if((getUserByID(user.getId()) != null )) {
				System.out.println("User already exists: " + user.toString());
				try {
					
					BufferedWriter bw = new BufferedWriter(new FileWriter(log));
					bw.append("User already exists: " + user.getName() + " " + user.getLName() 
						+ " " + user.getId() + "\n");
					bw.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
				return;
			}
			
			if(userWithDifferentData(user)){
				System.out.println("User has different data: " + user.toString());
				try {
					
					BufferedWriter bw = new BufferedWriter(new FileWriter(log));
					bw.append("User already exists with different data: " + user.getName() + " " + user.getLName() 
						+ " " + user.getId() + "\n");
					bw.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
				return;
			}
		}

		try {
			
			PreparedStatement pstmt = conn.prepareStatement(PropReader.get("SAVE_USER"));

			pstmt.setString(1, user.getName());
			pstmt.setString(2, user.getLName());
			pstmt.setString(3, user.getEmail());
			pstmt.setString(4, user.getDOB());
			pstmt.setString(5, user.getAddress());
			pstmt.setInt(7, user.getId());
			pstmt.setString(8, user.getPassword());

			pstmt.executeUpdate();

		} catch (SQLException e) {
			System.err.println(e);
		}
	}
	
	public boolean userWithDifferentData(User givenUser) 
	{
		User searchedUser = getUserByEmail(givenUser.getEmail());
		return givenUser.equals(searchedUser);
	}
	
	public User getUserByEmail(String email) {
		
		try {
			PreparedStatement pstmt = conn.prepareStatement(PropReader.get("USER_BY_EMAIL"));
			pstmt.setString(1, email);
			ResultSet rs = pstmt.executeQuery();
			
			if (rs.next()){
				User res = new User(rs.getString("FName"), rs.getString("LName"), rs.getInt("ID"), rs.getString("Email"), 
						rs.getString("DOB"), rs.getInt("Gender") == 0 ? false : true, rs.getString("Password"), rs.getString("Address"));
				return res;
			
			}
		} catch (SQLException e) {
			return null;
		}
		return null;
	}
}
