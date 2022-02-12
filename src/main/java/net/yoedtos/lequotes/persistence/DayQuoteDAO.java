package net.yoedtos.lequotes.persistence;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

import net.yoedtos.lequotes.entity.DayQuote;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DayQuoteDAO extends DAO {

	private static final Logger LOGGER = LoggerFactory.getLogger(DayQuoteDAO.class);
	
	public DayQuoteDAO() {
		checkTable();
	}
	
	public void addByLote(DayQuote dayQuote) throws DAOException {
		
		String sql = "INSERT INTO quotes (title, text, audio) VALUES (?, ?, ?)";
		Object[] params = {dayQuote.getTitle(), dayQuote.getTxtReader(), dayQuote.getAudio() };
		addByBatch(sql, params);
	}
	
	public int[] saveLote() throws DAOException {
		int[] saved;
		
		try {
			saved = saveBatch();
			return saved;
			
		} finally {
			cleanUp();
		}
	}
	
	public void createQuoteTable() throws DAOException {
		String sql = "CREATE TABLE quotes (id identity, title varchar(200), text clob, audio blob)";
		executeStatement(sql);
	}
	
	public void dropQuoteTable() throws DAOException {
		
		String sql = "DROP TABLE quotes";
		executeStatement(sql);		
	}
	
	public DayQuote load(DayQuote dayQuote) throws DAOException, IOException {
		int id = dayQuote.getId();
		String sql = "SELECT * FROM quotes WHERE id = ?";
		Object[] params = {id};
		try {
			ResultSet result = executeQuery(sql, params);
			if (result.next()) {
				return restoreObject(dayQuote, result);
			} else {
				throw new DAOException("ID: " + id + " quote not found");
			}
		} catch (SQLException e) {
			throw new DAOException(e.getMessage());
		} finally {
			cleanUp();
		}
	}
	
	public int remove(int id) throws DAOException {
		
		String sql = "UPDATE quotes SET title = ?, text = ?, audio = ? WHERE id = ?";
		Object[] params = {null, null, null, id};
		
		return executeUpdate(sql, params);
	}
	
	
	public int add(DayQuote dayQuote) throws DAOException {
		
		String sql = "INSERT INTO quotes (title, text, audio) VALUES (?, ?, ?)";
		Object[] params = {dayQuote.getTitle(), dayQuote.getTxtReader(), dayQuote.getAudio()};

		int saved;	
		try {
			saved = executeUpdate(sql, params);
		} finally {
			cleanUp();
		}
		return saved;	
	}
	
	public int update(DayQuote dayQuote, int id) throws DAOException {
		
		String sql = "UPDATE quotes SET title = ?, text = ?, audio = ? WHERE id = ?";
		Object[] params = {dayQuote.getTitle(), dayQuote.getTxtReader(), dayQuote.getAudio(), id};
		
		int saved;
		try {
			saved = executeUpdate(sql, params);	
		} finally {
			cleanUp();
		}
		return saved;
	}
	
	public Integer getNullRow() throws DAOException {
		
		String sql = "SELECT id FROM quotes WHERE title IS NULL LIMIT 1";
		Object[] params = null;
		
		try {
			ResultSet result = executeQuery(sql, params);
			
			if (!result.next()) {
				return null;
			}
			return result.getInt(1);
		} catch (SQLException e) {
			throw new DAOException(e.getMessage());
		} finally {
			cleanUp();
		}
	}
	
	private DayQuote restoreObject(DayQuote dayQuote, ResultSet result) throws DAOException, IOException {
		
		try {
			dayQuote.setId(result.getInt(1));
			dayQuote.setTitle(result.getString(2));
			dayQuote.setTxtReader(result.getCharacterStream(3));
			dayQuote.setAudioByte(IOUtils.toByteArray(result.getBinaryStream(4)));
		} catch (SQLException e) {
			throw new DAOException(e.getMessage());
		}
		
		return dayQuote;
	}
	
	private void checkTable() {
		LOGGER.trace("check Table called");
		
		if(!hasTable()) {
			try {
				createQuoteTable();
			} catch (DAOException e) {
				LOGGER.error(e.getMessage());
				throw new RuntimeException(e.getMessage());
			}
			LOGGER.debug("Quote Table created");
		}
	}
}
