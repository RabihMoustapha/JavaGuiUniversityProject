package Controllers;

import Views.MainFrame;

public class MainController {
    private MainFrame view;

    public MainController(MainFrame view) {
        this.view = view;
        initController();
    }

    private void initController() {
        view.getContactsButton().addActionListener(e -> openContactsWindow());
        view.getGroupsButton().addActionListener(e -> openGroupsWindow());
    }

    private void openContactsWindow() {
    	
    }

    private void openGroupsWindow() {
    	
    }
}
