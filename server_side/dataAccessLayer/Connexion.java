package dataAccessLayer;

import java.sql.*;
import java.util.*;

import configuration.GameServerConf;

public class Connexion {

	protected Connection conn;
	protected Statement stmt;
	protected ResultSet rset;
	protected ResultSetMetaData rsetMeta;
	protected PreparedStatement pstmt;

	private String userName;
	private String password;
	private String serverName;
	private String portNumber;
	private String databaseBaseName;

	// Connection to Database
	public Connexion(GameServerConf configuration) throws SQLException, ClassNotFoundException {

		try {

			this.userName = configuration.getDatabaseUsername();
			this.password = configuration.getDatabasePassword();
			this.serverName = configuration.getDatabaseServername();
			this.portNumber = configuration.getDatabasePortnumber();
			this.databaseBaseName = configuration.getDatabaseBaseName();
			Properties connectionProps = new Properties();

			// setproperty only accept the string.
			connectionProps.setProperty("user", this.userName);
			connectionProps.setProperty("password", this.password);

			conn = DriverManager.getConnection(
					"jdbc:mysql://" + this.serverName + ":" + this.portNumber + "/" + this.databaseBaseName,
					connectionProps);

		} catch (Exception e) {
			System.out.println("Connexion failure with the database :" + e);
			e.printStackTrace();

		}
	}

	public Connection getInstance() {
		return conn;
	}
}