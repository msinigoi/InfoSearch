package logic;

import org.json.simple.parser.ParseException;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.*;

public class InfoSearchTest {

    private static final String ERROR_STRING = "error";
    private static final String ERROR_INVALID_FIELD_STRING = "ERROR: invalid field, insert music or movie.";
    private static final String ERROR_INVALID_VALUE_STRING = "ERROR: value is null.";
    private static final String MOVIE_STRING = "movie";
    private static final String MOVIE_NAME_STRING = "Indiana Jones";
    private static final String MUSIC_NAME_STRING = "Dinosaur Jr";
    private static final String RESPONSE_WITH_HTML_STRING = "Raiders of the Lost Ark - 1981,     <a href='/name/nm0000229/'>Steven Spielberg</a>\n" +
            "Indiana Jones and the Last Crusade - 1989,     <a href='/name/nm0000229/'>Steven Spielberg</a>\n" +
            "Indiana Jones and the Temple of Doom - 1984,     <a href='/name/nm0000229/'>Steven Spielberg</a>\n" +
            "Indiana Jones and the Kingdom of the Crystal Skull - 2008,     <a href='/name/nm0000229/'>Steven Spielberg</a>";
    private static final String RESPONSE_WITHOUT_HTML_STRING = "Raiders of the Lost Ark - 1981, Steven Spielberg\n" +
            "Indiana Jones and the Last Crusade - 1989, Steven Spielberg\n" +
            "Indiana Jones and the Temple of Doom - 1984, Steven Spielberg\n" +
            "Indiana Jones and the Kingdom of the Crystal Skull - 2008, Steven Spielberg";
    private InfoSearch infoSearch;

    @Before
    public void init() {
        infoSearch = new InfoSearch();
    }

    @Test
    public void setFieldAndValueTest() {
        infoSearch.setField(MOVIE_STRING);
        //infoSearch.setValue(MOVIE_NAME_STRING);

        assertEquals(MOVIE_STRING, infoSearch.getField());
        assertEquals(MOVIE_NAME_STRING, infoSearch.getValue());
    }

    @Test
    public void checkFetchDataWrongFieldTest() throws IOException, ParseException {
        infoSearch.setField(ERROR_STRING);
        //infoSearch.setValue(MUSIC_NAME_STRING);

        assertEquals(ERROR_INVALID_FIELD_STRING, infoSearch.fetchData());
    }

    @Test
    public void stripHTMLFromResponseTest() {
        assertEquals(RESPONSE_WITHOUT_HTML_STRING, infoSearch.stripHtml(RESPONSE_WITH_HTML_STRING));
    }

    @Test
    public void valueFieldIsNullTest() throws IOException, ParseException {
        infoSearch.setField(MOVIE_STRING);
        infoSearch.setValue(null);

        assertEquals(ERROR_INVALID_VALUE_STRING, infoSearch.fetchData());
    }

    @Test
    public void composeValueTest() {
        assertEquals("the star wars", infoSearch.composeValue(new String[]{"movie", "the", "star", "wars"}));
        assertEquals("", infoSearch.composeValue(new String[]{"movie"}));
    }
}