package Main;

import Views.MainFrame;
import Controllers.MainController;

public class MainApp {
    public static void main(String[] args) {
            MainFrame mainFrame = new MainFrame();
            MainController controller = new MainController(mainFrame);
            mainFrame.setVisible(true);
    }
}
