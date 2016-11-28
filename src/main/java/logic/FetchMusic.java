package logic;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class FetchMusic {

    private static final String GET_METHOD_STRING = "GET";
    private static final String SPOTIFY_URL_STRING = "https://api.spotify.com/v1/search?q=";
    private static final String SPOTIFY_URL_TYPE_ALBUM_STRING = "&type=album";
    private static final String ALBUMS_STRING = "albums";
    private static final String ITEMS_STRING = "items";
    private static final String NAME_STRING = "name";
    private static final String NEWLINE_STRING = "\r\n";
    private static final String NO_ALBUMS_FOUND_STRING = "No albums found for this artist.";

    public JSONObject getJsonFromApi(String artistName) throws IOException, ParseException {
        String url = SPOTIFY_URL_STRING + artistName.replace(" ", "%20") + SPOTIFY_URL_TYPE_ALBUM_STRING;

        URL obj = new URL(url);
        HttpURLConnection connection = (HttpURLConnection) obj.openConnection();
        connection.setRequestMethod(GET_METHOD_STRING);

        int responseCode = connection.getResponseCode();
        if (responseCode == 200) {
            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String inputLine;
            StringBuilder response = new StringBuilder();

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();

            JSONParser parser = new JSONParser();
            return (JSONObject) parser.parse(response.toString());
        } else {
            System.err.println(connection.getResponseCode() + " - " + connection.getResponseMessage());
            return null;
        }
    }

    String formatSpotifyJsonObject(JSONObject jsonObject) {
        String albums = "";
        JSONArray jsonArrayAlbums = (JSONArray) ((JSONObject) jsonObject.get(ALBUMS_STRING)).get(ITEMS_STRING);
        for (Object album : jsonArrayAlbums) {
            albums += ((JSONObject) album).get(NAME_STRING) + NEWLINE_STRING;
        }
        return albums.isEmpty() ? NO_ALBUMS_FOUND_STRING : albums;
    }

}
