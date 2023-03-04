package safbuilder.ui.components;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;

import java.io.File;

public class DirectoryChooserWidget extends HBox {

	private File file = null;
	private final Button button;
	private final Label label;

	public DirectoryChooserWidget(Stage stage){
		super();
		button = new Button();
		button.setText("Choose");

		label = new Label();
		label.setWrapText(true);
		getChildren().addAll(button, label);

		DirectoryChooser directoryChooser = new DirectoryChooser();
		button.setOnAction(actionEvent -> {
			this.file = directoryChooser.showDialog(stage);
			if(file != null)
				label.setText(file.getPath());
		});
	}

	public void setText(String text){
		button.setText(text);
	}

	public File getFile(){
		return file;
	}

}
