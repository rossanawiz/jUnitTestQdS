package kabalpackage.utilities;

import java.awt.AlphaComposite;
import java.awt.Color;

public class LayoutVariables{
    public static final int WINDOW_WIDTH = 800;
    public static final int WINDOW_HEIGHT = 600;
    public static final int KABAL_STACK_COUNT = 7;
    public static final int SEKV_STACK_COUNT = 4;
    public static final int CARD_SPACING = 25;
    public static final int CARD_YSPACING = 20;
    public static final int STACK_SPACING = 20;
    public static final int SECTION_SPACING = 20;
    public static final int CARD_WIDTH = 79;
    public static final int CARD_HEIGHT = 123;
    public static final int MARGIN_LEFT = 60;
    public static final int SEQ_STACK_XPOS_START = 357;
    public static final int SEQ_STACK_YPOS_START = 35;
    public static final int STACK_YPOS_START = 178;
    public static final Color BACKGROUND_COLOR = new Color(55, 95, 20);
    public static final Color PLACEHOLDER_COLOR = Color.WHITE;
    public static final AlphaComposite PLACEHOLDER_ALPHA = AlphaComposite.getInstance(3, 0.05F);
    public static final AlphaComposite PLACEHOLDER_ALPHA_HIGHLIGHT = AlphaComposite.getInstance(3, 0.15F);
    public static final String[] bgNames = { "Standard green", "Standard blue", "Blue boxes", "Green boxes", "Pleasant", "Abstract blue", "Abstract yellow" };
    public static final String[] fileNames = { "bgstandardgreen.png", "bgstandardblue.png", "bgblueboxes.png", "bggreenboxes.png", "bgpleasant.png", "bgabstractblue.png", "bgabstractyellow.png" };
}
