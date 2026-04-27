package pocketbattles.ui;

import javax.imageio.ImageIO;
import javax.swing.JPanel;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

public class BattleBackgroundPanel extends JPanel {
    private static final String BACKGROUND_PATH = "/assets/background/pixelBackground.jpg";

    private final BufferedImage backgroundImage;

    public BattleBackgroundPanel() {
        backgroundImage = loadBackgroundImage();
        setOpaque(false);
    }

    @Override
    protected void paintComponent(Graphics graphics) {
        super.paintComponent(graphics);
        Graphics2D brush = (Graphics2D) graphics.create();
        brush.setRenderingHint(
                RenderingHints.KEY_INTERPOLATION,
                RenderingHints.VALUE_INTERPOLATION_NEAREST_NEIGHBOR
        );

        if (backgroundImage == null) {
            drawFallbackBackground(brush);
        } else {
            drawImageToFillPanel(brush);
        }

        brush.dispose();
    }

    private BufferedImage loadBackgroundImage() {
        try (InputStream inputStream = getClass().getResourceAsStream(BACKGROUND_PATH)) {
            if (inputStream == null) {
                return null;
            }
            return ImageIO.read(inputStream);
        } catch (IOException exception) {
            return null;
        }
    }

    private void drawImageToFillPanel(Graphics2D brush) {
        Image scaledImage = backgroundImage.getScaledInstance(
                getWidth(),
                getHeight(),
                Image.SCALE_REPLICATE
        );
        brush.drawImage(scaledImage, 0, 0, this);
    }

    private void drawFallbackBackground(Graphics2D brush) {
        brush.setColor(new Color(170, 220, 255));
        brush.fillRect(0, 0, getWidth(), getHeight());

        brush.setColor(new Color(90, 170, 70));
        brush.fillRect(0, getHeight() / 2, getWidth(), getHeight() / 2);
    }
}