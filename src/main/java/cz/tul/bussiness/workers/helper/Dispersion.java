package cz.tul.bussiness.workers.helper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by Bc. Marek Jindrák on 18.10.2016.
 */
public class Dispersion {
    private static final Logger logger = LoggerFactory.getLogger(Dispersion.class);
    private double prumer;
    private double rozptyl;

    public Dispersion(double values[][]) {
        prumer = 0.0;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                prumer = prumer + values[i][j];
            }
        }
        // System.out.println("+"+prumer);
        prumer = prumer / 9;
        double temp = 0;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                temp = temp + Math.pow((values[i][j] - prumer), 2);
            }
        }
        rozptyl = temp / 9;
    }

    public double getPrumer() {
        return prumer;
    }

    public void setPrumer(double prumer) {
        this.prumer = prumer;
    }

    public double getRozptyl() {
        return rozptyl;
    }

    public void setRozptyl(double rozptyl) {
        this.rozptyl = rozptyl;
    }
}
