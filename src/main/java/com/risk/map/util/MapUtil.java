package com.risk.map.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Optional;

import com.risk.entity.Continent;
import com.risk.entity.Map;
import com.risk.entity.Territory;
import com.risk.exception.InvalidMapException;
import com.risk.validate.MapValidator;

import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Control;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.TitledPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;

/**
 * @author rahul This class is used to handle utilities for maps.
 * @version 1.0.0
 *
 */
public class MapUtil {

	/**
	 *  This method is used to clear off all the field on form elements.
	 * @param fields text fields
	 */
	public static void clearTextField(TextField... fields) {
		for (TextField field : fields) {
			field.clear();
		}
	}

	/**
	 * This method is used to disable the field on form elements.
	 * 
	 * @param controls
	 *            controls
	 */
	public static void disableControl(Control... controls) {
		for (Control control : controls) {
			control.setDisable(true);
		}
	}

	/**
	 * This method is used to enable the field on form elements.
	 * 
	 * @param controls
	 *            controls
	 */
	public static void enableControl(Control... controls) {
		for (Control control : controls) {
			control.setDisable(false);
		}
	}

	/**
	 * This method is used to open up a dialog box to choose a map file.
	 * 
	 * @return file of type object {@link File}
	 */
	public static File showFileChooser() {
		FileChooser fileChooser = new FileChooser();
		File file = null;
		FileChooser.ExtensionFilter extensionFilter = new FileChooser.ExtensionFilter("Map files (*.map)", "*.map");
		fileChooser.getExtensionFilters().add(extensionFilter);
		file = fileChooser.showOpenDialog(null);
		return file;
	}

	/**
	 * This method is used to save map object into a map file.
	 * @param file file object
	 * @param map map object
	 * @throws InvalidMapException invalid map file exception
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

	/**
	 * Info box to display message.
	 * 
	 * @param infoMessage
	 *            infomessage box
	 * @param titleBar
	 *            title bar for message
	 * @param headerMessage
	 *            header message
	 */
	public static void infoBox(String infoMessage, String titleBar, String headerMessage) {
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle(titleBar);
		alert.setHeaderText(headerMessage);
		alert.setContentText(infoMessage);
		alert.showAndWait();
	}

	/**
	 * This method is used to print a message in green or red in textarea.
	 * 
	 * @param textArea
	 *            text area refrence
	 * @param message
	 *            message
	 * @param isSuccess
	 *            is success message
	 */
	public static void outPutMessgae(TextArea textArea, String message, boolean isSuccess) {
		if (isSuccess) {
			textArea.setStyle("-fx-text-fill: green;");
		} else {
			textArea.setStyle("-fx-text-fill: red;");
		}

		textArea.setText(message);
	}

	/**
	 * This method is used to get input in a dialog box.
	 * 
	 * @return String input entered by a user
	 */
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

	/**
	 * This method is used to get input in a dialog box for fortification so that
	 * user can enter a number of armies.
	 * 
	 * @return Integer number of armies entered by a user.
	 */
	public static int inputDialogueBoxForArmiesFortification() {
		TextInputDialog dialog = new TextInputDialog();
		dialog.setTitle("Input a number");
		dialog.setHeaderText("Enter number of armies for fortification (1 less than total number)");
		Optional<String> result = dialog.showAndWait();
		if (result.isPresent())
			return Integer.parseInt(result.get());
		else
			return 0;
	}

	/**
	 * Create new titled pain.
	 * 
	 * @param continent
	 *            continent object
	 * @return TitledPane Titled pane
	 */
	public static TitledPane createNewTitledPane(Continent continent) {
		VBox hbox = new VBox();
		for (Territory territory : continent.getTerritories()) {
			Label label1 = new Label();
			if (territory.getPlayer() != null) {
				label1.setText(
						territory.getName() + ":-" + territory.getArmies() + "-" + territory.getPlayer().getName());
			} else {
				label1.setText(territory.getName() + ":-" + territory.getArmies());
			}
			hbox.getChildren().add(label1);
		}
		TitledPane pane = new TitledPane(continent.getName(), hbox);

		return pane;
	}

	/**
	 * Output to game console screen
	 * 
	 * @param valueOf
	 *            text value
	 * @param gameConsole
	 *            game console object
	 */
	public static void appendTextToGameConsole(String valueOf, TextArea gameConsole) {
		Platform.runLater(() -> gameConsole.appendText(valueOf));
	}

	/**
	 * This method is used to load Image view from a folder.
	 * 
	 * @param imageView
	 *            image to show up.
	 * @param classLoader
	 *            to load up the image.
	 * @return ImageView imageView to .
	 */
	public static ImageView loadImageView(ImageView imageView, ClassLoader classLoader) {
		InputStream inputStream = null;
		try {
			inputStream = new FileInputStream(new File(classLoader.getResource("risk.jpg").getFile()));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Image image = new Image(inputStream);
		imageView.setImage(image);

		return imageView;
	}
}
