package client.mainWindow.pages.labPages.labPage.algorithm;

import AmadeyLogicGame.AmadeyLogicGame;
import LogisimFX.Startup;
import LogisimFX.newgui.FrameManager;
import client.mainWindow.pages.labPages.labPage.base.stage.StageController;

import entity.work.GivenTask;
import entity.work.solution.Algorithm;

import javafx.fxml.FXMLLoader;
import javafx.scene.control.ScrollPane;

import java.io.IOException;
import java.util.Objects;

public class AlgorithmController extends StageController {

    private GivenTask givenTask;

    public AlgorithmController() {
        super();

        givenTask = new GivenTask();
        Algorithm algorithm = new Algorithm();
        algorithm.setType(Algorithm.TYPE.LOVT);
        givenTask.setAlgorithm(algorithm);

        requestShowAlgorithm(givenTask);

    }

    void requestShowAlgorithm(GivenTask givenTask) {

        switch (givenTask.getAlgorithm().getType()){
            case AOVT: {
                try {
                    ((ScrollPane)getRoot()).setContent(FXMLLoader.load(Objects.requireNonNull(getClass().getClassLoader().getResource("aovt_start.fxml"))));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            case ALG: {
                ((ScrollPane)getRoot()).setContent(AmadeyLogicGame.load(new String[]{"Test"}).getRoot());
            }
            case LOVT: {
                Startup startup = Startup.parseArgs(new String[0]);
                startup.setOnSucceeded(event ->  ((ScrollPane)getRoot()).setContent(FrameManager.getScene().getRoot()));
                startup.run();

            }
        }

    }

}
