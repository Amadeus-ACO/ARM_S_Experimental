package INSTRUMENTS.TABLE;


import INSTRUMENTS.SUBCLASS.LimitedTextField;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.ListIterator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class View {
    private final Model model;
    @Setter
    private Controller controller;
    @Setter
    @Getter
    private ArrayList<CheckBox> boxes = new ArrayList<>();
    @Setter
    @Getter
    public GridPane gridPane;
    @Setter
    @Getter
    VBox TruthTableBox;
    @Setter
    @Getter
    private LinkedList<StackPane> fields = new LinkedList<>();
    Button btn_approve = new Button("Подтвердить");

    ArrayList<ArrayList<StackPane>> net = new ArrayList<>();



    public View(Model model, Controller controller){
        this.model = model;
        this.controller = controller;

        gridPane = new GridPane();
        gridPane.setAlignment(Pos.CENTER);
        for (int i = 0; i < model.getCountParams(); i++) {
            Text t = new Text("a" + i);
            VBox vBox1 = new VBox(t);
            vBox1.setAlignment(Pos.CENTER);
            gridPane.add(vBox1, i+1, 0);
        }
        VBox vBox1 = new VBox(new Text("F"));
        vBox1.setAlignment(Pos.CENTER);
        gridPane.add(vBox1, model.getCountParams()+1, 0);

        for (int j = 1; j < Math.pow(2, model.getCountParams()) + 1; j++) {
            ArrayList<StackPane> line = new ArrayList<>();
            for (int i = 1; i < model.getCountParams() + 2; i++) {
                StackPane stackPane  = new StackPane();
                fields.add(stackPane);
                LimitedTextField textField = new LimitedTextField();
                textField.setMaxLength(1);
                textField.setPrefWidth(30);
                textField.setPrefWidth(textField.getPrefWidth());
                textField.textProperty().addListener((observableValue, s, t1) -> {
                    if (!t1.matches("^[01]$")) {
                        textField.setText("");
                    }
                });
                textField.setOnKeyReleased(this::behaviour);
                stackPane.getChildren().add(textField);
                textField.setPrefWidth(10);

                stackPane.setPrefWidth(40);
                stackPane.setPrefHeight(40);
                line.add(stackPane);

                StackPane.setMargin(textField, new Insets(10,1,10,1));

                gridPane.add(stackPane, i, j);
            }
            this.net.add(line);
        }

        for (int i = 0; i < Math.pow(2, model.getCountParams()); i++) {
            CheckBox checkBox = new CheckBox();
            gridPane.add(checkBox,0,i+1);
            checkBox.setVisible(false);
            boxes.add(checkBox);
        }


        Button btn = new Button("Пометить цветом");
        btn.setOnAction(this.controller::change);
        gridPane.add(btn,model.getCountParams() + 2, 0 );

        gridPane.add(btn_approve,model.getCountParams() + 3,0);
        btn_approve.setVisible(false);
        btn_approve.setOnAction(this.controller::changeColor);

        ScrollPane scrollPane = new ScrollPane(gridPane);
        TruthTableBox = new VBox(scrollPane);

        scrollPane.setMinHeight(gridPane.getHeight());
        //scrollPane.setPrefHeight(gridPane.getHeight());
        TruthTableBox.setMinHeight(scrollPane.getHeight());

        gridPane.setAlignment(Pos.CENTER);
        TruthTableBox.setAlignment(Pos.CENTER);



    }



    private void behaviour(KeyEvent keyEvent) {

        StackPane stackPane = (StackPane) ((TextField)keyEvent.getSource()).getParent();

        Pattern pattern = Pattern.compile("^[01]$");
        Matcher matcher = pattern.matcher(keyEvent.getText());

        if (matcher.matches()) {
            try {
                ListIterator<StackPane> iterator = fields.listIterator(fields.indexOf(stackPane) + 1);
                if (iterator.hasNext()) {
                    this.controller.changeModel(
                            keyEvent.getText(),
                            fields.indexOf(stackPane)
                                    %
                                    (this.model.getCountParams()+1),
                            (int)(fields.indexOf(stackPane)/(this.model.getCountParams()+1))

                    );
                    iterator.next().getChildren().get(0).requestFocus();
                }
            } catch (Exception ignored) {
                ignored.printStackTrace();
            }
        }
        else {
            ((TextField)stackPane.getChildren().get(0)).setText(((TextField)stackPane.getChildren().get(0)).getText());
        }
    }
}