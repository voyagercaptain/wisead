package kr.wise.commons.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.apache.log4j.Logger;

public class ConnectionHelper {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(ConnectionHelper.class);

//	public static Connection getDAConnection() throws SQLException, Exception {
//		try{
//			Class.forName(ExecutorConf.getDa_driver());
////			logger.debug("===con timeout setting===");
//			DriverManager.setLoginTimeout(30);
//			Connection con = DriverManager.getConnection(ExecutorConf.getDa_jdbcUrl(), ExecutorConf.getDa_user(), ExecutorConf.getDa_password());
////			logger.debug("===con time ===");
//			con.setAutoCommit(false);
//			return con;
//		}catch(SQLException e){
//			logger.error(e);
//			throw e;
//		}catch(Exception e){
//			logger.error(e);
//			throw e;
//		}
//	}
//
//	public static Connection getMetaConnection() throws SQLException, Exception {
//		try{
//			Class.forName(ExecutorConf.getMeta_driver());
//			Connection con = DriverManager.getConnection(ExecutorConf.getMeta_jdbcUrl(), ExecutorConf.getMeta_user(), ExecutorConf.getMeta_password());
//			con.setAutoCommit(false);
//			return con;
//		}catch(SQLException e){
//			logger.error(e);
//			throw e;
//		}catch(Exception e){
//			logger.error(e);
//			throw e;
//		}
//	}

	public static Connection getConnection(String driver, String jdbcUrl, String user, String password) throws SQLException, Exception {
		try {
			Class.forName(driver);
			Connection con = DriverManager.getConnection(jdbcUrl, user, password);
			con.setAutoCommit(false);
			return con;
		}catch(SQLException e){
			logger.error(e);
			throw e;
		}catch(Exception e){
			logger.error(e);
			throw e;
		}
	}
	
}
