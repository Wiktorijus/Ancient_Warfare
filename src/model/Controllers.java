package model;

import javafx.fxml.FXMLLoader;


public class Controllers {
	
    private static Controller mainController;

    public static Controller getMainController() {
        return mainController;
    }

    public static void setMainController(Controller mainController) {
        Controllers.mainController = mainController;
    }

    public static void setMainControllerLoader(FXMLLoader mainControllerLoader) {
        Controllers.mainController = mainControllerLoader.getController();
    }
}
