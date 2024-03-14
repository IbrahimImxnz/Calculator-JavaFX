import javafx.application.Application;
import javafx.beans.property.SimpleStringProperty;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class Calculator extends Application {

    private TextField inputField;
    private SimpleStringProperty input = new SimpleStringProperty("");
    private String operation = "";
    private double leftOperand = 0;

    @Override
    public void start(Stage primaryStage) {
        BorderPane root = new BorderPane();
        root.setPadding(new Insets(10));

        inputField = new TextField();
        inputField.setEditable(false);
        inputField.textProperty().bind(input);

        GridPane buttonGrid = new GridPane();
        buttonGrid.setHgap(5);
        buttonGrid.setVgap(5);

        addButton(buttonGrid, "7", 0, 0);
        addButton(buttonGrid, "8", 1, 0);
        addButton(buttonGrid, "9", 2, 0);
        addButton(buttonGrid, "/", 3, 0);

        addButton(buttonGrid, "4", 0, 1);
        addButton(buttonGrid, "5", 1, 1);
        addButton(buttonGrid, "6", 2, 1);
        addButton(buttonGrid, "*", 3, 1);

        addButton(buttonGrid, "1", 0, 2);
        addButton(buttonGrid, "2", 1, 2);
        addButton(buttonGrid, "3", 2, 2);
        addButton(buttonGrid, "-", 3, 2);

        addButton(buttonGrid, "0", 0, 3);
        addButton(buttonGrid, "C", 1, 3);
        addButton(buttonGrid, "CE", 2, 3);
        addButton(buttonGrid, "+", 3, 3);

        addButton(buttonGrid, "=", 0, 4, 4, 1);

        root.setTop(inputField);
        root.setBottom(buttonGrid);

        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Calculator");
        primaryStage.show();
    }

    private void addButton(GridPane grid, String text, int col, int row) {
        addButton(grid, text, col, row, 1, 1);
    }

    private void addButton(GridPane grid, String text, int col, int row, int colspan, int rowspan) {
        Button button = new Button(text);
        button.setOnAction(e -> handleButtonAction(text));
        grid.add(button, col, row, colspan, rowspan);
    }

    private void handleButtonAction(String buttonText) {
        switch (buttonText) {
            case "C":
                if (!input.getValue().isEmpty()) {
                    input.setValue(input.getValue().substring(0, input.getValue().length() - 1));
                }
                break;
            case "CE":
                input.setValue("");
                break;
            case "+":
            case "-":
            case "*":
            case "/":
                leftOperand = Double.parseDouble(input.getValue());
                operation = buttonText;
                input.setValue("");
                break;
            case "=":
                double rightOperand = Double.parseDouble(input.getValue());
                double result = calculateResult(leftOperand, rightOperand, operation);
                input.setValue(String.valueOf(result));
                break;
            default:
                input.setValue(input.getValue() + buttonText);
                break;
        }
    }

    private double calculateResult(double leftOperand, double rightOperand, String operation) {
        switch (operation) {
            case "+":
                return leftOperand + rightOperand;
            case "-":
                return leftOperand - rightOperand;
            case "*":
                return leftOperand * rightOperand;
            case "/":
                if (rightOperand != 0) {
                    return leftOperand / rightOperand;
                } else {
                    return Double.NaN; // Handle division by zero
                }
            default:
                return 0;
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
