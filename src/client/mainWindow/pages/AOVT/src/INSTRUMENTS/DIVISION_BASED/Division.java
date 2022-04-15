package INSTRUMENTS.DIVISION_BASED;

import INSTRUMENTS.Const;
import INSTRUMENTS.Instrument;
import INSTRUMENTS.MULTIPLICATION_BASED.MultiplicationInstrument;
import INSTRUMENTS.SUBCLASS.DivisionBlock;
import INSTRUMENTS.SUBCLASS.LimitedTextField;
import INSTRUMENTS.Step;
import javafx.event.ActionEvent;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.control.Label;
import javafx.util.Pair;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Optional;

public class Division extends Instrument {
    //TODO: поведение внутри строки
    public int digit_dem;
    public LinkedList<FlowPane> fields = new LinkedList<>();

    public ArrayList<FlowPane> demList = new ArrayList<>();
    public ArrayList<FlowPane> detList = new ArrayList<>();
    public ArrayList<FlowPane> Result_List = new ArrayList<>();
    public ArrayList<DivisionBlock> divisionBlocks = new ArrayList<>();
    public GridPane digitPane = new GridPane();
    public GridPane dividerQuotientDigit = new GridPane();
    private final LimitedTextField sep_field_dem;
    private final LimitedTextField sep_field_det;
    private final LimitedTextField sep_field_cha;

    /**
     * @param index - индекс
     * @param step - шаг
     * @param digit_dem - количество разрядов делимого
     * @param digit_det - количество разрядов делителя
     */
    public Division(int index, Step step, int digit_dem, int digit_det) {
        super("Деление в столбик", index, step);

        this.digitNet = new GridPane();
        this.digitNet.setGridLinesVisible(true);

        this.digit_dem = digit_dem;

        //---------------------------------------------------------DIGIT MANIPULATORS-----------------------------------
        Label dividend = new Label("Делимое: ");
        Label divider = new Label(" Делитель: ");
        Label quotient = new Label(" Частное: ");

        Button button_add_dem_digit = new Button("+");
        Button button_add_det_digit = new Button("+");
        Button button_add_cha_digit = new Button("+");
        Button button_rem_dem_digit = new Button("-");
        Button button_rem_det_digit = new Button("-");
        Button button_rem_cha_digit = new Button("-");

        button_add_dem_digit.setOnAction(this::add_dem_digit);
        button_rem_dem_digit.setOnAction(this::rem_dem_digit);
        button_add_det_digit.setOnAction(this::add_det_digit);
        button_rem_det_digit.setOnAction(this::rem_det_digit);
        button_add_cha_digit.setOnAction(this::add_cha_digit);
        button_rem_cha_digit.setOnAction(this::rem_cha_digit);

        Button sep_dem = new Button("Отделить");
        Button sep_det = new Button("Отделить");
        Button sep_cha = new Button("Отделить");
        
        sep_dem.setOnAction(this::sep_dem);
        sep_det.setOnAction(this::sep_det);
        sep_cha.setOnAction(this::sep_cha);

        this.sep_field_dem = new LimitedTextField();
        this.sep_field_det = new LimitedTextField();
        this.sep_field_cha = new LimitedTextField();

        VBox innerVbox = new VBox();


        FlowPane pane = new FlowPane();//Pane для заголовочной части манипуляции разрядами и самой сетки
        //pane.setGridLinesVisible(true);

         //Добавление заголовочной части

        pane.getChildren().addAll(dividend,button_rem_dem_digit, button_add_dem_digit, sep_field_dem,sep_dem,divider,
                button_rem_det_digit, button_add_det_digit, sep_field_det,sep_det,quotient,button_rem_cha_digit,
                button_add_cha_digit,sep_field_cha,sep_cha);

        //---------------------------------------------------------FLOW PANES-------------------------------------------
        for (int i = 0; i < digit_dem; i++) {
            FlowPane f = createFlow();
            this.digitNet.add( f,i + 3,1);
            demList.add(f);
            fields.add(f);
        }

        //-------------------------------------------------------------------------------------------------------------
        for (int i = 0; i < digit_det; i++) {
            FlowPane f = createFlow();
            this.dividerQuotientDigit.add( f,i + 1,0);
            detList.add(f);
        }


        Result_List.add(createFlow());
        this.dividerQuotientDigit.add(Result_List.get(Result_List.size()-1),1,2);
        //-------------------------------------------------------------------------------------------------------------


        DivisionBlock divisionBlock = new DivisionBlock(digit_dem, this);

        divisionBlock.firstCoordinate = new Pair<>(3,3);
        divisionBlock.place();
        divisionBlocks.add(divisionBlock);

        //---------------------------------------------------------DELIMITERS-------------------------------------------
        GraphicsContext gc;
        Canvas vertical_delimiter = new Canvas();
        vertical_delimiter.setWidth(5);
        vertical_delimiter.setHeight(fields.get(0).getPrefHeight() * 2 + 10);
        gc = vertical_delimiter.getGraphicsContext2D();
        gc.setStroke(Color.BLACK);
        gc.setLineWidth(vertical_delimiter.getWidth());
        gc.strokeLine(0, 0, 0, vertical_delimiter.getHeight());
        this.dividerQuotientDigit.add(vertical_delimiter, 0, 0, 1, 3);


        Canvas horizontal_delimiter = new Canvas();
        horizontal_delimiter.setWidth(digit_det * fields.get(0).getPrefWidth());
        horizontal_delimiter.setHeight(5);
        gc = horizontal_delimiter.getGraphicsContext2D();
        gc.setStroke(Color.BLACK);
        gc.setLineWidth(horizontal_delimiter.getHeight());
        gc.strokeLine(0, 0, horizontal_delimiter.getWidth(), 0);
        this.dividerQuotientDigit.add(horizontal_delimiter, 1, 1, digit_det, 1);

        //--------------------------------------------------------------------------------------------------------------

        Button deleteBlockButton = new Button("Удалить блок");
        deleteBlockButton.setOnAction(this::deleteBlock);

        FlowPane pane1 = new FlowPane();
        pane1.getChildren().add(deleteBlockButton);

        digitNet.setMinWidth(GridPane.USE_PREF_SIZE);
        digitNet.setMinHeight(GridPane.USE_PREF_SIZE);
        digitNet.setPrefWidth(GridPane.USE_COMPUTED_SIZE);
        digitNet.setPrefHeight(GridPane.USE_COMPUTED_SIZE);
        digitNet.setMaxWidth(GridPane.USE_PREF_SIZE);
        digitNet.setMaxHeight(GridPane.USE_PREF_SIZE);

        digitPane.add(this.digitNet, 0, 0);
        digitPane.add(this.dividerQuotientDigit, 1,0);

        System.out.println(GridPane.getRowIndex(this.digitNet) + ", " + GridPane.getColumnIndex(this.digitNet));
        System.out.println(GridPane.getRowIndex(this.dividerQuotientDigit) + ", " + GridPane.getColumnIndex(this.dividerQuotientDigit));

        innerVbox.getChildren().add(pane);
        //innerVbox.getChildren().add(new Pane(new Label("&nbsp;")));
        this.digitPane.setGridLinesVisible(true);
        innerVbox.getChildren().add(this.digitPane);
        innerVbox.getChildren().add(pane1);
        this.titledPane.setContent(innerVbox);

    }

