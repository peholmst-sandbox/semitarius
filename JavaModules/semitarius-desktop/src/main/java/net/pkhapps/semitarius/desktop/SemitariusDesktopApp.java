package net.pkhapps.semitarius.desktop;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import net.pkhapps.semitarius.desktop.client.Context;
import net.pkhapps.semitarius.desktop.client.MemberSummaryClient;
import net.pkhapps.semitarius.desktop.client.StatusDescriptorClient;
import net.pkhapps.semitarius.desktop.ui.MemberSummaryController;
import net.pkhapps.semitarius.desktop.ui.MemberSummaryScene;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;

/**
 * TODO Document me
 */
public class SemitariusDesktopApp extends Application {

    private final MemberSummaryClient memberSummaryClient;
    private final StatusDescriptorClient statusDescriptorClient;

    public SemitariusDesktopApp() {
        Client client = ClientBuilder.newClient();
        Context context = Context.getInstance();
        memberSummaryClient = new MemberSummaryClient(client, context);
        statusDescriptorClient = new StatusDescriptorClient(client, context);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("Semitarius Desktop");
        primaryStage.setMaximized(true);

        Scene scene = new MemberSummaryScene(new MemberSummaryController(memberSummaryClient));
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
