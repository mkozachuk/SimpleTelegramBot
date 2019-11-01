import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.List;

public class Bot extends TelegramLongPollingBot {

    public void onUpdateReceived(Update update) {
        update.getUpdateId();

        SendMessage sendMessage = new SendMessage().setChatId(update.getMessage().getChatId());

        if (update.getMessage().getText().equals("Cześć")){
            sendMessage.setText("Cześć!");
            try{
                execute(sendMessage);
            }
            catch (TelegramApiException e){
                e.printStackTrace();
                System.out.println("Exception when execute sendMessage");
            }
        }

    }

    public String getBotUsername() {
        return "@JJuniorBot";
    }

    public String getBotToken() {

        return "977535205:AAFphfcBRD0vuO9DrjkqZFHvP8Gosg_sZnE";
    }
}
