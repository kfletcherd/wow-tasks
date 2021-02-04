package util.db;

import util.SystemProperties;

import java.sql.ResultSet;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

/**
 * Establishes a postgres connection and pointlessly wraps stuff in helper methods
 */
public final class Postgresql {

	/**
	 * Store for the current connection
	 */
	private Connection db;


	/**
	 * Store for the active results set
	 */
	private ResultSet results;


	/**
	 * Attempt to open a connection to the database and store the connection
	 *
	 * @throws SQLException On connection errors
	 */
	public Postgresql()
	throws SQLException {
		db = DriverManager.getConnection(
			new StringBuilder(SystemProperties.getProperty("uri"))
				.append(SystemProperties.getProperty("url"))
				.append(SystemProperties.getProperty("db"))
				.toString(),
			SystemProperties.getProperty("usr"),
			SystemProperties.getProperty("pwd")
		);
	}


	/**
	 * Disable autocommit for transactional stuff
	 *
	 * @return Returns itself for method chaining
	 * @throws SQLException on database errors
	 */
	public Postgresql begin()
	throws SQLException {
		db.setAutoCommit(false);
		return this;
	}


	/**
	 * Rolls back a transaction if auto-commit is disabled
	 *
	 * @return Returns itself for method chaining
	 * @throws SQLException on database errors
	 */
	public Postgresql rollback()
	throws SQLException {
		if(!db.getAutoCommit())
			db.rollback();
		return this;
	}


	/**
	 * Commits the current transaction if auto-commit is disabled
	 *
	 * @return Returns itself for method chaining
	 * @throws SQLException on database errors
	 */
	public Postgresql commit()
	throws SQLException {
		if(!db.getAutoCommit())
			db.commit();
		return this;
	}


	/**
	 * Closes the current connection
	 *
	 * @throws SQLException on database errors
	 */
	public void close()
	throws SQLException {
		reset();
		db.close();
	}


	/**
	 * Closes and nulls the current result and/or prepared statement
	 *
	 * @return Returns itself for method chaining
	 * @throws SQLException on database errors
	 */
	public Postgresql reset()
	throws SQLException{
		if(results != null){
			results.close();
			results = null;
		}

		return this;
	}


	/**
	 * Execute a query that will not return a result set
	 * (insert, delete, update)
	 *
	 * Typecasting needs to happen in the query to allow this method
	 * to be straight forward
	 *
	 * @param sql The sql string
	 * @param vals The values to bind to the prepared statement, in order
	 * @return Returns itself for method chaining
	 * @throws SQLException on database errors
	 */
	public Postgresql execute(String sql, String[] vals)
	throws SQLException {
		PreparedStatement statement = db.prepareStatement(sql);

		for(int i = 0; i < vals.length; i++)
			statement.setString(i + 1, vals[i]);

		statement.execute();

		return this;
	}


	/**
	 * Execute a query that should return a result set (without parameters)
	 * (select, insert --- returning)
	 *
	 * Use the getNextResultRow method to work with the results
	 *
	 * @param sql The sql string
	 * @return Returns itself for method chaining
	 * @throws SQLException on database errors
	 */
	public Postgresql executeQuery(String sql)
	throws SQLException, Exception {
		PreparedStatement statement = db.prepareStatement(sql);
		results = statement.executeQuery();

		return this;
	}


	/**
	 * Execute a query that should return a result set (with parameters)
	 * (select, insert --- returning)
	 *
	 * Typecasting needs to happen in the query to allow this method
	 * to be straight forward
	 *
	 * Use the getNextResultRow method to work with the results
	 *
	 * @param sql The sql string
	 * @param vals The values to bind to the prepared statement, in order
	 * @return Returns itself for method chaining
	 * @throws SQLException on database errors
	 */
	public Postgresql executeQuery(String sql, String[] vals)
	throws SQLException {
		PreparedStatement statement = db.prepareStatement(sql);

		for(int i = 0; i < vals.length; i++)
			statement.setString(i + 1, vals[i]);

		results = statement.executeQuery();

		return this;
	}


	/**
	 * Get the next row of values, all as strings cause they'll probably
	 * get stuffed in some other format anyway, so f-it, this is easiest
	 *
	 * @return An array of Strings of each column value
	 * @throws Exception If no results exist to look over
	 * @throws SQLException On database or other errors
	 */
	public String[] getNextResultRow()
	throws SQLException, Exception {
		if(results == null)
			throw new Exception("No results set");

		int cols = results.getMetaData().getColumnCount();

		String[] res = new String[cols];

		// Get the next result, fresh queries start at -1 cursor or something
		if(results.next() == false)
			return null;

		for(int i = 1; i <= cols; i++){
			res[i - 1] = results.getString(i);
		}

		return res;
	}


	/**
	 * Test to make sure one can insert, get and delete a record from a "test"
	 * table
	 */
	public static void main(String[] a)
	throws Exception {
		String sql;
		String[] params;

		Postgresql pg = new Postgresql();
		pg.begin();

		sql = "INSERT INTO test VALUES (?, ?);";
		params = new String[]{"1", "asdf"};
		pg.execute(sql, params);
		pg.commit();

		sql = "SELECT * FROM test WHERE id = ?";
		params = new String[]{"1"};
		pg.executeQuery(sql, params);
		System.out.println(String.join(", ", pg.getNextResultRow()));

		pg.execute("DELETE FROM test WHERE id = 1", new String[]{});
		pg.commit();

		pg.close();
	}
}

