import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;

public class Movie {
    private Document document;

    public Movie() {
        connect();
    }

    private void connect() {
        try {
            document = Jsoup.connect("https://www.metacritic.com/movie/marriage-story").get();
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
}
