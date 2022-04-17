package client.mainWindow.pages.trainingPage;

import LogisimFX.IconsManager;
import STARTERS.Main;
import client.mainWindow.MainController;
import client.mainWindow.pages.Pages;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;

public class TrainerPageController {

    @FXML
    private HBox buttonsPageHBox;

    @FXML
    public void initialize(){

        CustomButton aovt = new CustomButton(Main.icon_imgvw);
        aovt.setOnAction(event -> MainController.getInstance().requestOpenPage(Pages.AOVT_PAGE));

        CustomButton alg = new CustomButton(AmadeyLogicGame.Util.IconsManager.AmadayLogicGame_imgvw);
        alg.setOnAction(event -> MainController.getInstance().requestOpenPage(Pages.ALG_PAGE, "training"));

        CustomButton logisimFX = new CustomButton(IconsManager.LogisimFX_imgvw);
        logisimFX.setOnAction(event -> MainController.getInstance().requestOpenPage(Pages.LOGISIM_PAGE));

        buttonsPageHBox.getChildren().addAll(aovt, alg, logisimFX);

    }

    private static class CustomButton extends Button{

        int prefWidth = 25;
        int prefHeight = 25;

        public CustomButton(ImageView icon){

            super();
            setPrefSize(prefWidth,prefHeight);
            setMinSize(prefWidth,prefHeight);
            setMaxSize(prefWidth,prefHeight);
            graphicProperty().setValue(icon);

        }

        public CustomButton(int prefWidth, int prefHeight, ImageView icon){

            super();
            setPrefSize(prefWidth,prefHeight);
            setMinSize(prefWidth,prefHeight);
            setMaxSize(prefWidth,prefHeight);
            graphicProperty().setValue(icon);

        }

    }

}
