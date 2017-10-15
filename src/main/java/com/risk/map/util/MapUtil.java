package com.risk.map.util;

import java.io.File;
import java.util.Optional;

import com.risk.entity.Map;
import com.risk.exception.InvalidMapException;
import com.risk.validate.MapValidator;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.stage.FileChooser;

/**
 * @author rahul
 *
 */
public class MapUtil {

	/**
	 * @param field
	 */
	public static void clearTextField(TextField... fields) {
		for (TextField field : fields) {
			field.clear();
		}
	}

	/**
	 * @return
	 */
	public static File showFileChooser() {
		FileChooser fileChooser = new FileChooser();

		FileChooser.ExtensionFilter extensionFilter = new FileChooser.ExtensionFilter("Map files (*.map)", "*.map");
		fileChooser.getExtensionFilters().add(extensionFilter);
		File file = fileChooser.showOpenDialog(null);

		return file;
	}

	/**
	 * @param file
	 * @param map
	 */
	public static void saveMap(File file, Map map) throws InvalidMapException {
		MapValidator.validateMap(map);
		MapFileWriter fileWriter = new MapFileWriter();

		if (file == null) {
			FileChooser fileChooser = new FileChooser();

			// Set extension filter
			FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("Map files (*.map)", "*.map");
			fileChooser.getExtensionFilters().add(extFilter);

			// Show save file dialog
			file = fileChooser.showSaveDialog(null);
		}

		fileWriter.writeMapToFile(map, file);
	}

	public static void infoBox(String infoMessage, String titleBar, String headerMessage) {
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle(titleBar);
		alert.setHeaderText(headerMessage);
		alert.setContentText(infoMessage);
		alert.showAndWait();
	}

	/**
	 * @param textArea
	 * @param message
	 * @param isSuccess
	 */
	public static void outPutMessgae(TextArea textArea, String message, boolean isSuccess) {
		if (isSuccess) {
			textArea.setStyle("-fx-text-fill: green;");
		} else {
			textArea.setStyle("-fx-text-fill: red;");
		}

		textArea.setText(message);
	}

	public static String inputDailougeBox() {
		String input = "0";
		TextInputDialog dialog = new TextInputDialog();
		dialog.setTitle("Input");
		dialog.setHeaderText("Enter number of armies to be placed.");
		Optional<String> result = dialog.showAndWait();
		if (result.isPresent()) {
			input = result.get();
		}
		return input;
	}
}
