package logic;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;

import static org.junit.Assert.*;

public class FetchMovieTest {

    private static final String INDIANA_JONES_MOVIES_STRING = "Raiders of the Lost Ark - 1981, Steven Spielberg\r\n" +
    "Indiana Jones and the Last Crusade - 1989, Steven Spielberg\r\n" +
    "Indiana Jones and the Temple of Doom - 1984, Steven Spielberg\r\n" +
    "Indiana Jones and the Kingdom of the Crystal Skull - 2008, Steven Spielberg\r\n";
    private static final String TITLE_DESCRIPTION_STRING = "title_description";
    private static final String MOVIE_NAME_STRING = "Indiana Jones";
    private FetchMovie fetchMovie;

    @Before
    public void init() {
        fetchMovie = new FetchMovieStub();
    }

    @Test
    public void fetchMovieTest() throws IOException, ParseException {
        JSONObject jsonObject = fetchMovie.getJsonFromApi(MOVIE_NAME_STRING);

        assertNotNull(jsonObject);
    }

    @Test
    public void parseJsonObjectTest() throws IOException, ParseException {
        assertEquals(INDIANA_JONES_MOVIES_STRING, fetchMovie.formatIMDBJsonObject(fetchMovie.getJsonFromApi(MOVIE_NAME_STRING)));
    }

    @Test
    public void isNotMovieTest() {
        JSONObject movie = new JSONObject();
        movie.put(TITLE_DESCRIPTION_STRING, "TV series");

        assertFalse(fetchMovie.isMovie(movie));
    }

    @Test
    public void isNotMovieButNullTest() {
        JSONObject movie = new JSONObject();
        movie.put(TITLE_DESCRIPTION_STRING, null);

        assertFalse(fetchMovie.isMovie(movie));
    }

    @Test
    public void isMovieTest() {
        JSONObject movie = new JSONObject();
        movie.put(TITLE_DESCRIPTION_STRING, "1989, Steven Spielberg");

        assertTrue(fetchMovie.isMovie(movie));
    }

    private class FetchMovieStub extends FetchMovie {

        @Override
        public JSONObject getJsonFromApi(String movieName) {
            URL url = Thread.currentThread().getContextClassLoader().getResource("IndianaJones.json");
            File file = new File(url.getPath());
            JSONParser parser = new JSONParser();
            try {
                Object object = parser.parse(new FileReader(file));
                return (JSONObject) object;
            } catch (IOException | ParseException e) {
                e.printStackTrace();
                return null;
            }
        }

        @Override
        public String formatIMDBJsonObject(JSONObject jsonObject) {
            return super.formatIMDBJsonObject(jsonObject);
        }

        @Override
        public boolean isMovie(JSONObject movie) {
            return super.isMovie(movie);
        }
    }
}
