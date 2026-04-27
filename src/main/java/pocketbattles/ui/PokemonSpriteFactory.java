package pocketbattles.ui;

import pocketbattles.model.Pokemon;
import pocketbattles.model.Type;

import javax.imageio.ImageIO;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

public class PokemonSpriteFactory {
    private static final String FRONT_PATH = "/assets/sprites/front/";
    private static final String BACK_PATH = "/assets/sprites/back/";

    public Icon createFrontSprite(Pokemon pokemon, int size) {
        return createSprite(pokemon, size, false);
    }

    public Icon createBackSprite(Pokemon pokemon, int size) {
        return createSprite(pokemon, size, true);
    }

    private Icon createSprite(Pokemon pokemon, int size, boolean backSprite) {
        BufferedImage image = loadSprite(pokemon, backSprite);
        if (image == null) {
            image = createPlaceholderSprite(pokemon, size, backSprite);
        }
        return new ImageIcon(scale(image, size));
    }

    private BufferedImage loadSprite(Pokemon pokemon, boolean backSprite) {
        String folder = backSprite ? BACK_PATH : FRONT_PATH;
        String resourcePath = folder + pokemon.getSpriteKey() + ".png";

        try (InputStream inputStream = getClass().getResourceAsStream(resourcePath)) {
            if (inputStream == null) {
                return null;
            }
            return ImageIO.read(inputStream);
        } catch (IOException exception) {
            return null;
        }
    }

    private BufferedImage createPlaceholderSprite(Pokemon pokemon, int size, boolean backSprite) {
        BufferedImage image = new BufferedImage(size, size, BufferedImage.TYPE_INT_ARGB);
        Graphics2D graphics = image.createGraphics();
        graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        Color primary = typeColor(pokemon.getPrimaryType());
        Color secondary = primary.darker();
        if (backSprite) {
            Color temp = primary;
            primary = secondary;
            secondary = temp;
        }

        graphics.setColor(new Color(0, 0, 0, 60));
        graphics.fillOval(size / 6, size - size / 4, size * 2 / 3, size / 8);

        graphics.setColor(primary);
        graphics.fillRoundRect(size / 4, size / 4, size / 2, size / 2, 16, 16);
        graphics.setColor(secondary);
        graphics.fillOval(size / 6, size / 3, size / 4, size / 4);
        graphics.fillOval(size * 7 / 12, size / 3, size / 4, size / 4);
        graphics.fillRect(size / 3, size * 5 / 8, size / 8, size / 6);
        graphics.fillRect(size * 13 / 24, size * 5 / 8, size / 8, size / 6);

        graphics.setColor(Color.WHITE);
        graphics.setFont(new Font(Font.SANS_SERIF, Font.BOLD, Math.max(12, size / 5)));
        String label = pokemon.getName().substring(0, 1).toUpperCase();
        graphics.drawString(label, size / 2 - graphics.getFontMetrics().stringWidth(label) / 2, size / 2 + 6);

        graphics.dispose();
        return image;
    }

    private Image scale(BufferedImage image, int size) {
        return image.getScaledInstance(size, size, Image.SCALE_FAST);
    }

    private Color typeColor(Type type) {
        return switch (type) {
            case FIRE -> new Color(240, 128, 48);
            case WATER -> new Color(104, 144, 240);
            case GRASS -> new Color(120, 200, 80);
            case ELECTRIC -> new Color(248, 208, 48);
            case ICE -> new Color(152, 216, 216);
            case FIGHTING -> new Color(192, 48, 40);
            case POISON -> new Color(160, 64, 160);
            case GROUND -> new Color(224, 192, 104);
            case FLYING -> new Color(168, 144, 240);
            case PSYCHIC -> new Color(248, 88, 136);
            case BUG -> new Color(168, 184, 32);
            case ROCK -> new Color(184, 160, 56);
            case GHOST -> new Color(112, 88, 152);
            case DRAGON -> new Color(112, 56, 248);
            case DARK -> new Color(112, 88, 72);
            case STEEL -> new Color(184, 184, 208);
            case FAIRY -> new Color(238, 153, 172);
            case NORMAL -> new Color(168, 168, 120);
        };
    }
}
