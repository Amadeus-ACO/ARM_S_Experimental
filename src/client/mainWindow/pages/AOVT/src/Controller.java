import INSTRUMENTS.Instrument;
import INSTRUMENTS.Step;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import javafx.fxml.FXML;
import javafx.scene.Cursor;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

public class Controller {

    @FXML
    private Cursor cursor;

    @FXML
    private TextField step_index_field;

    @FXML
    private ScrollPane main_scroll;

    @FXML
    private VBox main_vbox;

    @FXML
    void addStepToEnd() {
        System.out.println(main_vbox.getChildren().size());
        main_vbox.getChildren().add(new Step(main_vbox.getChildren().size()+1, false).step_vbox);
    }

    @FXML
    void insertByIndex() {
        if (Integer.parseInt(step_index_field.getText()) <= main_vbox.getChildren().size()+1 && Integer.parseInt(step_index_field.getText())>0 ) {
            main_vbox.getChildren().add(Integer.parseInt(step_index_field.getText())-1, new Step(Integer.parseInt(step_index_field.getText()), true).step_vbox);
        }

    }

    public void convertToJson(){

        JsonObject root = new JsonObject();

        for(Step step: Step.steps){
            JsonArray instruments = new JsonArray();
            for(Instrument instrument: step.instruments){
                instruments.add(instrument.getJson());
            }
            root.add(step.choiceBox.getValue(), instruments);
        }

        System.out.println(root.toString());

    }

}
