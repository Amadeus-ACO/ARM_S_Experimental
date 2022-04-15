package client.mainWindow.tab;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Tab;
import javafx.scene.input.Dragboard;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Region;

import java.io.IOException;
import java.util.Objects;

public class TabView {
    private Tab tab;

    public TabView(TabController tabController) {
    }

    void initTab(Tab tab) {
        this.tab = tab;

    }

//    public void openPage(String type) {
//        switch (type) {
//            case "Main" -> {
//                tab.setText("Главная страница");
//
//                //System.out.println(mainPageController.getRoot());
//                //pageTab.setContent(mainPageController.getRoot());
//            }
//            case "AOVT" -> {
//                Parent root = null;
//                try {
//                    root = FXMLLoader.load(Objects.requireNonNull(getClass().getClassLoader().getResource("aovt_start.fxml")));
//                    pageTab.setText("AOVT HUET");
//                    ((AnchorPane) root).setMaxWidth(Region.USE_COMPUTED_SIZE);
//                    ((AnchorPane) root).setMaxHeight(Region.USE_COMPUTED_SIZE);
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//                pageTab.setContent(root);
//            }
//        }
//    }
}
