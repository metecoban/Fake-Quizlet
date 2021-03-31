package DBConnection;

import Model.Word;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;

public class DataSource {

	public static final String DB_NAME = "DictionaryDB";
	public static final String DB_HOST = "//DESKTOP-B7GTNMP\\SQLEXPRESS";
	public static final String CONNECTION_STING = "jdbc:sqlserver:" + DB_HOST + ";database=" + DB_NAME
			+ ";integratedSecurity=true;";

	public static final String TABLE_SECTION = "Section";
	public static final String COLUMN_SECTION_SETID = "sectionID";
	public static final String COLUMN_SECTION_NAME = "name";
	public static final String COLUMN_SECTION_FIRSTCOLUMNNAME = "firstColumnName";
	public static final String COLUMN_SECTION_SECONDCOLUMNNAME = "secondColumnName";

	public static final String TABLE_WORD = "Word";
	public static final String COLUMN_WORD_SETID = "sectionID";
	public static final String COLUMN_WORD_WORD1 = "word1";
	public static final String COLUMN_WORD_WORD2 = "word2";

	private Connection con;

	private DataSource() {
	}

	private static DataSource instance = new DataSource();

	public static DataSource getInstance() {
		return instance;
	}

	public boolean openCon() {
		try {
			con = DriverManager.getConnection(CONNECTION_STING);
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			return true;
		}
	}

	public ObservableList<Word> getTableValues(String str) {
		try (Statement stm = con.createStatement();
				ResultSet rslt = stm.executeQuery("Select " + TABLE_WORD + "." + COLUMN_WORD_SETID + ","
						+ COLUMN_WORD_WORD1 + "," + COLUMN_WORD_WORD2 + " from " + TABLE_WORD + " inner join "
						+ TABLE_SECTION + " on " + TABLE_SECTION + "." + COLUMN_SECTION_SETID + " = " + TABLE_WORD + "."
						+ COLUMN_WORD_SETID + " where " + TABLE_SECTION + "." + COLUMN_SECTION_NAME + " = " + "'" + str
						+ "'" + ";")) {
			ObservableList<Word> allValues = FXCollections.observableArrayList();
			while (rslt.next()) {
				allValues.add(new Word(rslt.getInt(COLUMN_WORD_SETID), rslt.getString(COLUMN_WORD_WORD1),
						rslt.getString(COLUMN_WORD_WORD2)));
			}
			return allValues;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}

	}

	public ArrayList<String> getColumnNameValues(String str) {
		try (Statement stm = con.createStatement();
				ResultSet rslt = stm.executeQuery(
						"select " + COLUMN_SECTION_FIRSTCOLUMNNAME + "," + COLUMN_SECTION_SECONDCOLUMNNAME + " from "
								+ TABLE_SECTION + " where " + COLUMN_SECTION_NAME + " = " + "'" + str + "'" + ";")) {
			ArrayList<String> allValues = new ArrayList<>();
			while (rslt.next()) {
				allValues.add(rslt.getString(COLUMN_SECTION_FIRSTCOLUMNNAME));
				allValues.add(rslt.getString(COLUMN_SECTION_SECONDCOLUMNNAME));
			}
			return allValues;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

	public ArrayList<String> getComboBoxNameValues() {
		try (Statement stm = con.createStatement();
				ResultSet rslt = stm.executeQuery("select " + COLUMN_SECTION_NAME + " from " + TABLE_SECTION)) {
			ArrayList<String> allValues = new ArrayList<>();
			while (rslt.next()) {
				allValues.add(rslt.getString(COLUMN_SECTION_NAME));
			}
			return allValues;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}

	}

	public HashMap<String, String> getHashMapWordValues(String str) {
		try (Statement stm = con.createStatement();
				ResultSet rslt = stm.executeQuery("select " + COLUMN_WORD_WORD1 + "," + COLUMN_WORD_WORD2 + " from "
						+ TABLE_WORD + " inner join " + TABLE_SECTION + " on " + TABLE_SECTION + "."
						+ COLUMN_SECTION_SETID + " = " + TABLE_WORD + "." + COLUMN_WORD_SETID + " where "
						+ TABLE_SECTION + "." + COLUMN_SECTION_NAME + " = " + "'" + str + "'" + ";")) {
			HashMap<String, String> allValues = new HashMap<>();
			while (rslt.next()) {
				allValues.put(rslt.getString(COLUMN_WORD_WORD1), rslt.getString(COLUMN_WORD_WORD2));
			}
			return allValues;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

	public ArrayList<String> getComboBoxWordValues(String str) {
		try (Statement stm = con.createStatement();
				ResultSet rslt = stm.executeQuery("select " + COLUMN_WORD_WORD1 + "," + COLUMN_WORD_WORD2 + " from "
						+ TABLE_WORD + " inner join " + TABLE_SECTION + " on " + TABLE_SECTION + "."
						+ COLUMN_SECTION_SETID + " = " + TABLE_WORD + "." + COLUMN_WORD_SETID + " where "
						+ TABLE_SECTION + "." + COLUMN_SECTION_NAME + " = " + "'" + str + "'" + ";")) {
			ArrayList<String> allValues = new ArrayList<>();
			while (rslt.next()) {
				allValues.add(rslt.getString(COLUMN_WORD_WORD1) + " - " + rslt.getString(COLUMN_WORD_WORD2));
			}
			return allValues;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}

	}

	public void delWordsFromSet(String str) {
		try (PreparedStatement stm = con.prepareStatement(
				"DELETE FROM " + TABLE_WORD + " WHERE " + COLUMN_WORD_WORD1 + " = " + "'" + str + "'" + " ");) {
			stm.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void delSet(String str) {
		try (PreparedStatement stm = con.prepareStatement("DELETE FROM " + TABLE_WORD + " WHERE " + COLUMN_WORD_SETID
				+ " = " + "'" + getWhichId(str) + "'" + " ");) {
			stm.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		try (PreparedStatement stm = con.prepareStatement(
				"DELETE FROM " + TABLE_SECTION + " WHERE " + COLUMN_SECTION_NAME + " = " + "'" + str + "'" + " ");) {
			stm.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	private String getWhichId(String str) {
		String count = "";
		try (Statement stm = con.createStatement();
				ResultSet rslt = stm.executeQuery("select " + COLUMN_SECTION_SETID + " from " + TABLE_SECTION
						+ " where " + COLUMN_SECTION_NAME + " = " + "'" + str + "'" + ";")) {

			while (rslt.next()) {
				count = rslt.getString(COLUMN_WORD_SETID);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return count;
	}

	public void addWordsToSet(String setNumber, String word1, String word2) {
		try (PreparedStatement stm = con.prepareStatement("insert into " + TABLE_WORD + " (" + COLUMN_WORD_SETID + ", "
				+ COLUMN_WORD_WORD1 + ", " + COLUMN_WORD_WORD2 + ") values ('" + getWhichId(setNumber) + "', '" + word1
				+ "', '" + word2 + "')");) {
			stm.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void addSet(String name, String firstColumnName, String secondColumnName) {
		try (PreparedStatement stm = con.prepareStatement("insert into " + TABLE_SECTION + " (" + COLUMN_SECTION_NAME
				+ ", " + COLUMN_SECTION_FIRSTCOLUMNNAME + ", " + COLUMN_SECTION_SECONDCOLUMNNAME + ") values ('" + name
				+ "', '" + firstColumnName + "', '" + secondColumnName + "')");) {
			stm.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void closeCon() {
		try {
			if (con != null) {
				con.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