    private void sep_cha(ActionEvent actionEvent) {
        int amount;
        try {
            amount = Integer.parseInt(this.sep_field_cha.getText());
            if (amount > Result_List.size() || amount <0) return;
            for (FlowPane pane : Result_List) {
                ((Text) pane.getChildren().get(0)).setText(" ");
            }
            if (amount == 0) return;
            ((Text)Result_List.get(Result_List.size()-amount).getChildren().get(0)).setText(".");
        } catch (NumberFormatException ignored) {

        }
    }

    private void sep_det(ActionEvent actionEvent) {
        try {
            int amount = Integer.parseInt(this.sep_field_det.getText());
            if (amount > detList.size() || amount <0) return;
            for (FlowPane pane : detList) {
                ((Text) pane.getChildren().get(0)).setText(" ");
            }
            if (amount == 0) return;
            ((Text)detList.get(detList.size()-amount).getChildren().get(0)).setText(".");
        } catch (NumberFormatException ignored) {

        }
    }

    private void sep_dem(ActionEvent actionEvent) {
        try {
            int amount = Integer.parseInt(this.sep_field_dem.getText());
            if (amount > demList.size() || amount <0) return;
            for (FlowPane pane : demList) {
                ((Text) pane.getChildren().get(0)).setText(" ");
            }
            if (amount == 0) return;
            ((Text)demList.get(demList.size()-amount).getChildren().get(0)).setText(".");
        } catch (NumberFormatException ignored) {

        }
    }

    private void rem_cha_digit(ActionEvent actionEvent) {
        if(this.Result_List.size() == 1) return;
        FlowPane fp = this.Result_List.get(this.Result_List.size() - 1);
        this.dividerQuotientDigit.getChildren().remove(fp);
        this.Result_List.remove(fp);
    }

    private void add_cha_digit(ActionEvent actionEvent) {
        if(this.Result_List.size() >= 16) return;
        FlowPane fp = createFlow();
        this.dividerQuotientDigit.add(fp, this.Result_List.size() + 1, 2);
        this.Result_List.add(fp);
    }

    private void rem_det_digit(ActionEvent actionEvent) {
        if(this.detList.size() == 1) return;
        FlowPane fp = this.detList.get(this.detList.size() - 1);
        this.dividerQuotientDigit.getChildren().remove(fp);
        this.detList.remove(fp);
    }

