package logic;

import org.apache.commons.lang.StringEscapeUtils;
import org.json.simple.parser.ParseException;
import java.io.IOException;

class InfoSearch {

    private static final String MOVIE_STRING = "movie";
    private static final String MUSIC_STRING = "music";
    private static final String ERROR_INVALID_FIELD_STRING = "ERROR: invalid field, insert music or movie.";
    private static final String ERROR_VALUE_STRING = "ERROR: value is null.";
    private static final String TAB_CHAR = "  ";
    private static final String SPACE_CHAR = " ";
    private String field;
    private String value = "";

    void setField(String field) {
        this.field = field;
    }

    void setValue(String value) {
        this.value = value;
    }

    String getField() {
        return field;
    }

    String getValue() {
        return value;
    }

    String fetchData() throws IOException, ParseException {
        if (isValueNull()) {
            return ERROR_VALUE_STRING;
        }
        if (MOVIE_STRING.equalsIgnoreCase(field)) {
            FetchMovie fetchMovie = new FetchMovie();
            return stripHtml(fetchMovie.formatIMDBJsonObject(fetchMovie.getJsonFromApi(value)));
        } else if (MUSIC_STRING.equalsIgnoreCase(field)) {
            FetchMusic fetchMusic = new FetchMusic();
            return fetchMusic.formatSpotifyJsonObject(fetchMusic.getJsonFromApi(value));
        } else {
            return ERROR_INVALID_FIELD_STRING;
        }
    }

    private boolean isValueNull() {
        return value == null || value.isEmpty();
    }

    String stripHtml(String responseWithHtml) {
        return StringEscapeUtils.unescapeHtml(responseWithHtml.replaceAll("\\<.*?>", "").replaceAll(TAB_CHAR, ""));
    }

    String composeValue(String[] args) {
        StringBuilder builder = new StringBuilder();
        for (int i = 1; i < args.length; i++) {
            String s = i == args.length - 1 ? args[i] : args[i] + SPACE_CHAR;
            builder.append(s);
        }
        return builder.toString();
    }

}
