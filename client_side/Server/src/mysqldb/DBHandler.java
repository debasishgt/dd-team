package mysqldb;



import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;

import core.Player;

public class DBHandler {
	private DBInit dbInit;
	private Connection conn;
	public DBHandler() {
		// TODO Auto-generated constructor stub
		dbInit = DBInit.getInstance();
	}
	private boolean init(){
		boolean ret = false;

		try {
			conn = (Connection) DriverManager.getConnection("jdbc:mysql://localhost:3306/library","root", "");
			ret = true;
		}

		catch (SQLException e){
			System.out.println("Error Message:" +e.getMessage());
		}
		return ret;
	}
	public boolean processLogin(String username, String password){
		boolean ret = false;
		PreparedStatement pStmt = null;
		if (conn == null)
			if (init() != true)
				return ret;
		try {
			conn = (Connection) DriverManager.getConnection("jdbc:mysql://localhost:3306/library","root","");
			if(conn != null){
				pStmt = (PreparedStatement) conn.prepareStatement("select * from users where username like ? and password like ?");
				if(pStmt != null){
					pStmt.setString(1, username);
					pStmt.setString(2, password);
//					ret = pStmt.execute();
					ResultSet rs  = pStmt.executeQuery();
					ret = rs.last();
					pStmt.close();
				}
				conn.close();
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally{
			try{if(conn != null)conn.close();conn=null;if(pStmt != null)pStmt.close();}catch(SQLException ignore){return ret;}
		}
		return ret;
	}
	public boolean getPlayerPosition(String username){
		boolean ret = false;
		PreparedStatement pStmt = null;
		if (conn == null)
			if (init() != true)
				return ret;
		try {
			conn = (Connection) DriverManager.getConnection("jdbc:mysql://localhost:3306/library","root","");
			if(conn != null){
				pStmt = (PreparedStatement) conn.prepareStatement("select * from users where username like ?");
				if(pStmt != null){
					pStmt.setString(1, username);
//					ret = pStmt.execute();
					ResultSet rs  = pStmt.executeQuery();
					ret = rs.last();
					pStmt.close();
				}
				conn.close();
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally{
			try{if(conn != null)conn.close();conn=null;if(pStmt != null)pStmt.close();}catch(SQLException ignore){return ret;}
		}
		return ret;
	}

	// Changes Made -- Ansab
	public Player getPlayer(String username){
		Player returnPlayer = null;
		PreparedStatement pStmt = null;
		if (conn == null)
			if (init() != true)
				return returnPlayer;
		try {
			conn = (Connection) DriverManager.getConnection("jdbc:mysql://localhost:3306/library","root","");
			if(conn != null){
				pStmt = (PreparedStatement) conn.prepareStatement("select * from users where username like ?");
				if(pStmt != null){
					pStmt.setString(1, username);
					ResultSet rs  = pStmt.executeQuery();
					if(rs.next()){
						short type = (short)rs.getInt("type");
						float x = rs.getFloat("x");
						float y = rs.getFloat("y");
						float h = rs.getFloat("h");
						returnPlayer = new Player(username);
						returnPlayer.setType(type);
						returnPlayer.setX(x);
						returnPlayer.setY(y);
						returnPlayer.setHeading(h);
					}
					pStmt.close();
				}
				conn.close();
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally{
			try{if(conn != null)conn.close();conn=null;if(pStmt != null)pStmt.close();}catch(SQLException ignore){return returnPlayer;}
		}
		return returnPlayer;
	}

	public int savePlayerPosition(String username, short type, float x, float y, float h){
		int ret = 0;
		PreparedStatement pStmt = null;
		PreparedStatement ps = null;
		if (conn == null)
			if (init() != true)
				return ret;
		if(conn != null){
			try{
				pStmt = (PreparedStatement) conn.prepareStatement("select * from users where username like ?");
				if(pStmt != null){
					pStmt.setString(1, username);
					ResultSet rs = pStmt.executeQuery();
					if(rs.next()){
						ps = (PreparedStatement) conn.prepareStatement("update users set type = ?, x = ?, y = ?, h = ? where username like ?");
						ps.setInt(1, (int)type);
						ps.setFloat(2, x);
						ps.setFloat(3, y);
						ps.setFloat(4, h);
						ps.setString(5, username);
						System.out.println(ps.toString());
						ret = ps.executeUpdate();
						ps.close();
						conn.close();
						conn = null;
					}
					else{
						pStmt.getResultSet();
						System.out.println(rs.toString());
						ret = -1;
					}
				}
			}
			catch(SQLException error){
					System.out.println(error.getMessage());
			}
			finally{
				try{if(ps != null)ps.close();ps = null;if(pStmt != null)pStmt.close();pStmt = null;if(conn != null)conn.close();conn = null;} catch(SQLException ignore){}
			}
		}

		return ret;
	}

	public int registerUser(String username, String password){
		int ret = 0;
		PreparedStatement pStmt = null;
		PreparedStatement ps = null;
		if (conn == null)
			if (init() != true)
				return ret;
		if(conn != null){
			try{
				pStmt = (PreparedStatement) conn.prepareStatement("select * from users where username like ?");
				if(pStmt != null){
					pStmt.setString(1, username);
					ResultSet rs = pStmt.executeQuery();
					if(!rs.next()){
						ps = (PreparedStatement) conn.prepareStatement("insert into users values (?, ?, ?, ?, ?, ?)");
						ps.setString(1, username);
						ps.setString(2, password);
						ps.setInt(3, 0);
						ps.setFloat(4, 0);
						ps.setFloat(5, 0);
						ps.setFloat(6, 0);
						System.out.println(ps.toString());
						ret = ps.executeUpdate();
						ps.close();
						conn.close();
						conn = null;
					}
					else{
						pStmt.getResultSet();
						System.out.println(rs.toString());
						ret = -1;
					}
				}
			}
			catch(SQLException error){
					System.out.println(error.getMessage());
			}
			finally{
				try{if(ps != null)ps.close();ps = null;if(pStmt != null)pStmt.close();pStmt = null;if(conn != null)conn.close();conn = null;} catch(SQLException ignore){}
			}
		}

		return ret;
	}
}
