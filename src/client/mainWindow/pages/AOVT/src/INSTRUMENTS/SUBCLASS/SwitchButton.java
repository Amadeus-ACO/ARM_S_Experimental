package INSTRUMENTS.SUBCLASS;

import javafx.event.ActionEvent;


public class SwitchButton extends SwitchCarry{
    private DivisionBlock DIVISIONBLOCK;

    /**
     * @return
     */
    public SwitchButton(DivisionBlock db){
        super();
        this.DIVISIONBLOCK = db;
        this.button.setOnAction(this::switch_type);

    }

    /**
     *
     * @param row
     * @param column
     */
    public void setCoordinate(int row, int column){
        this.row = row;
        this.column = column;
    }

    @Override
    protected void switch_type(ActionEvent actionEvent) {
        DIVISIONBLOCK.createNewBlock(this);
    }
}
