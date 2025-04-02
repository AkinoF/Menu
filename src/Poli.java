import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class Poli extends Application {

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Poli");

        // Создаем меню
        MenuBar menuBar = new MenuBar();

        // Создаем меню "Файл"
        Menu fileMenu = new Menu("Файл");
        MenuItem saveItem = new MenuItem("Сохранение");
        MenuItem loadItem = new MenuItem("Загрузка");
        fileMenu.getItems().addAll(saveItem, loadItem);

        // Создаем меню "Данные"
        Menu dataMenu = new Menu("Данные");
        MenuItem subgroupItem = new MenuItem("Подгруппа");
        MenuItem listItem = new MenuItem("Список");
        MenuItem absencesItem = new MenuItem("Пропуски");
        dataMenu.getItems().addAll(subgroupItem, listItem, absencesItem);

        // Создаем меню "Редактирование"
        Menu editMenu = new Menu("Редактирование");
        MenuItem editItem = new MenuItem("Редактирование");
        MenuItem viewItem = new MenuItem("Просмотр");
        MenuItem clearItem = new MenuItem("Очистка");
        editMenu.getItems().addAll(editItem, viewItem, clearItem);

        // Создаем пункт меню "Выход"
        Menu exitMenu = new Menu("Выход");
        MenuItem exitItem = new MenuItem("Выход"); // Создаем элемент меню "Выход"

        // Добавляем обработчик события для выхода
        exitItem.setOnAction(e -> {
            System.exit(0); // Завершение программы
        });

        exitMenu.getItems().add(exitItem); // Добавляем элемент в меню

        // Добавляем все меню в меню-бар
        menuBar.getMenus().addAll(fileMenu, dataMenu, editMenu, exitMenu);

        // Создаем основной макет
        BorderPane root = new BorderPane();
        root.setTop(menuBar);

        // Создаем сцену и устанавливаем ее на сцену
        Scene scene = new Scene(root, 800, 600);

        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}