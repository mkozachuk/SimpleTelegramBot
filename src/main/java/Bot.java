
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Paths;
import java.nio.file.Files;


public class Bot extends TelegramLongPollingBot {

    private long chat_id;
    private Movie movie = new Movie();

    public void onUpdateReceived(Update update) {
        update.getUpdateId();

        SendMessage sendMessage = new SendMessage().setChatId(update.getMessage().getChatId());
        chat_id = update.getMessage().getChatId();
        sendMessage.setText(input(update.getMessage().getText()));

        try {
            execute(sendMessage);
        } catch (TelegramApiException e) {
            e.printStackTrace();
            System.out.println("Exception when execute sedMessage");
        }
    }

    public String getBotUsername() {
        return "@JJuniorBot";
    }

    public String getBotToken() {

        return "977535205:AAFphfcBRD0vuO9DrjkqZFHvP8Gosg_sZnE";
    }

    private String input(String msg) {
        if (msg.contains("Hi") || msg.contains("Cześć") || msg.contains("Hello")) {
            return "Hello!";
        }
        if (msg.contains("info")) {
            return getInfoMovie();
        }
        return msg;
    }

    public String getInfoMovie() {
        SendPhoto sendPhoto = new SendPhoto();


        try (InputStream in = new URL(movie.getImage()).openStream()) {

            Files.copy(in, Paths.get("/Users/maksymkozachuk/Desktop/t/1.jpg"));
            sendPhoto.setChatId(chat_id);
            sendPhoto.setPhoto(new File("/Users/maksymkozachuk/Desktop/t/1.jpg"));
            execute(sendPhoto);
            Files.delete(Paths.get("/Users/maksymkozachuk/Desktop/t/1.jpg"));

        } catch (IOException e) {
            System.out.println("Image File Not Found");
        } catch (TelegramApiException e) {
            System.out.println("Exception when execute sendPhoto");
        }

        String info = movie.getTitle()
                + "\n" + movie.getGenres()
                + "\n Description " + movie.getDescription()
                + "\n Score " + movie.getScore();


        return info;

    }
}
