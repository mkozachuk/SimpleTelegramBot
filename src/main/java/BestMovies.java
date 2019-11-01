import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;

public class BestMovies {
    Document document;

    public String[] getTopMovies(String period) {
        if (period.equals("All Time")) {
            try {
                document = Jsoup.connect("https://www.metacritic.com/browse/movies/score/metascore/all/filtered?sort=desc").get();
            } catch (IOException e) {
                e.printStackTrace();
                System.out.println("IOE in getTopMovies (All Time)");
            }

        }
        if (period.equals("Last 90 Days")) {
            try {
                document = Jsoup.connect("https://www.metacritic.com/browse/movies/score/metascore/90day/filtered?sort=desc").get();
            } catch (IOException e) {
                e.printStackTrace();
                System.out.println("IOE in getTopMovies (90 Days)");
            }

        }
        if (period.equals("Most Discussed")) {
            try {
                document = Jsoup.connect("https://www.metacritic.com/browse/movies/score/metascore/discussed/filtered?sort=desc").get();
            } catch (IOException e) {
                e.printStackTrace();
                System.out.println("IOE in getTopMovies (Most Discussed)");
            }

        }

        String[] info = new String[20];

        Elements title = document.getElementsByClass("title");
        Elements score = document.getElementsByClass("metascore_w large movie positive perfect");
        Elements description = document.getElementsByClass("summary");
        Elements releaseDate = document.getElementsByClass("clamp-details");

        for (int i = 0; i < title.size(); i++) {
            if (i < 20) {
                info[i] = "\n\n\n"
                        + "Title: " + title.get(i).text() + "\n"
                        + "Score: " + score.get(i).text() + "\n"
                        + "\n"
                        + "Release Date: " + releaseDate.get(i).text() + "\n"
                        + description.get(i).text() + "\n";
            }


        }

        return info;
    }

}
