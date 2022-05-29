package INSTRUMENTS.TABLE;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.scene.control.CheckBox;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Random;

public class Controller {
    @Setter
    Model model;
    @Setter
    View view;
    void change(ActionEvent actionEvent) {
        this.view.btn_approve.setVisible(true);

        for (CheckBox c:this.view.getBoxes()) {
            c.setVisible(true);
        }
    }

    void changeColor(ActionEvent actionEvent) {
        Random rand = new Random();
        float r = rand.nextFloat();
        float g = rand.nextFloat();
        float b = rand.nextFloat();

        for (int i = 0; i < this.view.getBoxes().size(); i++) {
            if (this.view.getBoxes().get(i).isSelected()) {
                try{
                    Paint s = this.view.net.get(i).get(0).getBackground().getFills().get(0).getFill();
                    for (ArrayList<StackPane> a :this.view.net) {
                        if (a.get(0).getBackground() != null){
                            if (a.get(0).getBackground().getFills().get(0).getFill().equals(s)){
                                for (StackPane pane:a) {
                                    pane.setBackground(null);
                                }
                            }
                        }
                    }
                } catch (Exception ignored){}

                Color color = Color.color(r, g, b);
                this.model.rows.get(i).color = color;

                this.view.net.get(i).forEach(e -> e.setBackground(new Background(new BackgroundFill(
                        color, CornerRadii.EMPTY, Insets.EMPTY
                ))));
            }
        }

        this.view.btn_approve.setVisible(false);

        for (CheckBox c:this.view.getBoxes()) {
            c.setVisible(false);
            c.setSelected(false);
        }

    }

    public void changeModel(String text, int index, int numberRow) {
        if (index < this.model.getCountParams()) {
            this.model.rows.get(numberRow).args[index] = Integer.parseInt(text);
        }
        else this.model.rows.get(numberRow).result = Integer.parseInt(text);

        method();
    }

    void method() {
        ObjectMapper mapper = new ObjectMapper();
        try {
            String json = mapper.writeValueAsString(this.model);
            System.out.println(json);
        }
        catch (JsonProcessingException e){
            e.printStackTrace();
        }

    }
}

