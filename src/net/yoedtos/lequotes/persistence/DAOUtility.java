package net.yoedtos.lequotes.persistence;

import java.io.File;
import java.sql.SQLException;

import org.h2.tools.Recover;
import org.h2.tools.RunScript;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DAOUtility {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(DAOUtility.class);
	
	private DAOUtility() {

	}

	public static void recoverDB(File dir) {
		DbConfig config = new DbConfig();
		String[] token = config.getUrl().split("[:/]");

		System.out.print("Recover process ...");
		try {
			Recover.execute(token[2], token[3]);			
		} catch (SQLException e) {
			LOGGER.error(e.getMessage());
			System.out.println("\rRecover process: Fail!");
			System.exit(1);
		}
		System.out.println("\rRecover process: Ok!");
		
		System.out.println("Backuping DB to " + dir);
		String name = token[2] + "/" + token[3];
		File dbFile = new File(name + ".h2.db");
		
		try {
			if(dbFile.renameTo(new File(dir + "/"+ dbFile.getName()))) {
				System.out.println("File backuped successfully!");
			} 
		} catch (SecurityException e) {
			LOGGER.error(e.getMessage());
			System.out.println("Failed to backup file!");
			System.exit(1);
		}
			
		System.out.print("Recreate database ...");
		String fileName = name + ".h2.sql";
		try {
			RunScript.execute(config.getUrl(), config.getUser(), config.getPassword(), fileName, null, false);
			File sqlFile = new File(fileName);
			sqlFile.deleteOnExit();
		} catch (SQLException e) {
			LOGGER.error(e.getMessage());
			System.out.println("\rRecreate database: Fail!");
			System.exit(1);
		}
		System.out.println("\rRecreate database: Ok!");
	}
}
