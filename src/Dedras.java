import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.ArrayList;

public class Dedras extends Application {

    private ListView<Person> personListView;
    private TextArea personDetailsArea;

    private ArrayList<Person> people;

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("FBI");

        // Инициализация данных
        people = new ArrayList<>();
        people.add(new Person("John", "Doe", "123 Main St", "Springfield", "12345", "01/01/1990"));
        people.add(new Person("Jane", "Smith", "456 Elm St", "Springfield", "12345", "02/02/1992"));

        // Создание элементов интерфейса
        personListView = new ListView<>();
        personDetailsArea = new TextArea();
        personDetailsArea.setEditable(false);

        // Заполнение списка людей
        personListView.getItems().addAll(people);

        // Обработчик выбора человека из списка
        personListView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                showPersonDetails(newValue);
            }
        });

        // Создание кнопок
        Button newButton = new Button("New");
        Button editButton = new Button("Edit");
        Button deleteButton = new Button("Delete");

        deleteButton.setOnAction(e -> {
            Person selectedPerson = personListView.getSelectionModel().getSelectedItem();
            if (selectedPerson != null) {
                people.remove(selectedPerson);
                updatePersonList();
                personDetailsArea.clear();
            }
        });

        // Создание меню
        MenuBar menuBar = new MenuBar();

        Menu fileMenu = new Menu("File");
        Menu statisticsMenu = new Menu("Statistics");
        Menu helpMenu = new Menu("Help");

        menuBar.getMenus().addAll(fileMenu, statisticsMenu, helpMenu);

        // Размещение элементов в панели
        VBox leftPanel = new VBox(personListView, newButton, editButton, deleteButton);

        BorderPane root = new BorderPane();
        root.setTop(menuBar);
        root.setLeft(leftPanel);
        root.setCenter(personDetailsArea);

        Scene scene = new Scene(root, 600, 400);

        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void showPersonDetails(Person person) {
        personDetailsArea.setText(
                "First Name: " + person.getFirstName() + "\n" +
                        "Last Name: " + person.getLastName() + "\n" +
                        "Street: " + person.getStreet() + "\n" +
                        "City: " + person.getCity() + "\n" +
                        "Postal Code: " + person.getPostalCode() + "\n" +
                        "Birthday: " + person.getBirthday()
        );
    }

    private void updatePersonList() {
        personListView.getItems().clear();
        personListView.getItems().addAll(people);
    }

    public static void main(String[] args) {
        launch(args);
    }
}