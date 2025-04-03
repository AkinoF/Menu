import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
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

        newButton.setOnAction(e -> showPersonDialog(null));

        editButton.setOnAction(e -> {
            Person selectedPerson = personListView.getSelectionModel().getSelectedItem();
            if (selectedPerson != null) {
                showPersonDialog(selectedPerson);
            }
        });

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

    private void showPersonDialog(Person person) {

        // Создаем новое окно
        Stage dialogStage = new Stage();
        dialogStage.initModality(Modality.APPLICATION_MODAL);
        dialogStage.setTitle(person == null ? "Add Person" : "Edit Person");

        // Поля для ввода данных
        TextField firstNameField = new TextField(person != null ? person.getFirstName() : "");
        TextField lastNameField = new TextField(person != null ? person.getLastName() : "");
        TextField streetField = new TextField(person != null ? person.getStreet() : "");
        TextField cityField = new TextField(person != null ? person.getCity() : "");
        TextField postalCodeField = new TextField(person != null ? person.getPostalCode() : "");
        TextField birthdayField = new TextField(person != null ? person.getBirthday() : "");

        VBox dialogPane = new VBox(10);
        dialogPane.getChildren().addAll(
                new Label("First Name:"), firstNameField,
                new Label("Last Name:"), lastNameField,
                new Label("Street:"), streetField,
                new Label("City:"), cityField,
                new Label("Postal Code:"), postalCodeField,
                new Label("Birthday:"), birthdayField
        );

        Button saveButton = new Button("Save");
        saveButton.setOnAction(e -> {
            if (person == null) { // Добавление нового человека
                people.add(new Person(firstNameField.getText(), lastNameField.getText(), streetField.getText(),
                        cityField.getText(), postalCodeField.getText(), birthdayField.getText()));
            } else { // Редактирование существующего человека
                person.firstName = firstNameField.getText();  // Изменение полей напрямую для редактирования
                person.lastName = lastNameField.getText();
                person.street = streetField.getText();
                person.city = cityField.getText();
                person.postalCode = postalCodeField.getText();
                person.birthday = birthdayField.getText();
            }
            updatePersonList();
            dialogStage.close();
        });

        dialogPane.getChildren().add(saveButton);

        Scene dialogScene = new Scene(dialogPane, 300, 400);
        dialogStage.setScene(dialogScene);
        dialogStage.showAndWait(); // Ожидание закрытия окна перед продолжением выполнения кода
    }

    public static void main(String[] args) {
        launch(args);
    }
}