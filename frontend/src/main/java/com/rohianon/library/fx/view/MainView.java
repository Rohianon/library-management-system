package com.rohianon.library.fx.view;

import com.rohianon.library.fx.model.Book;
import com.rohianon.library.fx.service.BookService;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

import java.time.LocalDate;
import java.util.List;

public class MainView extends VBox {

    private final TableView<Book> tableView;
    private final TextField titleField;
    private final TextField authorField;
    private final TextField isbnField;
    private final DatePicker publishedDatePicker;
    private final Button addButton;
    private final Button updateButton;
    private final Button deleteButton;
    private final Button refreshButton;
    private final Button prevPageButton;
    private final Button nextPageButton;
    private final Label pageInfoLabel;
    private final ComboBox<Integer> pageSizeBox;

    public MainView() {
        this.bookService = new BookService();

        setSpacing(10);
        setPadding(new Insets(10));

        // Initialize form fields
        titleField = new TextField();
        titleField.setPromptText("Title");

        authorField = new TextField();
        authorField.setPromptText("Author");

        isbnField = new TextField();
        isbnField.setPromptText("ISBN");

        publishedDatePicker = new DatePicker();
        publishedDatePicker.setPromptText("Published Date");

        HBox formBox = new HBox(10,
                new Label("Title:"), titleField,
                new Label("Author:"), authorField,
                new Label("ISBN:"), isbnField,
                new Label("Published:"), publishedDatePicker
        );
        formBox.setPadding(new Insets(5));

        // Initialize buttons
        addButton = new Button("Add");
        updateButton = new Button("Update");
        deleteButton = new Button("Delete");
        refreshButton = new Button("Refresh");
        prevPageButton = new Button("Prev");
        nextPageButton = new Button("Next");
        pageInfoLabel = new Label("Page 1 of 1");
        pageSizeBox = new ComboBox<>();
        pageSizeBox.getItems().addAll(5, 10, 20, 50);
        pageSizeBox.setValue(10);

        addButton.setOnAction(e -> handleAdd());

        HBox buttonBox = new HBox(10, addButton, updateButton, deleteButton, refreshButton);
        buttonBox.setPadding(new Insets(5));

        HBox paginationBox = new HBox(10,
                prevPageButton,
                nextPageButton,
                new Label("Page size:"),
                pageSizeBox,
                pageInfoLabel
        );
        paginationBox.setPadding(new Insets(5));

        // Initialize table
        tableView = createTableView();
        VBox.setVgrow(tableView, Priority.ALWAYS);

        getChildren().addAll(formBox, buttonBox, tableView, paginationBox);
    }

    private void handleAdd() {
        String title = titleField.getText().trim();
        String author = authorField.getText().trim();
        String isbn = isbnField.getText().trim();
        LocalDate publishedDate = publishedDatePicker.getValue();

        if (title.isEmpty() || author.isEmpty()) {
            showError("Validation Error", "Title and Author are required.");
            return;
        }

        Book book = new Book(null, title, author, isbn, publishedDate);

        try {
            bookService.createBook(book);
            refreshTable();
            clearForm();
        } catch (Exception e) {
            showError("Error", "Failed to add book: " + e.getMessage());
        }
    }

    private void refreshTable() {
        try {
            List<Book> books = bookService.getAllBooks();
            tableView.setItems(FXCollections.observableArrayList(books));
        } catch (Exception e) {
            showError("Error", "Failed to load books: " + e.getMessage());
        }
    }

    private void clearForm() {
        titleField.clear();
        authorField.clear();
        isbnField.clear();
        publishedDatePicker.setValue(null);
    }

    private void showError(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private TableView<Book> createTableView() {
        TableView<Book> table = new TableView<>();

        TableColumn<Book, java.util.UUID> idColumn = new TableColumn<>("ID");
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        idColumn.setPrefWidth(200);

        TableColumn<Book, String> titleColumn = new TableColumn<>("Title");
        titleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));
        titleColumn.setPrefWidth(200);

        TableColumn<Book, String> authorColumn = new TableColumn<>("Author");
        authorColumn.setCellValueFactory(new PropertyValueFactory<>("author"));
        authorColumn.setPrefWidth(150);

        TableColumn<Book, String> isbnColumn = new TableColumn<>("ISBN");
        isbnColumn.setCellValueFactory(new PropertyValueFactory<>("isbn"));
        isbnColumn.setPrefWidth(150);

        TableColumn<Book, LocalDate> dateColumn = new TableColumn<>("Published Date");
        dateColumn.setCellValueFactory(new PropertyValueFactory<>("publishedDate"));
        dateColumn.setPrefWidth(120);

        table.getColumns().addAll(idColumn, titleColumn, authorColumn, isbnColumn, dateColumn);
        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY_FLEX_LAST_COLUMN);

        return table;
    }

    public TableView<Book> getTableView() {
        return tableView;
    }

    public TextField getTitleField() {
        return titleField;
    }

    public TextField getAuthorField() {
        return authorField;
    }

    public TextField getIsbnField() {
        return isbnField;
    }

    public DatePicker getPublishedDatePicker() {
        return publishedDatePicker;
    }

    public Button getAddButton() {
        return addButton;
    }

    public Button getUpdateButton() {
        return updateButton;
    }

    public Button getDeleteButton() {
        return deleteButton;
    }

    public Button getRefreshButton() {
        return refreshButton;
    }

    public Button getPrevPageButton() {
        return prevPageButton;
    }

    public Button getNextPageButton() {
        return nextPageButton;
    }

    public Label getPageInfoLabel() {
        return pageInfoLabel;
    }

    public ComboBox<Integer> getPageSizeBox() {
        return pageSizeBox;
    }
}
