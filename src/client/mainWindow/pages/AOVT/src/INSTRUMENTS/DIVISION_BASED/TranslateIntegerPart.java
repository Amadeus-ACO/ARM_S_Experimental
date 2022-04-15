/*
 * Copyright (c) amadeus-aco.ru
 */

package INSTRUMENTS.DIVISION_BASED;

import INSTRUMENTS.Instrument;
import INSTRUMENTS.SUBCLASS.LimitedTextField;
import INSTRUMENTS.Step;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

import java.util.LinkedList;


public class TranslateIntegerPart extends Instrument {
    /**
     * @param index     - индекс
     * @param step      - шаг
     * @param digit_dem - количество разрядов делимого
     * @param digit_det - количество разрядов делителя
     */
    //TODO настроить делитель (по системе счисления)
    //TODO неизменяемый делитель
    public Button button_plus;
    public TextField dividend_dig; //Разряды делимого
    public TextField divider_dig; // Разряды делителя
    public VBox translate_vBox;
    public FlowPane pane;
    public LinkedList<Division> list = new LinkedList<>();

    public TranslateIntegerPart(int index, Step step/*, int digit_dem, int digit_det*/) {
        super("Перевод целой части числа в другую СС делением", index, step);
        button_plus = new Button("+");
        button_plus.setOnAction(this::add_div);

        dividend_dig = new TextField();
        divider_dig = new TextField();

        pane = new FlowPane();

        this.translate_vBox = new VBox();
        this.titledPane.setContent(translate_vBox);

        pane.getChildren().addAll(new Label("Количество разрядов делимого: "), dividend_dig,
                new Label("Система счисления: "), divider_dig, button_plus);

        this.translate_vBox.getChildren().addAll(pane);

    }

    private void add_div(ActionEvent actionEvent){
        try {
            checkValue();
        } catch (IllegalArgumentException e){
            System.out.println(e.getMessage());
        }
        Division division = new Division(this.list.size()+1, step,  Integer.parseInt(dividend_dig.getText()),
                /*Integer.parseInt(divider_dig.getText())*/divider_dig.getText().length());
        division.deleteButton.setOnAction(actionEvent1 -> division.delete_1(this));
        int i = 0;
        //TODO: убрать ограничение на 1 и 0 в detList (иначе нельзя написать основание системы счисления)
        for (FlowPane pane : division.detList){
            ((LimitedTextField) pane.getChildren().get(1)).setText(Character.toString(divider_dig.getText().charAt(i)));
            i++;
        }
        this.list.add(division);
        this.translate_vBox.getChildren().add(division.vBox);

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

}
