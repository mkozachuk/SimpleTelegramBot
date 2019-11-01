
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
    private Movie movie = new Movie();
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

        if (msg.equals("Hi") || msg.equals("Hello") || msg.equals("/start")) {
            keyboard.clear();
            firstKeyboardRow.clear();
            firstKeyboardRow.add("In Theaters\uD83C\uDFAC");
            firstKeyboardRow.add("Best Movies\uD83C\uDFC6");
            secondKeyboardRow.add("Coming Soon\uD83D\uDD1C");
            keyboard.add(firstKeyboardRow);
            keyboard.add(secondKeyboardRow);
            replyKeyboardMarkup.setKeyboard(keyboard);
        }
        return "Choose...";
    }

    public String getInfoMovie() {
        SendPhoto sendPhoto = new SendPhoto();


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

        String info = "Title: "
                + movie.getTitle()
                + "\n" + movie.getReleaseDate()
                + "\n"
                + "\nScore " + movie.getScore()
                + "\n" + movie.getGenres()
                + "\n"
                + "\nDescription " + movie.getDescription();



        return info;

    }
}
