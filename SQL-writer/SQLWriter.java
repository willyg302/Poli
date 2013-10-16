import java.io.File;
import java.io.*;

import org.tmatesoft.sqljet.core.SqlJetException;
import org.tmatesoft.sqljet.core.SqlJetTransactionMode;
import org.tmatesoft.sqljet.core.schema.ISqlJetIndexDef;
import org.tmatesoft.sqljet.core.schema.ISqlJetTableDef;
import org.tmatesoft.sqljet.core.table.ISqlJetCursor;
import org.tmatesoft.sqljet.core.table.ISqlJetTable;
import org.tmatesoft.sqljet.core.table.ISqlJetTransaction;
import org.tmatesoft.sqljet.core.table.SqlJetDb;

public class SQLWriter {
	public static void main(String args[]) throws SqlJetException  {
		if (args.length != 1) {
			System.out.println("Usage: java SQLWriter [name of file to convert]");
			return;
		}
		String inFile = args[0];

		// Open and flush DB File
		File dbFile = new File(inFile + ".db");
		dbFile.delete();
		SqlJetDb db = SqlJetDb.open(dbFile, true);

		// Set basic DB options
		db.getOptions().setAutovacuum(true);
		db.runTransaction(new ISqlJetTransaction() {
			public Object run(SqlJetDb db) throws SqlJetException {
				db.getOptions().setUserVersion(1);
				return true;
			}
		}, SqlJetTransactionMode.WRITE);

		// Create tables
		db.beginTransaction(SqlJetTransactionMode.WRITE);
		try {
			String metadataCT = "CREATE TABLE \"android_metadata\" (\"locale\" TEXT DEFAULT \'en_US\')";
			//String sqliteCT = "CREATE TABLE sqlite_sequence(name,seq)";
			String tilesCT = "CREATE TABLE tiles (_id INTEGER PRIMARY KEY, type TEXT, data TEXT, bookmarked BOOLEAN, hashtag TEXT, candidate TEXT, created DATE)";
			db.createTable(metadataCT);
			//db.createTable(sqliteCT);
			db.createTable(tilesCT);
		} finally {
			db.commit();
		}

		// Instantiate metadata
		db.beginTransaction(SqlJetTransactionMode.WRITE);
		try {
			ISqlJetTable table = db.getTable("android_metadata");
			table.insert("en_US");
		} finally {
			db.commit();
		}

		// Populate TILES table
		db.beginTransaction(SqlJetTransactionMode.WRITE);
		try (BufferedReader br = new BufferedReader(new FileReader(inFile + ".txt"))) {
			String line;
			int lineCount = 0;
			ISqlJetTable table = db.getTable("tiles");
			while ((line = br.readLine()) != null) {
				lineCount++;
				String[] parts = line.split("\\|");
				table.insert(lineCount, parts[0], parts[1], false, parts[2], parts[3], parts[4]);
			}
		} catch (SqlJetException | IOException e) {
			//
		} finally {
			db.commit();
		}

		db.close();
	}
}