package INSTRUMENTS.SUBCLASS;

import INSTRUMENTS.Const;
import INSTRUMENTS.DIVISION_BASED.Division;
import javafx.event.ActionEvent;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.util.Pair;

import java.util.ArrayList;

public class DivisionBlock {
    Label minus = new Label("-");
    Division instrument;

    public int getMaxDigit() {
        return maxDigit;
    }

    public void setMaxDigit(int maxDigit) {
        this.maxDigit = maxDigit;
    }

    int maxDigit;

    public ArrayList<FlowPane> subtrahend = new ArrayList<>();
    public ArrayList<FlowPane> blockResult = new ArrayList<>();
    public ArrayList<SwitchButton> underBlockResult = new ArrayList<>();

    Canvas canvas = new Canvas();
    Button addDigitSubtrahend = new Button("+");
    Button removeDigitSubtrahend = new Button("-");

    Button addDigitBlockResult = new Button("+");
    Button removeDigitBlockResult = new Button("-");
    /**
     * row, column
     */
    public Pair<Integer,Integer> firstCoordinate;
    private boolean canChange = true;

    public DivisionBlock(int maxDigit, Division instrument){
        this.instrument = instrument;
        this.maxDigit = maxDigit;
        this.addDigitBlockResult.setOnAction(this::addDigitBlockResult);
        this.removeDigitBlockResult.setOnAction(this::removeDigitBlockResult);

        this.addDigitSubtrahend.setOnAction(this::addDigitSubtrahend);
        this.removeDigitSubtrahend.setOnAction(this::removeDigitSubtrahend);

        subtrahend.add(createFlow());
        blockResult.add(createFlow());

        SwitchButton sw = new SwitchButton(this);
        underBlockResult.add(sw);

        canvas.setHeight(5);
        canvas.setWidth(subtrahend.size()*subtrahend.get(0).getPrefWidth());
        GraphicsContext gc = canvas.getGraphicsContext2D();
        gc.setStroke(Color.FUCHSIA);
        gc.setLineWidth(5);
        gc.strokeLine(0, 2, (int) canvas.getWidth(), 2);
    }


    private void removeDigitSubtrahend(ActionEvent actionEvent) {
        if(this.subtrahend.size() == 1 || !canChange) return;
        FlowPane fp = this.subtrahend.get(this.subtrahend.size()-1);

        GridPane gridPane = ((GridPane) this.subtrahend.get(0).getParent());
        gridPane.getChildren().remove(fp);
        this.subtrahend.remove(fp);
        this.resize_line();

    }

    private void addDigitSubtrahend(ActionEvent actionEvent) {

        if(this.subtrahend.size() == this.maxDigit || !canChange) return;
        FlowPane fp = createFlow();

        this.subtrahend.add(fp);
        GridPane gridPane = ((GridPane) this.subtrahend.get(0).getParent());
        gridPane.add(fp,GridPane.getColumnIndex(this.subtrahend.get(this.subtrahend.size()-2)) + 1, this.firstCoordinate.getKey());

        this.resize_line();
    }

    private void removeDigitBlockResult(ActionEvent actionEvent) {
        if(this.blockResult.size() == 1 || !canChange) return;
        FlowPane fp = this.blockResult.get(this.blockResult.size()-1);

        GridPane gridPane = ((GridPane) this.blockResult.get(0).getParent());
        gridPane.getChildren().remove(fp);

        SwitchButton sb = this.underBlockResult.get(this.underBlockResult.size()-1);

        gridPane.getChildren().remove(sb.get());

        this.blockResult.remove(fp);
        this.underBlockResult.remove(sb);
    }

    private void addDigitBlockResult(ActionEvent actionEvent) {
        if(this.blockResult.size() == this.maxDigit || !canChange) return;
        FlowPane fp = createFlow();

        this.blockResult.add(fp);
        GridPane gridPane = ((GridPane) this.blockResult.get(0).getParent());
        gridPane.add(fp,GridPane.getColumnIndex(this.blockResult.get(this.blockResult.size()-2)) + 1,this.firstCoordinate.getKey()+2);


        SwitchButton sb = new SwitchButton(this);
        underBlockResult.add(sb);
        gridPane.add(sb.get(),GridPane.getColumnIndex(this.underBlockResult.get(this.underBlockResult.size()-2).get()) + 1,this.firstCoordinate.getKey()+3);
        sb.setCoordinate(this.firstCoordinate.getKey()+3,GridPane.getColumnIndex(this.underBlockResult.get(this.underBlockResult.size()-2).get()) + 1);
    }

