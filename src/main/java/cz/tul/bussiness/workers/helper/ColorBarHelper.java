package cz.tul.bussiness.workers.helper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Bc. Marek Jindrák on 14.12.2016.
 */
public class ColorBarHelper {
    private static final Logger logger = LoggerFactory.getLogger(ColorBarHelper.class);
    private static List<Color> colors = new ArrayList<>();
    private static ColorBarHelper INSTANCE;

    private ColorBarHelper() {
    }

    public static ColorBarHelper getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new ColorBarHelper();
            initColors();
        }
        return INSTANCE;


    }

    private static void initColors() {
        colors.add(new Color(0, 0, 128));
        colors.add(new Color(0, 0, 140));
        colors.add(new Color(0, 0, 152));
        colors.add(new Color(0, 0, 164));
        colors.add(new Color(0, 0, 176));
        colors.add(new Color(0, 0, 188));
        colors.add(new Color(0, 0, 200));
        colors.add(new Color(0, 0, 212));
        colors.add(new Color(0, 0, 224));
        colors.add(new Color(0, 0, 236));
        colors.add(new Color(0, 0, 248));
        colors.add(new Color(0, 5, 255));
        colors.add(new Color(0, 17, 255));
        colors.add(new Color(0, 29, 255));
        colors.add(new Color(0, 41, 255));
        colors.add(new Color(0, 53, 255));
        colors.add(new Color(0, 65, 255));
        colors.add(new Color(0, 77, 255));
        colors.add(new Color(0, 89, 255));
        colors.add(new Color(0, 101, 255));
        colors.add(new Color(0, 113, 255));
        colors.add(new Color(0, 125, 255));
        colors.add(new Color(0, 137, 255));
        colors.add(new Color(0, 149, 255));
        colors.add(new Color(0, 161, 255));
        colors.add(new Color(0, 173, 255));
        colors.add(new Color(0, 185, 255));
        colors.add(new Color(0, 197, 255));
        colors.add(new Color(0, 209, 255));
        colors.add(new Color(0, 221, 255));
        colors.add(new Color(0, 233, 255));
        colors.add(new Color(0, 245, 255));
        colors.add(new Color(0, 255, 253));
        colors.add(new Color(0, 255, 241));
        colors.add(new Color(0, 255, 229));
        colors.add(new Color(0, 255, 217));
        colors.add(new Color(0, 255, 205));
        colors.add(new Color(0, 255, 193));
        colors.add(new Color(0, 255, 181));
        colors.add(new Color(0, 255, 169));
        colors.add(new Color(0, 255, 157));
        colors.add(new Color(0, 255, 145));
        colors.add(new Color(0, 255, 133));
        colors.add(new Color(0, 255, 121));
        colors.add(new Color(0, 255, 109));
        colors.add(new Color(0, 255, 97));
        colors.add(new Color(0, 255, 85));
        colors.add(new Color(0, 255, 73));
        colors.add(new Color(0, 255, 61));
        colors.add(new Color(0, 255, 49));
        colors.add(new Color(0, 255, 37));
        colors.add(new Color(0, 255, 25));
        colors.add(new Color(0, 255, 13));
        colors.add(new Color(0, 255, 1));
        colors.add(new Color(11, 255, 0));
        colors.add(new Color(23, 255, 0));
        colors.add(new Color(35, 255, 0));
        colors.add(new Color(47, 255, 0));
        colors.add(new Color(59, 255, 0));
        colors.add(new Color(71, 255, 0));
        colors.add(new Color(83, 255, 0));
        colors.add(new Color(95, 255, 0));
        colors.add(new Color(107, 255, 0));
        colors.add(new Color(119, 255, 0));
        colors.add(new Color(131, 255, 0));
        colors.add(new Color(143, 255, 0));
        colors.add(new Color(155, 255, 0));
        colors.add(new Color(167, 255, 0));
        colors.add(new Color(179, 255, 0));
        colors.add(new Color(191, 255, 0));
        colors.add(new Color(203, 255, 0));
        colors.add(new Color(215, 255, 0));
        colors.add(new Color(227, 255, 0));
        colors.add(new Color(239, 255, 0));
        colors.add(new Color(251, 255, 0));
        colors.add(new Color(255, 247, 0));
        colors.add(new Color(255, 235, 0));
        colors.add(new Color(255, 223, 0));
        colors.add(new Color(255, 211, 0));
        colors.add(new Color(255, 199, 0));
        colors.add(new Color(255, 187, 0));
        colors.add(new Color(255, 175, 0));
        colors.add(new Color(255, 163, 0));
        colors.add(new Color(255, 151, 0));
        colors.add(new Color(255, 139, 0));
        colors.add(new Color(255, 127, 0));
        colors.add(new Color(255, 115, 0));
        colors.add(new Color(255, 103, 0));
        colors.add(new Color(255, 91, 0));
        colors.add(new Color(255, 79, 0));
        colors.add(new Color(255, 67, 0));
        colors.add(new Color(255, 55, 0));
        colors.add(new Color(255, 43, 0));
        colors.add(new Color(255, 31, 0));
        colors.add(new Color(255, 19, 0));
        colors.add(new Color(255, 7, 0));
        colors.add(new Color(250, 0, 0));
        colors.add(new Color(238, 0, 0));
        colors.add(new Color(226, 0, 0));
        colors.add(new Color(214, 0, 0));
        colors.add(new Color(202, 0, 0));
        colors.add(new Color(190, 0, 0));
        colors.add(new Color(178, 0, 0));
        colors.add(new Color(166, 0, 0));
        colors.add(new Color(154, 0, 0));
        colors.add(new Color(142, 0, 0));


    }

    public Color getColor(int index) {
        if (index > 100 || 0 > index) {
            index = 0;
        }
        return colors.get(index);

    }
}
