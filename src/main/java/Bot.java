
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Paths;
import java.nio.file.Files;
import java.util.ArrayList;


public class Bot extends TelegramLongPollingBot {

    private long chat_id;
    String lastMessage = "";
    BestMovies best = new BestMovies();
    ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();

    public void onUpdateReceived(Update update) {
        update.getUpdateId();

        SendMessage sendMessage = new SendMessage().setChatId(update.getMessage().getChatId());
        chat_id = update.getMessage().getChatId();


        String text = update.getMessage().getText();
        sendMessage.setReplyMarkup(replyKeyboardMarkup);

        try {
            sendMessage.setText(getMessage(text));
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

    public String getMessage(String msg) {
        ArrayList keyboard = new ArrayList();
        KeyboardRow firstKeyboardRow = new KeyboardRow();
        KeyboardRow secondKeyboardRow = new KeyboardRow();

        replyKeyboardMarkup.setSelective(true);
        replyKeyboardMarkup.setResizeKeyboard(true);
        replyKeyboardMarkup.setOneTimeKeyboard(false);






/*
        if (msg.equals("Hi") || msg.equals("Hello") || msg.equals("/start")) {
            lastMessage = msg;
            keyboard.clear();
            firstKeyboardRow.clear();
            firstKeyboardRow.add("In Theaters\uD83C\uDFAC");
            firstKeyboardRow.add("Best Movies\uD83C\uDFC6");
            secondKeyboardRow.add("Coming Soon\uD83D\uDD1C");
            keyboard.add(firstKeyboardRow);
            keyboard.add(secondKeyboardRow);
            replyKeyboardMarkup.setKeyboard(keyboard);
        }

 */

        if (lastMessage.equals("In Theaters\uD83C\uDFAC") || lastMessage.equals("Best Movies\uD83C\uDFC6") || lastMessage.equals("Coming Soon\uD83D\uDD1C")) {
            if (msg.equals("All Time")) {
                return getInfoMovie(best.getTopMovies(msg));
            }
            if (msg.equals("Last 90 Days")) {
                return getInfoMovie(best.getTopMovies(msg));
            }
            if (msg.equals("Most Discussed")) {
                return getInfoMovie(best.getTopMovies(msg));
            }
        }
        if (msg.equals("In Theaters\uD83C\uDFAC") || lastMessage.equals("Best Movies\uD83C\uDFC6") || lastMessage.equals("Coming Soon\uD83D\uDD1C")) {
            lastMessage = msg;
            keyboard.clear();
            firstKeyboardRow.clear();
            firstKeyboardRow.add("All Time");
            firstKeyboardRow.add("Last 90 Days");
            firstKeyboardRow.add("Most Discussed");
            firstKeyboardRow.add("Menu");
            keyboard.addAll(firstKeyboardRow);
            replyKeyboardMarkup.setKeyboard(keyboard);
            return "Choose.....";
        }
        return "smt";


    }

    public String getInfoMovie(String[] href) {
        SendPhoto sendPhoto = new SendPhoto();
        String info = "";

        for (int i = 0; i < href.length; i++) {
            info = "";
            Movie movie = new Movie(href[i]);
            if (Files.exists(Paths.get("/Users/maksymkozachuk/IdeaProjects/STB/src/main/resources/img/"))) {
                try {
                    Files.delete(Paths.get("/Users/maksymkozachuk/IdeaProjects/STB/src/main/resources/img/"));
                } catch (IOException e) {
                    e.printStackTrace();
                    System.out.println("IOE in getInfoMovie when del");
                }
            }


            try (InputStream in = new URL(movie.getImage()).openStream()) {

                Files.copy(in, Paths.get("/Users/maksymkozachuk/IdeaProjects/STB/src/main/resources/img/img.jpg"));
                sendPhoto.setChatId(chat_id);
                sendPhoto.setPhoto(new File("/Users/maksymkozachuk/IdeaProjects/STB/src/main/resources/img/img.jpg"));
                execute(sendPhoto);
                Files.delete(Paths.get("/Users/maksymkozachuk/IdeaProjects/STB/src/main/resources/img/img.jpg"));

            } catch (IOException e) {
                System.out.println("Image File Not Found");
            } catch (TelegramApiException e) {
                System.out.println("Exception when execute sendPhoto");
            }

            info = "Title: "
                    + movie.getTitle()
                    + "\n" + movie.getReleaseDate()
                    + "\n"
                    + "\nScore " + movie.getScore()
                    + "\n" + movie.getGenres()
                    + "\n"
                    + "\nDescription " + movie.getDescription();


            SendMessage sendMessage = new SendMessage().setChatId(chat_id);
            try {
                sendMessage.setText(info);
                execute(sendMessage);
            } catch (TelegramApiException e) {
                e.printStackTrace();
                System.out.println("TelegramAipException");
            }
        }
        return "Done!";
    }

    public String getBestMovies(String[] text) {
        SendMessage sendMessage = new SendMessage().setChatId(chat_id);
        for (int i = 0; i < text.length; i++) {
            try {
                sendMessage.setText(text[i]);
                execute(sendMessage);
            } catch (TelegramApiException e) {
                e.printStackTrace();
                System.out.println("Telegram exception (getBestMovies)");
            }

        }
        return "Done";

    }
}
