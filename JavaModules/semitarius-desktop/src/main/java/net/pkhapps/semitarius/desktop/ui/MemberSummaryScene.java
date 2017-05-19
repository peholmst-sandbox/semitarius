package net.pkhapps.semitarius.desktop.ui;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import net.pkhapps.semitarius.desktop.ui.MemberSummaryController.MemberSummaryModel;
import org.jetbrains.annotations.NotNull;

/**
 * TODO Document me!
 */
public class MemberSummaryScene extends Scene {

    private final TableView<MemberSummaryModel> table = new TableView<>();
    private final Button refreshBtn = new Button("Refresh");// TODO Replace with icon
    private final Label tenantLbl = new Label();
    private final MemberSummaryController controller;

    public MemberSummaryScene(@NotNull MemberSummaryController controller) {
        super(new BorderPane());
        this.controller = controller;
        final BorderPane borderPane = (BorderPane) getRoot();
        getStylesheets().add("semitarius-desktop.css");
        //borderPane.setBorder(Border);

        // Header
        tenantLbl.setText("Pargas FBK"); // TODO Replace with real data
        tenantLbl.getStyleClass().add("header-tenant-name");

        refreshBtn.setOnAction(controller::refresh);

        final HBox header = new HBox();
        header.setPadding(new Insets(10));
        header.getChildren().addAll(tenantLbl, refreshBtn);
        header.getStyleClass().add("header");
        borderPane.setTop(header);

        // Table
        TableColumn<MemberSummaryModel, String> lastName = tableColumn("Last name",
                MemberSummaryModel.PROP_LAST_NAME);
        lastName.setPrefWidth(200);
        TableColumn<MemberSummaryModel, String> firstName =
                tableColumn("First name", MemberSummaryModel.PROP_FIRST_NAME);
        firstName.setPrefWidth(200);
        TableColumn<MemberSummaryModel, String> email = tableColumn("E-mail", MemberSummaryModel.PROP_EMAIL);
        email.setPrefWidth(200);
        TableColumn<MemberSummaryModel, String> phoneNumber =
                tableColumn("Phone number", MemberSummaryModel.PROP_PHONE_NUMBER);
        phoneNumber.setPrefWidth(200);
        table.getColumns().addAll(lastName, firstName, email, phoneNumber);
        table.setItems(controller.getModel());
        table.getStyleClass().add("member-summary-table");
        table.setSelectionModel(null); // No table selection
        borderPane.setCenter(table);
    }

    private <M, V> TableColumn<M, V> tableColumn(String text, String propertyName) {
        TableColumn<M, V> column = new TableColumn<>(text);
        column.setCellValueFactory(new PropertyValueFactory<>(propertyName));
        column.setSortable(false); // No sorting
        return column;
    }
}
