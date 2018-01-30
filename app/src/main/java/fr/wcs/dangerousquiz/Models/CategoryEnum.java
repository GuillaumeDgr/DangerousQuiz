package fr.wcs.dangerousquiz.Models;

/**
 * Created by apprenti on 1/26/18.
 */

public class CategoryEnum {

    public enum Category {

        HISTORY("HISTORY"),
        MUSIC("MUSIC"),
        SPORT("SPORT"),
        SCIENCE("SCIENCE"),
        ART("ART"),
        CINEMA("CINEMA"),
        GEOGRAPHY("GEOGRAPHY"),
        NATURE("NATURE"),
        NEWS("NEWS"),
        PEOPLE("PEOPLE"),
        GENERALKNOWLEDGE("GENERAL KNOWLEDGE");

        private final String stringValue;

        Category(final String s) {
            stringValue = s;
        }
        public String toString() {
            return stringValue;
        }
    }
}
