package pe.edu.lamolina.gradotitulo.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import pe.edu.lamolina.gradotitulo.entity.SCGFacultadEntity;
import pe.edu.lamolina.gradotitulo.service.FacultadService;
import pe.edu.lamolina.util.ExcelUtils;

@Controller
@RequestMapping("excel/*")
public class ExcelController {

	@Autowired
	private FacultadService facultadService;

	@RequestMapping(value = "export")
	public ModelAndView save(HttpServletResponse response) throws IOException {
		SCGFacultadEntity facultad = new SCGFacultadEntity();
		facultad.setFlagEstado("Activo");
		List<SCGFacultadEntity> falutadList = facultadService.getListFacultad(facultad);
		List<List<String>> employeeDetailsMasterList = convertToListOfList(falutadList);
		List<String> myReportHeader = getTrainingReportHeader();
		employeeDetailsMasterList.add(0, myReportHeader);
		HSSFWorkbook workBook = ExcelUtils.prepareWorkBook(response, employeeDetailsMasterList, "Test Report");
		ExcelUtils.generateReport(response, workBook, "Test Report");
		return null;
	}

	private List<List<String>> convertToListOfList(List<SCGFacultadEntity> employeeList) {

		List<List<String>> masterList = new ArrayList<List<String>>();
		ListIterator<SCGFacultadEntity> iterator = employeeList.listIterator();
		while (iterator.hasNext()) {
			SCGFacultadEntity emp = iterator.next();
			List<String> empDetails = new ArrayList<String>();
			empDetails.add(iterator.previousIndex() + 1 + "");
			empDetails.add(emp.getTextoNombreEspanol());
			masterList.add(empDetails);
		}
		return masterList;
	}

	private List<String> getTrainingReportHeader() {
		List<String> headerList = new ArrayList<String>(15);
		headerList.add("Sr No");
		headerList.add("Nombre");
		return headerList;
	}

}
