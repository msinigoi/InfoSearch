package logic;

import org.json.simple.parser.ParseException;

import java.io.IOException;

public class Main {

    public static void main(String[] args) {
        InfoSearch infoSearch = new InfoSearch();
        infoSearch.setField(args[0]);
        infoSearch.setValue(infoSearch.composeValue(args));
        try {
            System.out.println(infoSearch.fetchData());
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
    }
}
