package sachin.codejam;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 *
 * @author Sachin
 */
public class ExcelManager {

	private XSSFSheet sheet;
	private XSSFWorkbook workbook;

	public ExcelManager() {
		FileInputStream f = null;
		try {
			f = new FileInputStream(new File("Resources/Config.xlsx"));
			this.workbook = new XSSFWorkbook(f);
			this.sheet = workbook.getSheetAt(0);
		} catch (FileNotFoundException ex) {
			Logger.getLogger(ExcelManager.class.getName()).log(Level.WARN, null, ex);
		} catch (IOException ex) {
			Logger.getLogger(ExcelManager.class.getName()).log(Level.WARN, null, ex);
		} finally {
			try {
				f.close();
			} catch (IOException ex) {
				Logger.getLogger(ExcelManager.class.getName()).log(Level.WARN, null, ex);
			}
		}
	}

	/**
	 * This method is used to kill all the processes running all web driver
	 * server if any is running as system processes.
	 *
	 * @return map containing of config keys and value
	 **/
	public Map<String, String> readConfigData() {
		Iterator<Row> rowIterator = sheet.rowIterator();
		DataFormatter df = new DataFormatter();
		Map<String, String> map = new HashMap<>();
		while (rowIterator.hasNext()) {
			Row row = rowIterator.next();
			if (row.getRowNum() != 0) {
				map.put(df.formatCellValue(row.getCell(0)), df.formatCellValue(row.getCell(1)).toString());
			}
		}
		try {
			workbook.close();
		} catch (IOException ex) {
			Logger.getLogger(ExcelManager.class.getName()).log(Level.WARN, null, ex);
		}
		return map;
	}
}
