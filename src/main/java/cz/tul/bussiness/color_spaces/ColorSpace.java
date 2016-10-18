package cz.tul.bussiness.color_spaces;

import java.util.List;

/**
 * Created by Marek on 17.08.2016.
 */
public interface ColorSpace {
    /**
     * Save currently data
     */
    void saveSpace();

    /**
     * Returns real location of picture
     *
     * @return
     */
    String getRealLocation();

    /**
     * Returns list of available channels
     *
     * @return
     */
    List<String> getData();// list MAt je správně

    /**
     * This method creating matching histogram
     */
    void makeHistogram();
}
