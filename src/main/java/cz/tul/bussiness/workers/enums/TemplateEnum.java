package cz.tul.bussiness.workers.enums;

/**
 * Created by Bc. Marek Jindrák on 20.02.2017.
 */
public enum TemplateEnum {
    TM("TM"),
    TM_CCOEFF("TM_CCOEFF"),
    TM_CCOEFF_NORMED("TM_CCOEFF_NORMED"),
    TM_CCORR("TM_CCORR"),
    TM_CCORR_NORMED("TM_CCORR_NORMED"),
    TM_SQDIFF("TM_SQDIFF"),
    TM_SQDIFF_NORMED("TM_SQDIFF_NORMED");


    private String templateName;

    TemplateEnum(String key) {
        this.templateName = key;
    }

    public String getTemplateName() {
        return templateName;
    }
}

