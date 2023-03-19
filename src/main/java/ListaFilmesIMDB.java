import com.sun.deploy.net.HttpRequest;
import sun.net.www.http.HttpClient;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.io.BufferedReader;
import java.io.InputStreamReader;

import io.github.cdimascio.dotenv.Dotenv;

public class ListaFilmesIMDB {
    public static void main(String[] args) throws IOException {

        // Searcg Title https://imdb-api.com/en/API/SearchTitle/<$IMDB_KEY>/<$title>
        // TOP 250 movies https://imdb-api.com/en/API/Top250Movies/k_uxt1ijvp
        // TOP 250 TVs https://imdb-api.com/en/API/Top250TVs/k_uxt1ijvp
        // Most Popular Movies https://imdb-api.com/en/API/MostPopularMovies/k_uxt1ijvp
        // Most Popular Tvs https://imdb-api.com/en/API/MostPopularTVs/k_uxt1ijvp
        // Comming soon https://imdb-api.com/en/API/ComingSoon/k_uxt1ijvp

        Dotenv dotenv = Dotenv.load();
        String api_key = dotenv.get("APIKEY");
        String query = "inception";
        String url = "https://imdb-api.com/pt/API/SearchTitle/" + api_key + "/" + query;

        // Create a URL object from the request URL
        URL imdbApiUrl = new URL(url);

        // Open a connection to the IMDb API endpoint
        HttpURLConnection con = (HttpURLConnection) imdbApiUrl.openConnection();
        con.setRequestMethod("GET");

        // Set the User-Agent header to identify your application
        con.setRequestProperty("User-Agent", "My Java Application");

        // Read the response from the IMDb API endpoint
        BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuilder response = new StringBuilder();
        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();

        // Print the response from the IMDb API endpoint
        System.out.println(response.toString());

    }
}
