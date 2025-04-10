import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;

import java.io.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
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
        // Добавьте больше тестовых данных с разными датами рождения

        // Создание элементов интерфейса
        personListView = new ListView<>();
        personDetailsArea = new TextArea();
        personDetailsArea.setEditable(false);

        // Заполнение списка людей
        updatePersonList();

        // Обработчик выбора человека из списка
        personListView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                showPersonDetails(newValue);
            }
        });

        // Создание кнопок
        Button newButton = new Button("New");
        Button deleteButton = new Button("Delete");
        Button editButton = new Button("Edit");

        newButton.setOnAction(e -> showPersonDialog(null));

        deleteButton.setOnAction(e -> {
            Person selectedPerson = personListView.getSelectionModel().getSelectedItem();
            if (selectedPerson != null) {
                people.remove(selectedPerson);
                updatePersonList();
                personDetailsArea.clear();
            }
        });

        editButton.setOnAction(e -> {
            Person selectedPerson = personListView.getSelectionModel().getSelectedItem();
            if (selectedPerson != null) {
                showPersonDialog(selectedPerson);
            }
        });

        // Создание меню
        MenuBar menuBar = new MenuBar();

        Menu fileMenu = new Menu("File");

        MenuItem saveMenuItem = new MenuItem("Save");
        saveMenuItem.setOnAction(e -> savePeopleToFile());

        MenuItem loadMenuItem = new MenuItem("Load");
        loadMenuItem.setOnAction(e -> loadPeopleFromFile());

        fileMenu.getItems().addAll(saveMenuItem, loadMenuItem);

        Menu helpMenu = new Menu("Help");

        MenuItem helpMenuItem = new MenuItem("Help");
        helpMenuItem.setOnAction(e -> showHelpDialog());

        helpMenu.getItems().add(helpMenuItem);

        // Добавляем кнопку Statistics в основное меню
        Menu statisticsMenu = new Menu("Statistics");

        MenuItem statisticsMenuItem = new MenuItem("Show Birthdays This Month");

        statisticsMenuItem.setOnAction(e -> showBirthdayStatistics());

        statisticsMenu.getItems().add(statisticsMenuItem);

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

    private void showBirthdayStatistics() {
        int[] birthdayCounts = new int[31]; // Массив для хранения количества дней рождения по дням месяца

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");

        for (Person person : people) {
            LocalDate birthdayDate = LocalDate.parse(person.getBirthday(), formatter);
            if (birthdayDate.getMonthValue() == LocalDate.now().getMonthValue()) {
                birthdayCounts[birthdayDate.getDayOfMonth() - 1]++;
            }
        }
        
    }

    private void showHelpDialog() {
        Stage helpStage = new Stage();
        helpStage.initModality(Modality.APPLICATION_MODAL);
        helpStage.setTitle("Help");

        TextArea helpTextArea = new TextArea();
        helpTextArea.setEditable(false);
        helpTextArea.setText(
                "Добро пожаловать приложение по слежке людей для FBI.\n\n" +
                        "- Используя кнопку 'New' даёт вам добавить нового человека в базу данных.\n" +
                        "- Выбирая человека из списка и нажимая кнопку 'Edit' даёт изменить их информацию.\n" +
                        "- Используйте кнопку 'Delete' чтобы удалить информацию о человеке из базы данных.\n" +
                        "- Save и Load функции которые находяться в кнопке File. Даёт вам сохранить ваши изменения и загрузить их."
        );

        VBox dialogPane = new VBox(10);
        dialogPane.getChildren().addAll(helpTextArea);

        Scene dialogScene = new Scene(dialogPane, 400, 300);
        helpStage.setScene(dialogScene);
        helpStage.showAndWait();
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

        Stage dialogStage = new Stage();
        dialogStage.initModality(Modality.APPLICATION_MODAL);
        dialogStage.setTitle(person == null ? "Add Person" : "Edit Person");

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
            if (person == null) {
                people.add(new Person(firstNameField.getText(), lastNameField.getText(), streetField.getText(),
                        cityField.getText(), postalCodeField.getText(), birthdayField.getText()));
            } else {
                person.firstName = firstNameField.getText();
                person.lastName = lastNameField.getText();
                person.street = streetField.getText();
                person.city = cityField.getText();
                person.postalCode = postalCodeField.getText();
                person.birthday= birthdayField .getText();
            }
            updatePersonList();
            dialogStage.close();
        });

        dialogPane.getChildren().add(saveButton);

        Scene dialogScene= 	new Scene(dialogPane ,300 ,400 );
        dialogStage .setScene(dialogScene );
        dialogStage .showAndWait();
    }

    private void savePeopleToFile() {
        try (ObjectOutputStream oos=	new ObjectOutputStream(new FileOutputStream ("people.dat"))) {
            oos.writeObject(people );
            System.out.println ("Data saved successfully.");
        } catch (IOException e) {
            e.printStackTrace ();
            showAlert ("Error saving data: "+ e .getMessage());
        }
    }

    private void loadPeopleFromFile () {
        try (ObjectInputStream ois=	new ObjectInputStream(new FileInputStream ("people.dat"))) {
            people.clear ();
            people.addAll((ArrayList<Person>) ois.readObject());
            updatePersonList ();
            System.out.println ("Data loaded successfully.");
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace ();
            showAlert ("Error loading data: "+ e .getMessage());
        }
    }

    private void showAlert(String message) {
        Alert alert=	new Alert(Alert.AlertType.ERROR );
        alert .setTitle ("Error ");
        alert .setHeaderText(null );
        alert .setContentText(message );
        alert .showAndWait ();
    }

    public static void main(String[] args) {
        launch(args );
    }
}