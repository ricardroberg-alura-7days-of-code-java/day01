import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Scanner;

import io.github.cdimascio.dotenv.Dotenv;
import org.json.JSONArray;
import org.json.JSONObject;

public class ListaFilmesIMDB {
    public static void main(String[] args) throws IOException {

        Scanner sc = new Scanner(System.in);

        final String MOST_POPULAR_TV = "MostPopularTVs/";
        final String MOST_POPULAR_MOVIE = "MostPopularMovies/";
        final String TOP_250_TV = "Top250TVs/";
        final String TOP_250_MOVIE = "Top250Movies/";

        String SearchField = "";
        String titleName = "";
        int consultas = 10;
        int opcao = 0;

        while (opcao != 6) {
            System.out.println("=================================== # de Resltados: " + consultas);
            System.out.println("1 - Alterar número de resultados");
            System.out.println("2 - Exibir lista Top 250 Filmes");
            System.out.println("3 - Exibir lista Top 250 Séries TV");
            System.out.println("4 - Filmes mais populares");
            System.out.println("5 - Séries TV mais populares");
            System.out.println("6 - Sair");
            System.out.println("====================================================");
            opcao = sc.nextInt();
            sc.nextLine();

            if (opcao == 1) {
                System.out.println("Informe a quantidade de resultados da pesquisa  (0 para Todos os Resultados): ");
                consultas = sc.nextInt();
                sc.nextLine();
            } else if (opcao == 2) {
                SearchField = TOP_250_MOVIE;
                titleName = "";
            } else if (opcao == 3) {
                SearchField = TOP_250_TV;
                titleName = "";
            } else if (opcao == 4) {
                SearchField = MOST_POPULAR_MOVIE;
                titleName = "";
            } else if (opcao == 5) {
                SearchField = MOST_POPULAR_TV;
                titleName = "";
            }

            if (opcao != 1 && opcao != 6) {
                ReturnSearch(SearchField, titleName, consultas);
            }
        }
    }

    public static void ReturnSearch(String SearchField, String titleName, int consultas) throws IOException {
        // Uma pequena ajuda do CHATGPT
        Dotenv dotenv = Dotenv.load();
        String api_key = dotenv.get("APIKEY");
        String url = "https://imdb-api.com/pt/API/" + SearchField + api_key + "/" + titleName;

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


        // Convertendo de volta para JSON pois não consegui iterar sobre StringBuilder
        JSONObject json = new JSONObject(response.toString());
        JSONArray results = json.getJSONArray("items");

        if (consultas == 0 || results.length() < consultas) consultas = results.length();

        for (int i = 0; i < consultas; i++) {
            JSONObject result = results.getJSONObject(i);
            String crew = result.getString("crew");
            String title = result.getString("title");
            String year = result.getString("year");
            String rank = result.getString("rank");


            System.out.println("POSIÇÃO: " + rank);
            System.out.println("TITLE: " + title);
            System.out.println("ANO: " + year);
            System.out.println("ELENCO: " + crew);
            System.out.println();
        }


    }

}

