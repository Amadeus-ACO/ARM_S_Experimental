/*
 * Copyright (c) amadeus-aco.ru
 */

package INSTRUMENTS.ADDITION_SUBSTRACTION;

import INSTRUMENTS.SUBCLASS.SwitchCarry;
import INSTRUMENTS.Step;
import com.google.gson.JsonObject;
import javafx.geometry.HPos;
import javafx.scene.layout.GridPane;


public class AdditionInstrument extends ADD_SUB_INSTRUMENT {

    public SwitchCarry result_carry = new SwitchCarry();

    public AdditionInstrument(int index, Step step, int digit) {
        super("Сложение в столбик", index, step, digit);

        signLabel.setText("+");

        this.result_carry.row = 4;
        this.result_carry.column = 0;
        this.result_carry.isOverFlowRes = true;
        this.digitNet.add(this.result_carry.get(),0,4);
        GridPane.setHalignment(this.result_carry.get(),HPos.RIGHT);
    }

    @Override
    public void removeDigit(){
        SwitchCarry s = this.carryList.get(0);

        this.digitNet.getChildren().remove(s.get());
        this.carryList.remove(s);

        for (int i = 0; i < this.digit-2; i++) {
            s = this.carryList.get(i);
            this.digitNet.getChildren().remove(s.get());
            this.digitNet.add(s.get(), s.column-1, s.row);
            s.column--;
        }
        super.removeDigit();

    }

    @Override
    public void checkValue() throws IllegalArgumentException {

    }

    public JsonObject getJson(){
        this.result_carry.row = 4;
        this.result_carry.column = 0;
        this.result_carry.isOverFlowRes = true;
        JsonObject addition = new JsonObject();
        addition.addProperty("type", label_text.getText());
        addition.addProperty("index", index);
        addition.addProperty("digit", digit);
        addition.addProperty("result_carry_row", result_carry.row);
        addition.addProperty("result_carry_column", result_carry.column);
        addition.addProperty("result_carry_isOverFlowRes", result_carry.isOverFlowRes);


        for(int i = 0; i < this.digitNet.getRowCount(); i++){
            StringBuilder sb = new StringBuilder();
            for (int j = 0; j < this.digitNet.getColumnCount(); j++){
                //sb.append(digitNet.getChil);
            }
            addition.addProperty("digitnet_row_"+i, sb.toString());
        }

        return new JsonObject();
    }

}