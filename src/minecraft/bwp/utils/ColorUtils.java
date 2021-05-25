package bwp.utils;

import java.awt.*;

public class ColorUtils {
    public static Color fromHex(String colorStr) {
        return new Color(
                Integer.valueOf(colorStr.substring( 3, 5 ), 16),
                Integer.valueOf( colorStr.substring( 5, 7 ), 16 ),
                Integer.valueOf( colorStr.substring( 7, 9 ), 16 ),
                Integer.valueOf( colorStr.substring( 1, 3 ), 16 ));
    }
}
