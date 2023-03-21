import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.io.BufferedReader;
import java.io.InputStreamReader;

import io.github.cdimascio.dotenv.Dotenv;

public class ListaFilmesIMDB {
    public static void main(String[] args) throws IOException {

        Dotenv dotenv = Dotenv.load();
        String api_key = dotenv.get("APIKEY");
        String url = "https://imdb-api.com/en/API/Top250TVs/" + api_key;

        URL imdbApiUrl = new URL(url);

        HttpURLConnection con = (HttpURLConnection) imdbApiUrl.openConnection();
        con.setRequestMethod("GET");

        con.setRequestProperty("User-Agent", "My Java Application");

        BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuilder response = new StringBuilder();
        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();

        System.out.println("Resposta: " + response);
    }
}

