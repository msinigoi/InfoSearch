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

public class FetchMovie {

    private static final String[] NO_MOVIE_ARRAY = new String[]{"tv", "short", "video", "game"};
    private static final String TITLE_DESCRIPTION_STRING = "title_description";
    private static final String TITLE_POPULAR_STRING = "title_popular";
    private static final String TITLE_STRING = "title";
    private static final String SEPARATION_STRING = " - ";
    private static final String NEWLINE_STRING = "\r\n";
    private static final String IMDB_URL_STRING = "http://www.imdb.com/xml/find?json=1&nr=1&tt=on&q=";
    private static final String GET_METHOD_STRING = "GET";
    private static final String NO_MOVIES_FOUND_STRING = "No movies found";

    public JSONObject getJsonFromApi(String movieName) throws IOException, ParseException {
        String url = IMDB_URL_STRING + movieName.replace(" ", "+");

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

    public String formatIMDBJsonObject(JSONObject jsonObject) {
        JSONArray jsonArray = (JSONArray) jsonObject.get(TITLE_POPULAR_STRING);
        String movies = "";
        for (Object movie : jsonArray) {
            if (isMovie((JSONObject) movie)) {
                movies += ((JSONObject) movie).get(TITLE_STRING) + SEPARATION_STRING + ((JSONObject) movie).get(TITLE_DESCRIPTION_STRING) + NEWLINE_STRING;
            }
        }
        return movies.isEmpty() ? NO_MOVIES_FOUND_STRING : movies;
    }

    public boolean isMovie(JSONObject movie) {
        String description = (String) movie.get(TITLE_DESCRIPTION_STRING);
        for (String wordToCheck : NO_MOVIE_ARRAY) {
            if (description == null || description.toLowerCase().contains(wordToCheck)) {
                return false;
            }
        }
        return true;
    }
}
