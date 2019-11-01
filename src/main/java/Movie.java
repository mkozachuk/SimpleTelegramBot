import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class Movie {
    Document document;

    public Movie(String href) {
        connect(href);
    }

    private void connect(String href) {
        try {
            document = Jsoup.connect(href).get();
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("IOE in Jsoup connect");
        }
    }

    public String getTitle() {
        Elements elements = document.getElementsByTag("h1");
        return elements.text();
    }

    public String getScore() {
        Elements elements = document.getElementsByClass("metascore_w larger movie positive");
        elements.remove(1);
        return elements.text();
    }

    public String getDescription() {
        Elements elements = document.getElementsByClass("summary_deck details_section");
        return elements.text();
    }

    public String getGenres() {
        Elements elements = document.getElementsByClass("genres");
        return elements.text();
    }

    public String getImage() {
        Elements elements = document.getElementsByClass("summary_img");
        String url = elements.attr("src");
        return url;

    }

    public String getReleaseDate() {
        Elements elements = document.getElementsByClass("release_date");
        return elements.text();
    }

    public String[] getTopMoviesImg(String period) {
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

        Elements nameMovie = document.getElementsByClass("clamp-image-wrap");
        ArrayList<String> movies = new ArrayList<>();
        for (int i = 0; i < nameMovie.size(); i++) {
            if (i < 10) {
                movies.add(nameMovie.get(i).attr("src"));
            }
        }

        Set<String> linksSet = new HashSet<>();
        linksSet.addAll(movies);
        movies.clear();
        movies.addAll(linksSet);

        String[] stringMovie = new String[movies.size()];
        for (int i = 0; i < stringMovie.length; i++) {
            stringMovie[i] = movies.get(i);
        }

        return stringMovie;
    }

}
