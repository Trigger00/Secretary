package pe.edu.lamolina.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;

/**
 * @author emiprav
 * 
 */
public class ExcelUtils {

	private static final String REPORT_EXCEL_SHEET_NAME_DEFAULT = "My Report";
	private static final String DEFAULT_FONT_FACE = "VERDANA";

	@SuppressWarnings("rawtypes")
	/**
	 * Reads an excel file from input stream using POI api
	 * @param inputStream
	 * @return
	 * @throws IOException
	 */
	public static List<List<String>> processExcel(InputStream inputStream)
			throws IOException {

		List<List<String>> sheetData = new ArrayList<List<String>>();
		HSSFWorkbook workbook = new HSSFWorkbook(inputStream);
		//
		// Get the first sheet on the workbook.
		//
		HSSFSheet sheet = workbook.getSheetAt(0);

		//
		// When we have a sheet object in hand we can iterator on
		// each sheet's rows and on each row's cells. We store the
		// data read on an ArrayList so that we can printed the
		// content of the excel to the console.
		//
		Iterator rows = sheet.rowIterator();

		while (rows.hasNext()) {
			HSSFRow row = (HSSFRow) rows.next();
			List<String> rowData = new ArrayList<String>();

			/*
			 * Iterator cells = row.cellIterator(); while (cells.hasNext()) {
			 * HSSFCell cell = (HSSFCell) cells.next();
			 * rowData.add(getCellValue(cell)); }
			 */
			for (int cn = 0; cn < row.getLastCellNum(); cn++) {
				// If the cell is missing from the file, generate a blank one
				Cell cell = row.getCell(cn,
						org.apache.poi.ss.usermodel.Row.RETURN_BLANK_AS_NULL);
				// Print the cell for debugging
				if (null == cell) {
					System.out.println("CELL: " + cn + " --> " + cell);
					rowData.add(null);
				} else {
					System.out.println("CELL: " + cn + " --> "
							+ cell.toString());
					rowData.add(cell.toString());
				}
			}
			sheetData.add(rowData);
		}
		showExcelData(sheetData);
		return sheetData;
	}

	/**
	 * Returns the value in the Cell of different type - Numeric, String
	 * 
	 * @param cell
	 * @return
	 */
	@SuppressWarnings("unused")
	private static String getCellValue(HSSFCell cell) {

		double fvalue;
		String svalue = "";
		int cellType = cell.getCellType();
		if (cellType == HSSFCell.CELL_TYPE_NUMERIC) {
			fvalue = (float) cell.getNumericCellValue();
			svalue = Double.toString(fvalue);
		} else if (cellType == HSSFCell.CELL_TYPE_STRING) {

			try {
				svalue = cell.getStringCellValue();
			} catch (Exception e) {
				fvalue = (float) cell.getNumericCellValue();
				svalue = Double.toString(fvalue);
			}
		}
		return svalue;
	}

	@SuppressWarnings("rawtypes")
	private static void showExcelData(List sheetData) {
		//
		// Iterates the data and print it out to the console.
		//
		for (int i = 0; i < sheetData.size(); i++) {
			List list = (List) sheetData.get(i);
			for (int j = 0; j < list.size(); j++) {
				String cell = (String) list.get(j);

				System.out.print(cell);
				if (j < list.size() - 1) {
					System.out.print(", ");
				}
			}
			System.out.println("");
		}
	}

