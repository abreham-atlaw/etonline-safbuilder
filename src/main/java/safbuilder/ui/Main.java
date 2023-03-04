package safbuilder.ui;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import safbuilder.ui.components.DirectoryChooserWidget;


public class Main extends Application {

	private final ViewModel viewModel = new ViewModel();

	@Override
	public void start(Stage primaryStage) {

		Label titleLabel = new Label();
		titleLabel.setText("ET-Online\nSAF Builder");
		titleLabel.setId("title");

		VBox formContainer = new VBox();
		formContainer.setId("form-wrapper");

		VBox csvPathFieldSet = new VBox();

		Label csvPathLabel = new Label();
		csvPathLabel.setText("Directory:");

		DirectoryChooserWidget fileChooser = new DirectoryChooserWidget(primaryStage);
		fileChooser.setId("file-chooser");

		csvPathFieldSet.getChildren().addAll(csvPathLabel, fileChooser);

		VBox subjectFieldSet = new VBox();

		Label subjectLabel = new Label("Subject");
		TextField subjectInput = new TextField();

		subjectFieldSet.getChildren().addAll(
				subjectLabel,
				subjectInput
		);

		ProgressIndicator progressIndicator = new ProgressIndicator();
		progressIndicator.setVisible(false);

		Button generateButton = new Button();
		generateButton.setText("Generate");
		generateButton.setId("submit-button");



		formContainer.getChildren().addAll(
				csvPathFieldSet,
				subjectFieldSet,
				generateButton
		);

		Label noteLabel = new Label();
		noteLabel.setText("Note: Make sure the csv file is located in the same folder as the PDFs.");
		noteLabel.setWrapText(true);


		VBox wrapper = new VBox();
		wrapper.setId("wrapper");
		wrapper.getChildren().addAll(
				titleLabel,
				formContainer,
				noteLabel
		);

		StackPane root = new StackPane();
		root.setId("root");
		root.getChildren().addAll(
				wrapper,
				progressIndicator
		);

		Scene scene = new Scene(root, 600, 800);
		scene.getStylesheets().add("styles/main_style.css");

		ProgressState.StateListener listener = status -> {
			switch (status){

				case None:
					progressIndicator.setVisible(false);
					wrapper.setVisible(true);
					break;

				case DONE:
					progressIndicator.setProgress(1.0);
					progressIndicator.setVisible(false);
					wrapper.setVisible(true);
					Platform.runLater(() -> {
						Alert successAlert = new Alert(Alert.AlertType.INFORMATION, String.format("Zip Successfully Generated at %s.", fileChooser.getFile().getParent()), ButtonType.OK);
						successAlert.show();
					});
					break;

				case FAILED:
					progressIndicator.setVisible(false);
					wrapper.setVisible(true);
					Platform.runLater(() -> {
						Alert failureAlert = new Alert(Alert.AlertType.ERROR, "Unexpected Error has occur. Check your Inputs.", ButtonType.OK);
						failureAlert.show();
					});
					break;

				case INITIATING:
					Platform.runLater(() -> {
						progressIndicator.setVisible(true);
						progressIndicator.setProgress(0.2);
					});
					wrapper.setVisible(false);
					break;

				case EXTRACTING:
					Platform.runLater(() -> {
						progressIndicator.setProgress(0.5);
					});
					break;

				case GENERATING:
					Platform.runLater(() -> {
						progressIndicator.setProgress(0.8);
					});
					break;
			}
		};
		generateButton.setOnMouseClicked(mouseEvent -> {

			ProgressState state = new ProgressState();
			state.setListener(listener);
			viewModel.generate(fileChooser.getFile(), subjectInput.getText(), state);

		});


		primaryStage.setTitle("ET-Online SAFBuilder");
		primaryStage.setScene(scene);
		primaryStage.show();
	}

	public static void main(String[] args) {
		launch(args);
	}

}
