package uno.view.components.api;

/**
 * Interface representing a UI component used to select a CardColor.
 */
@FunctionalInterface
public interface ColorChooserPanel {

    /**
     * Closes the chooser component after a selection has been made 
     * or the operation is canceled.
     */
    void closeChooser();
}
