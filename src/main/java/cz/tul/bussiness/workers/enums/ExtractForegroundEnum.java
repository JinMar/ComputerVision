package cz.tul.bussiness.workers.enums;

/**
 * Created by Bc. Marek Jindrák on 07.03.2017.
 */
public enum ExtractForegroundEnum {
    EXTRACT_FOREGROUND("EXTRACT_FOREGROUND"),
    EXTRACT_FOREGROUND_MASK("EXTRACT_FOREGROUND_MASK"),
    GC_INIT_WITH_MASK("GC_INIT_WITH_MASK"),
    GC_INIT_WITH_RECT("GC_INIT_WITH_RECT");


    private String extractForegroundName;

    ExtractForegroundEnum(String key) {
        this.extractForegroundName = key;
    }

    public String getExtractForegroundName() {
        return extractForegroundName;
    }
}
