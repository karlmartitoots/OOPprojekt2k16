package userinterface;

import javafx.event.ActionEvent;
import javafx.scene.input.MouseDragEvent;
import javafx.scene.input.MouseEvent;

/**
 * For using the Observer pattern as described by a hometask done previously.
 * Will help to separate out tasks between the nodes on the GUI.
 * http://www.tutorialspoint.com/design_pattern/observer_pattern.htm
 */
public interface Interactable {

    void reactToClick(MouseEvent mouseEvent);

    void reactToMouseDrag(MouseDragEvent mouseDragEvent);

    void reactToButtonPress(ActionEvent actionEvent);
}
