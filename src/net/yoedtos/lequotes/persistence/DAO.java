package net.yoedtos.lequotes.persistence;

import java.io.InputStream;
import java.io.Reader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class DAO {

	private static final Logger LOOGER = LoggerFactory.getLogger(DAO.class);
	
	private static DbConfig dbConfig;
	private Connection connection;
	private PreparedStatement preparedStatment;
	private Statement statment;
	
	static {	
		dbConfig = new DbConfig();
	}

	public void closeConnection() throws DAOException {

		if (connection != null) {
			try {
				connection.close();
			} catch (SQLException e) {
				LOOGER.error(e.getMessage());
				throw new DAOException("Error to close DB connection");
			}
			connection = null;
		}
	}

	protected boolean hasTable() {
		boolean exits = false;
		
		try {
			connection = getConnection(); 
			ResultSet result = connection.getMetaData().getTables(null, null, "QUOTES", null);
			
			if(result.next()) {
			  exits = true;
			}
		} catch (SQLException | ClassNotFoundException e) {
			LOOGER.error(e.getMessage());
			return false;
		}
		return exits;
		
	}
	
	protected void cleanUp() throws DAOException {
		try {
			if (preparedStatment != null) {
				preparedStatment.close();
				preparedStatment= null;
			}
		} catch (SQLException e) {
			LOOGER.error(e.getMessage());
			throw new DAOException();
		}
	}

	protected ResultSet executeQuery(String sql, Object[] params) throws DAOException {
		try {
			 return fillStatement(sql, params).executeQuery();
		} catch (SQLException | ClassNotFoundException e) {
			LOOGER.error(e.getMessage());
			throw new DAOException();
		}
	}
	
	protected int executeUpdate(String sql, Object[] params) throws DAOException {
		try {
			 return fillStatement(sql, params).executeUpdate();
		} catch (SQLException | ClassNotFoundException e) {
			LOOGER.error(e.getMessage());
			throw new DAOException();
		} 
	}
	
	protected void executeStatement(String sql) throws DAOException {
		try {
			statment = getStatement();
			statment.execute(sql);
		} catch (SQLException | ClassNotFoundException e) {
			LOOGER.error(e.getMessage());
			throw new DAOException();
		} finally {
			try {
				statment.close();
			} catch (SQLException e) {
				LOOGER.error(e.getMessage());
			}
		}
	}
	
	protected void addByBatch(String sql, Object[] params) throws DAOException {
		try {
			fillStatement(sql, params).addBatch();
		} catch (SQLException | ClassNotFoundException e) {
			LOOGER.error(e.getMessage());
			throw new DAOException();
		}
	}
	
	protected int[] saveBatch() throws DAOException {
		try {
			 return preparedStatment.executeBatch();
		} catch (SQLException e) {
			LOOGER.error(e.getMessage());
			throw new DAOException();
		} 
	}


	private PreparedStatement fillStatement(String sql, Object[] params) throws SQLException, ClassNotFoundException {

		preparedStatment = getPreparedStatement(sql);
		int count = 1;
		if (params != null) {
				
			for (Object param : params) {
				if (param instanceof String) {
					preparedStatment.setString(count++, (String)param);
				} else if (param instanceof Reader) {
					preparedStatment.setCharacterStream(count++, (Reader)param);
				} else if (param instanceof InputStream) {
					preparedStatment.setBinaryStream(count++, (InputStream)param);
				} else {
					preparedStatment.setObject(count++, param);
				}
			}
		}
		return preparedStatment;
	}
	
	private Connection getConnection() throws ClassNotFoundException, SQLException {
		if (connection == null) {
			Class.forName(dbConfig.getDriver());
			connection = DriverManager.getConnection(dbConfig.getUrl(), dbConfig.getUser(), dbConfig.getPassword()); 
		}

		return connection;	
	}
	
	private PreparedStatement getPreparedStatement(String sql) throws ClassNotFoundException, SQLException {
		if (preparedStatment == null) {
			preparedStatment = getConnection().prepareStatement(sql);
		}
		return preparedStatment;
	}
	
	private Statement getStatement() throws ClassNotFoundException, SQLException {
		if (statment == null) {
			statment = getConnection().createStatement();
		}
		return statment;
	}
}
