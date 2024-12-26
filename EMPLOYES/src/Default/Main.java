package Default;

import Controller.EmployeController;
import Controller.HolidayController;
import DAO.EmployeDAOImpl;
import DAO.HolidayDAOImpl;
import Model.EmployeModel;
import Model.HolidayModel;
import View.EmployeView;
import View.HolidayView;
import View.ManagementInterfaces;

public class Main {
	public static void main(String[] args) {
		EmployeView view = new EmployeView();
		HolidayView view2 =new HolidayView();
		EmployeDAOImpl dao = new EmployeDAOImpl();
		HolidayDAOImpl dao2 = new HolidayDAOImpl();
		EmployeModel model = new EmployeModel(dao);
		HolidayModel model2 = new HolidayModel(dao2);
		new EmployeController(view,model);
		new HolidayController(view2,model2);
		ManagementInterfaces combinedView = new ManagementInterfaces(view,view2);
		combinedView.setVisible(true);
		

	}
}
