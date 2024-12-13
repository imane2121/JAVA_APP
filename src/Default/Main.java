package Default;

import View.EmployeView;
import DAO.EmployeDAOimpl;
import Model.EmployeModel;
import Controller.EmployeController;

public class Main {

	public static void main(String[] args) {
		
		EmployeView view = new EmployeView();
		EmployeDAOimpl dao = new EmployeDAOimpl();
		EmployeModel model = new EmployeModel(dao);
		new EmployeController(view,model);
		view.setVisible(true);

	}

}