    public void resize_line() {
        canvas.setWidth(subtrahend.size()*subtrahend.get(0).getWidth());
        canvas.getGraphicsContext2D().strokeLine(0, 2,
                subtrahend.size()*subtrahend.get(0).getWidth(), 2);
        GridPane.setColumnSpan(canvas, subtrahend.size());
    }

    public FlowPane createFlow() {
        FlowPane pane = new FlowPane();

        pane.setMaxWidth(35);
        pane.setPrefWidth(pane.getMaxWidth());

        LimitedTextField textField = new LimitedTextField();
        textField.setMaxLength(1);
        textField.setPrefWidth(30);
        textField.setPrefWidth(textField.getPrefWidth());

        textField.textProperty().addListener((observableValue, s, t1) -> {
            if (!t1.matches(Const.regex_bin)) {
                textField.setText("");
            }
        });
        pane.getChildren().addAll(new Text(" "), textField);
        return pane;
    }

    public void place() {
        for (int i = 0; i < this.subtrahend.size(); i++) {
            this.instrument.digitNet.add(this.subtrahend.get(i),this.firstCoordinate.getValue() + i, this.firstCoordinate.getKey());
        }
        this.instrument.digitNet.add(minus,this.firstCoordinate.getValue()-1,this.firstCoordinate.getKey());
        this.instrument.digitNet.add(removeDigitSubtrahend,0,this.firstCoordinate.getKey());
        this.instrument.digitNet.add(addDigitSubtrahend,1,this.firstCoordinate.getKey());

        this.instrument.digitNet.add(removeDigitBlockResult,0,this.firstCoordinate.getKey()+2);
        this.instrument.digitNet.add(addDigitBlockResult,1,this.firstCoordinate.getKey()+2);

        this.instrument.digitNet.add(canvas, this.firstCoordinate.getValue(), this.firstCoordinate.getKey() + 1,subtrahend.size() ,1);

        for (int i = 0; i < this.blockResult.size(); i++) {
            this.instrument. digitNet.add(this.blockResult.get(i),this.firstCoordinate.getValue()+i, this.firstCoordinate.getKey()+2);
            this.instrument.digitNet.add(this.underBlockResult.get(i).get(),this.firstCoordinate.getValue()+i,this.firstCoordinate.getKey()+3);
            this.underBlockResult.get(i).setCoordinate(this.firstCoordinate.getKey()+3, this.firstCoordinate.getValue()+i);
        }
    }

    public void placeUnderBlock(){
        for (int i = 0; i < this.blockResult.size(); i++) {
            this.instrument.digitNet.add(this.underBlockResult.get(i).get(),this.firstCoordinate.getValue()+i,this.firstCoordinate.getKey()+3);
            this.underBlockResult.get(i).setCoordinate(this.firstCoordinate.getKey()+3, this.firstCoordinate.getValue()+i);
        }
    }


    public void createNewBlock(SwitchButton switchButton) {
        GridPane gridPane = ((GridPane) switchButton.get().getParent());

        for (SwitchButton s: this.underBlockResult) {
            gridPane.getChildren().remove(s.get());
        }

        DivisionBlock db = new DivisionBlock(this.instrument.digit_dem - switchButton.column + 3, this.instrument);
        db.firstCoordinate = new Pair<>(switchButton.row, switchButton.column);
        db.place();
        this.instrument.divisionBlocks.add(db);
        this.block();
    }

    private void block() {
        this.canChange = false;
    }
    public void unblock() {
        this.canChange = true;
    }

    public void remove() {
        this.underBlockResult.forEach(sw -> this.instrument.digitNet.getChildren().remove(sw.get()));

        this.instrument.digitNet.getChildren().remove(this.canvas);
        this.instrument.digitNet.getChildren().remove(this.minus);
        this.instrument.digitNet.getChildren().remove(this.removeDigitBlockResult);
        this.instrument.digitNet.getChildren().remove(this.addDigitBlockResult);
        this.instrument.digitNet.getChildren().remove(this.removeDigitSubtrahend);
        this.instrument.digitNet.getChildren().remove(this.addDigitSubtrahend);


        this.subtrahend.forEach(elem -> this.instrument.digitNet.getChildren().remove(elem));
        this.blockResult.forEach(elem -> this.instrument.digitNet.getChildren().remove(elem));

    }


}
