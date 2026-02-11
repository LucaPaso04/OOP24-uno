package uno.view.impl;

import uno.view.api.GameFrame;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import java.awt.Dimension;
import java.awt.Container;

/**
 * Concrete implementation of the main application window using Java Swing.
 */
public final class GameFrameImpl extends JFrame implements GameFrame {

    private static final long serialVersionUID = 1L;

    private static final Dimension MIN_SIZE = new Dimension(1200, 800);

    /**
     * Constructs the main window with standard settings.
     *
     * @param title The title to be displayed in the window's title bar.
     */
    public GameFrameImpl(final String title) {
        super(title);

        setDefaultCloseOperation(EXIT_ON_CLOSE);

        setMinimumSize(MIN_SIZE);

        setLocationRelativeTo(null);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void showScene(final Container scene) {
        setContentPane(scene);

        SwingUtilities.invokeLater(() -> {
            validate();
            repaint();
        });
    }
}
