package pe.edu.lamolina.report.component;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

import pe.edu.lamolina.gradotitulo.entity.SCGFacultadEntity;

public class ReportExcelComponent {
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
