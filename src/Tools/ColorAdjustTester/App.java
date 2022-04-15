/**
 * Copyright
 * https://betacode.net/11121/javafx-effect
 */

package Tools.ColorAdjustTester;

import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class App extends Application {

    private static final int ADJUST_TYPE_HUE = 1;
    private static final int ADJUST_TYPE_CONTRAST = 2;
    private static final int ADJUST_TYPE_SATURATION = 3;
    private static final int ADJUST_TYPE_BRIGHTNESS = 4;

    private ColorAdjust colorAdjust;

    private Label contrastField = new Label();
    private Label hueField = new Label();
    private Label saturationField = new Label();
    private Label brightnessField = new Label();

    private static String imageUrl;

    @Override
    public void start(Stage stage) {

        Label contrastLabel = new Label("Contrast:");
        Label hueLabel = new Label("Hue:");
        Label saturationLabel = new Label("Saturation:");
        Label brightnessLabel = new Label("Brightness:");

        Slider contrastSlider = this.createSlider(ADJUST_TYPE_CONTRAST);
        Slider hueSlider = this.createSlider(ADJUST_TYPE_HUE);
        Slider saturationSlider = this.createSlider(ADJUST_TYPE_SATURATION);
        Slider brightnessSlider = this.createSlider(ADJUST_TYPE_BRIGHTNESS);

        contrastField.setText(Double.toString(contrastSlider.getValue()));
        hueField.setText(Double.toString(hueSlider.getValue()));
        saturationField.setText(Double.toString(saturationSlider.getValue()));
        brightnessField.setText(Double.toString(brightnessSlider.getValue()));

        Image image = new Image(App.imageUrl);
        ImageView imageView = new ImageView(image);

        // Create the ColorAdjust
        colorAdjust = new ColorAdjust();

        // Applying ColorAdjust effect to the ImageView node
        imageView.setEffect(colorAdjust);

        VBox root = new VBox();
        root.setPadding(new Insets(10));

        root.getChildren().addAll(
                hueLabel, hueSlider, hueField,                      //
                saturationLabel, saturationSlider, saturationField, //
                brightnessLabel, brightnessSlider, brightnessField, //
                contrastLabel, contrastSlider, contrastField,       //
                imageView);

        Scene scene = new Scene(root, 1024, 768);
        stage.setTitle("JavaFX ColorAdjust Effect (o7planning.org)");
        stage.setScene(scene);
        stage.show();
    }

    // Create Slider to Adjust Color
    private Slider createSlider(final int adjustType) {
        Slider slider = new Slider();
        slider.setMin(-1);
        slider.setMax(1);
        slider.setBlockIncrement(0.1);
        slider.setValue(0);

        slider.valueProperty().addListener(new ChangeListener<Number>() {

            @Override
            public void changed(ObservableValue<? extends Number> observable, //
                                Number oldValue, Number newValue) {
                switch (adjustType) {
                    case ADJUST_TYPE_HUE -> {
                        hueField.setText(newValue.toString());
                        colorAdjust.setHue(newValue.doubleValue());
                    }
                    case ADJUST_TYPE_CONTRAST -> {
                        contrastField.setText(newValue.toString());
                        colorAdjust.setContrast(newValue.doubleValue());
                    }
                    case ADJUST_TYPE_SATURATION -> {
                        saturationField.setText(newValue.toString());
                        colorAdjust.setSaturation(newValue.doubleValue());
                    }
                    case ADJUST_TYPE_BRIGHTNESS -> {
                        brightnessField.setText(newValue.toString());
                        colorAdjust.setBrightness(newValue.doubleValue());
                    }
                }
            }
        });
        return slider;
    }

    public static void main(String[] args, String imageUrl) {
        App.imageUrl = imageUrl;
        launch(args);
    }

}
