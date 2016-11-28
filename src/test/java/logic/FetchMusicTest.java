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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class FetchMusicTest {

    private static final String DINOSAUR_JR_ALBUMS = "Give a Glimpse of What Yer Not\r\n" +
            "Without A Sound\r\n" +
            "You're Living All Over Me\r\n" +
            "Where You Been [Digital Version] [with Bonus Track]\r\n" +
            "Farm\r\n" +
            "Green Mind [Digital Version] [with Bonus Tracks]\r\n" +
            "Beyond\r\n" +
            "I Bet On Sky\r\n" +
            "Bug\r\n" +
            "Dinosaur\r\n" +
            "Hand It Over\r\n" +
            "Chocomel Daze (Live 1987)\r\n" +
            "Whatever's Cool With Me\r\n" +
            "J Mascis Live At CBGB's: The First Acoustic Show\r\n" +
            "Tiny\r\n" +
            "Goin Down\r\n" +
            "Now The Fall b/w Ricochet\r\n" +
            "Pieces b/w Houses\r\n";
    private static final String ARTIST_NAME = "Dinosaur jr";
    private static final String NO_ALBUMS_FOUND_FOR_THIS_ARTIST = "No albums found for this artist.";
    private FetchMusic fetchMusic;

    @Before
    public void init() {
        fetchMusic = new FetchMusicStub();
    }

    @Test
    public void fetchMusicTest() throws IOException, ParseException {
        JSONObject jsonObject = fetchMusic.getJsonFromApi(ARTIST_NAME);

        assertNotNull(jsonObject);
    }

    @Test
    public void fetchMusicNoArtistTest() throws IOException, ParseException {
        assertEquals(NO_ALBUMS_FOUND_FOR_THIS_ARTIST, fetchMusic.formatSpotifyJsonObject(fetchMusic.getJsonFromApi(null)));
    }

    @Test
    public void parseSpotifyJsonObjectTest() throws IOException, ParseException {
        assertEquals(DINOSAUR_JR_ALBUMS, fetchMusic.formatSpotifyJsonObject(fetchMusic.getJsonFromApi(ARTIST_NAME)));
    }

    private class FetchMusicStub extends FetchMusic {

        @Override
        public JSONObject getJsonFromApi(String artistName) {
            if (artistName == null) {
                URL url = Thread.currentThread().getContextClassLoader().getResource("noartist.json");
                File file = new File(url.getPath());
                JSONParser parser = new JSONParser();
                try {
                    Object object = parser.parse(new FileReader(file));
                    return (JSONObject) object;
                } catch (IOException | ParseException e) {
                    e.printStackTrace();
                    return null;
                }
            } else {
                URL url = Thread.currentThread().getContextClassLoader().getResource("DinosaurJr.json");
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
        }
    }
}
