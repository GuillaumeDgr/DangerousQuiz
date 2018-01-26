package fr.wcs.dangerousquiz.Models;

/**
 * Created by apprenti on 1/26/18.
 */

public class LevelEnum {

    public enum Level {

        BEGINNER("BEGINNER"),
        INTERMEDIATE("INTERMEDIATE"),
        ADVANCE("ADVANCE");

        private final String stringValue;

        Level(final String s) {
            stringValue = s;
        }
        public String toString() {
            return stringValue;
        }
    }
}
