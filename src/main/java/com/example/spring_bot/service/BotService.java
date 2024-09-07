package com.example.spring_bot.service;

import com.example.spring_bot.config.BotConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.commands.SetMyCommands;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.commands.BotCommand;
import org.telegram.telegrambots.meta.api.objects.commands.scope.BotCommandScope;
import org.telegram.telegrambots.meta.api.objects.commands.scope.BotCommandScopeDefault;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import javax.xml.stream.events.Comment;
import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class BotService extends TelegramLongPollingBot {
    private final BotConfig botConfig;
    private static final String HELP_TEXT = "This bot is created to demonstrate Spring capabilities.\n\n" +
            "You can execute command from te main menu on the left or by typing a command:\n\n" +
            "Type /start to see a welcome message\n\n" +
            "Type /myprofile to see your profile\n\n" +
            "Type /deleteprofile to delete your profile\n\n" +
            "Type /help to see  help commmand this bot\n\n" +
            "Type /settings to see  settings this bot\n\n" +
            "--------------------------------------------\n\n" +
            "Этот бот создает и помогает найти знакомства в интернете.\n\n" +
            "Здесь ты сможешь увидеть все команды который имеет данный бот и их информацию:\n\n" +
            "Тип /start увидишь сообщение, которое приходиь при первом запуске программы\n\n" +
            "Тип /myprofile увидишь информацию о своем созданном профиле\n\n" +
            "Тип /deleteprofile удалишь свой профиль\n\n" +
            "Тип /help увидишь информацию о данном боте и его командах\n\n" +
            "Тип /settings увидишь информацию о настройках данного бота";

    @Autowired
    public BotService(BotConfig botConfig) {
        this.botConfig = botConfig;
        List<BotCommand> listofCommands = new ArrayList();
        listofCommands.add(new BotCommand("/start", "English: get a welcome message " + " || " + " Русский: получите сообщение, которое при старте"));
        listofCommands.add(new BotCommand("/myprofile", "English: get your profile " + " || " + " Русский: зайдешь в свой профиль"));
        listofCommands.add(new BotCommand("/deleteprofile", "English: delete my profile " + " || " + " Русский: удалить свой профиль"));
        listofCommands.add(new BotCommand("/help", "English: info how to use this bot " + " || " + " Русский: иформация об всех командах, которая использует бот"));
        listofCommands.add(new BotCommand("/settings", "English: set your preferences " + " || " + "Русский: настройки бота"));
        try {
            this.execute(new SetMyCommands(listofCommands, new BotCommandScopeDefault(), null));
        } catch (TelegramApiException e) {
            log.error("Error setting bot's command list: " + e.getMessage());
        }
    }

    @Override
    public String getBotUsername() {
        return botConfig.getBot_name();
    }

    @Override
    public String getBotToken() {
        return botConfig.getBot_token();
    }

    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            String messageText = update.getMessage().getText();
            long chatId = update.getMessage().getChatId();
            switch (messageText) {
                case "/start":
                    try {
                        startCommandReceived(chatId, update.getMessage().getChat().getFirstName());
                    } catch (TelegramApiException e) {
                        throw new RuntimeException(e);
                    }
                    break;
                case "/help":
                    try {
                        sendMessage(chatId, HELP_TEXT);
                    } catch (TelegramApiException e) {
                        throw new RuntimeException(e);
                    }
                    break;
                default:
                    try {
                        sendMessage(chatId, "Пока ничего не придумал!");
                    } catch (TelegramApiException e) {
                        throw new RuntimeException(e);
                    }
            }
        }
    }

    public void startCommandReceived(long chatId, String name) throws TelegramApiException {
        String answer = ("Привет, " + name + " ,рад тебя видеть!))");
        sendMessage(chatId, answer);
    }

    public void sendMessage(long chatId, String sendText) throws TelegramApiException {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(String.valueOf(chatId));
        sendMessage.setText(sendText);
        execute(sendMessage);
    }
}
