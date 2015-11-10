package mysqldb;

public class DBInit {
	private static DBInit dbInit;
	private DBInit() {
		// TODO Auto-generated constructor stub
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();			
		}
	}
	public static DBInit getInstance(){
		if(dbInit == null)
			dbInit = new DBInit();
		
		return dbInit;
	}

}