    private void add_det_digit(ActionEvent actionEvent) {
        if(this.detList.size() >= 16) return;
        FlowPane fp = createFlow();
        this.dividerQuotientDigit.add(fp, this.detList.size() + 1, 0);
        this.detList.add(fp);

    }

    private void rem_dem_digit(ActionEvent actionEvent) {
        if(this.digit_dem == 1) return;
        if(this.divisionBlocks.size() != 1) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Изменение разрядности");

            alert.setHeaderText("Вы хотите изменить разрядность делимого. Это повлечет удаление вычислений в данном инструменте, сделанных до этого!");
            alert.setContentText("Подтвердите действие");
            Optional<ButtonType> option = alert.showAndWait();


            if (option.orElse(null) == ButtonType.OK) {
                FlowPane fp = this.demList.get(this.demList.size() - 1);
                this.digitNet.getChildren().remove(fp);
                this.digit_dem--;
                demList.remove(fp);
                this.deleteBlocks();
                divisionBlocks.get(0).setMaxDigit(this.digit_dem);
            }
        }
        else {
            FlowPane fp = this.demList.get(this.demList.size() - 1);
            this.digitNet.getChildren().remove(fp);
            this.digit_dem--;
            demList.remove(fp);
            divisionBlocks.get(0).setMaxDigit(this.digit_dem);
        }
    }

    private void add_dem_digit(ActionEvent actionEvent) {
        if(this.digit_dem >= 16) return;
        if(this.divisionBlocks.size() != 1) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Изменение разрядности");

            alert.setHeaderText("Вы хотите изменить разрядность делимого. Это повлечет удаление вычислений в данном инструменте, сделанных до этого!");
            alert.setContentText("Подтвердите действие");
            Optional<ButtonType> option = alert.showAndWait();


            if (option.orElse(null) == ButtonType.OK) {
                FlowPane fp = createFlow();
                this.digitNet.add(fp, this.digit_dem + 3, 1);
                this.digit_dem++;
                this.demList.add(fp);
                this.deleteBlocks();

                divisionBlocks.get(0).setMaxDigit(this.digit_dem);
            }


        }
        else {
            FlowPane fp = createFlow();
            this.digitNet.add(fp, this.digit_dem + 3, 1);
            this.digit_dem++;
            this.demList.add(fp);
            divisionBlocks.get(0).setMaxDigit(this.digit_dem);
        }
    }

    private void deleteBlocks(){
        int s = this.divisionBlocks.size();
        for (int i = 0; i < s; i++) {
            this.deleteBlock(null);
        }
    }

    private void deleteBlock(ActionEvent actionEvent) {
        if(this.divisionBlocks.size() == 1) return;
        DivisionBlock db = this.divisionBlocks.get(this.divisionBlocks.size()-1);
        db.remove();
        this.divisionBlocks.remove(db);
        db = this.divisionBlocks.get(this.divisionBlocks.size()-1);
        db.placeUnderBlock();
        db.unblock();
    }

    public void delete_1(TranslateIntegerPart translateIntegerPart){
        ((VBox)this.vBox.getParent()).getChildren().remove(this.vBox);
        translateIntegerPart.list.remove(this);

        for (Division d: translateIntegerPart.list) {
            d.change_index(this.index);
        }
    }

    @Override
    public String getInform() {
        return null;
    }

    @Override
    public void separate_digits(ActionEvent actionEvent) {

    }

    @Override
    public void checkValue() throws IllegalArgumentException {

    }

    public FlowPane createFlow(){
        FlowPane pane = new FlowPane();

        pane.setMaxWidth(35);
        pane.setPrefWidth(pane.getMaxWidth());

        LimitedTextField textField = new LimitedTextField();
        textField.setMaxLength(1);
        textField.setPrefWidth(30);
        textField.setPrefWidth(textField.getPrefWidth());
        textField.setPrefHeight(20);

        textField.textProperty().addListener((observableValue, s, t1) -> {
            if (!t1.matches(Const.regex_bin)) {
                textField.setText("");
            }
        });

        //textField.setOnKeyReleased(this::behaviour);
        pane.getChildren().addAll(new Text(" "), textField);
        pane.setPrefHeight(textField.getPrefHeight());
        return pane;
    }

    /*private void behaviour(@org.jetbrains.annotations.NotNull KeyEvent keyEvent) {

        FlowPane flowPane = (FlowPane) ((TextField)keyEvent.getSource()).getParent();

        Pattern pattern = Pattern.compile("^[01]$");
        Matcher matcher = pattern.matcher(keyEvent.getText());

        if (matcher.matches()) {
            try {
                ListIterator<FlowPane> iterator = fields.listIterator(fields.indexOf(flowPane) + 1);
                if (iterator.hasNext()) {
                    iterator.next().getChildren().get(1).requestFocus();
                }
            } catch (Exception ignored) {}
        }
        else {
            ((TextField)flowPane.getChildren().get(1)).setText(((TextField)flowPane.getChildren().get(1)).getText());
        }
    }*/
}
