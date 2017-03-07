package cz.tul.bussiness.workers;

import cz.tul.bussiness.jobs.exceptions.MinimalArgumentsException;
import cz.tul.bussiness.jobs.exceptions.NoTemplateFound;
import cz.tul.bussiness.workers.exceptions.SelectionLayerException;
import cz.tul.entities.PartAttributeValue;

import java.awt.image.BufferedImage;
import java.util.Set;

/**
 * Created by Bc. Marek Jindrák on 13.10.2016.
 */
public interface IMethodWorker {
    String getRealPath();

    /**
     * Metoda vykoná požadovanou funkci
     *
     * @throws SelectionLayerException   - Výjimka  je vyhozena v případě když, není vybrána správná vrstva pro zpracování, možnosti jsou 0, 1, 2
     * @throws MinimalArgumentsException - Výjimka je vyhozena v případě, že funkce nemá dostatečné množství argumentů
     * @throws NoTemplateFound           - Výjimka je vyhozena v případě že neni zadána šablona pro template matching
     */
    void work() throws SelectionLayerException, MinimalArgumentsException, NoTemplateFound;

    void setAttributes(Set<PartAttributeValue> attributes);

    void setImgName(String name);

    BufferedImage getImgData();

    void setImgData(BufferedImage imgData);

    void saveImg();

    void setClassifier(String classifier);

    void setOriginalImageData(BufferedImage originalImageData);


}