	/**
	 * Generates report in Excel format
	 * 
	 * @param response
	 * @param reportList
	 * @param workSheetName
	 */
	public static HSSFWorkbook prepareWorkBook(HttpServletResponse response,
			List<List<String>> reportList, String workSheetName) {

		final String METHOD_NAME = "convertListToExcel";
		System.out.println("Entered " + METHOD_NAME);

		if (StringUtils.isNullOrBlank(workSheetName)) {
			workSheetName = REPORT_EXCEL_SHEET_NAME_DEFAULT;
		}
		int lengthOfColumns = ((List<String>) reportList.get(0)).size();

		// Do the POI Things to generate the Excel File create a new workbook
		HSSFWorkbook workBook = new HSSFWorkbook();
		HSSFSheet sheet = workBook.createSheet(workSheetName);

		/*******************************************
		 * This Part Contains The Cell Formatting Stuff.
		 * ********************************************/
		// Create a Font For Header
		HSSFFont headerFont = workBook.createFont();
		headerFont.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
		headerFont.setFontName(DEFAULT_FONT_FACE);

		// create a style for the header Columns
		HSSFCellStyle columnHeaderStyle = workBook.createCellStyle();
		// columnHeaderStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND); //
		// Call This method before calling below one.
		// columnHeaderStyle.setFillForegroundColor(HSSFColor.GREY_50_PERCENT.index);
		columnHeaderStyle.setFont(headerFont);

		// Create a Font For Data Rows
		HSSFFont rowFont = workBook.createFont();
		rowFont.setFontName(DEFAULT_FONT_FACE);

		// create a style for the Records In Next Rows
		HSSFCellStyle rowCellStyle = workBook.createCellStyle();
		rowCellStyle.setFont(rowFont);
		/**********************************************/

		HSSFRow[] headerRow = new HSSFRow[reportList.size()];
		ListIterator<List<String>> outerListIterator = reportList
				.listIterator();

		// Create Header Row
		if (outerListIterator.hasNext()) {

			int rowIndex = outerListIterator.nextIndex();
			List<String> innerList = outerListIterator.next();

			// create header rows: Write the column header in Very First(0th)
			// Row Of Excel File
			headerRow[rowIndex] = sheet.createRow((short) rowIndex);
			HSSFCell[] headerColumns = new HSSFCell[lengthOfColumns];

			// Create Data for the Header Row
			ListIterator<String> innerListIterator = innerList.listIterator();
			while (innerListIterator.hasNext()) {
				int columnIndex = innerListIterator.nextIndex();
				headerColumns[columnIndex] = headerRow[rowIndex]
						.createCell(columnIndex);

				String cellValue = innerListIterator.next();
				headerColumns[columnIndex].setCellValue(cellValue);
				headerColumns[columnIndex].setCellStyle(columnHeaderStyle);
			}
		}

		// Create other Row(s)
		while (outerListIterator.hasNext()) {

			int rowIndex = outerListIterator.nextIndex();
			List<String> innerList = outerListIterator.next();

			headerRow[rowIndex] = sheet.createRow((short) rowIndex);
			HSSFCell[] headerColumns = new HSSFCell[innerList.size()];

			// Create Data for the Header Row
			ListIterator<String> innerListIterator = innerList.listIterator();
			while (innerListIterator.hasNext()) {
				int columnIndex = innerListIterator.nextIndex();
				headerColumns[columnIndex] = headerRow[rowIndex]
						.createCell(columnIndex);

				String cellValue = innerListIterator.next();
				headerColumns[columnIndex].setCellValue(cellValue);
				headerColumns[columnIndex].setCellStyle(rowCellStyle);
			}
		}
		return workBook;
	}

	/**
	 * Generates the report in excel format on the HttpServletResponse object
	 * 
	 * @param response
	 * @param workBook
	 * @throws IOException 
	 */
	public static void generateReport(HttpServletResponse response,
			HSSFWorkbook workBook, String workSheetName) throws IOException {

		// set specific response content type for xls files.
		response.setContentType("application/vnd.ms-excel; charset=utf-8");
		response.setHeader("Content-Disposition", "attachment; filename="
				+ workSheetName + ".xls");

		/*
		 * With this we let the browser know what the default name should be for
		 * the user to save this file.
		 */
		ServletOutputStream servletOutputStream = response.getOutputStream();
		workBook.write(servletOutputStream);
	}

}
