package Control;

import Model.*;
import View.*;
import javax.swing.*;
import java.io.*;

public class Client extends JFrame implements TCPConnectionListener {
    private TCPConnection connection;
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new Client();
        });
    }
    private Client() {
        try {
            connection = new TCPConnection(this, Config.IP, Config.PORT);
            DialogInitScreen initScreen = new DialogInitScreen(connection, this);
            if (initScreen.isChange()) {
                if (initScreen.isAdmin()) {
                    new InitScreenAdmin(connection);
                } else {
                    new InitScreenUser(initScreen.getUser(),connection);
                }
            } else {
                JOptionPane.showMessageDialog(this, "Пользователь не найден");
                System.exit(0);
            }
        } catch (IOException e) {
            System.out.println("RunnableСоединение не доступно");
        }
    }


    @Override
    public void onConnectionReady(TCPConnection tcpConnection) {
        System.out.println("Соединение готово");
    }

    @Override
    public void onReceiveObject(TCPConnection tcpConnection, Object object) {
        if (object instanceof Command) {
            Command command = (Command) object;//создается объект команды
            if (command.getObj().getClass() == Test.class) {//если тип объекта Test, необходима работа с тестами
                command.setRecords(CacheClient.getInstance().getTests());
                command.execute();
            } else if (command.getObj().getClass() == User.class) {//если тип объекта User, необходима работа с пользователями
                command.setRecords(CacheClient.getInstance().getUsers());
                command.execute();
            }
            if (command.getObj().getClass() == Question.class) {//если тип объекта Question, необходима работа с вопросами
                command.setRecords(CacheClient.getInstance().getQuestions());
                command.execute();
            }
            SwingUtilities.invokeLater(() -> {
                CacheClient.getInstance().casheChanged();
            });
        }
    }

    @Override
    public void onDisconnect(TCPConnection tcpConnection) {
        System.out.println("onDisconnect клиента");
    }

    @Override
    public void onException(TCPConnection tcpConnection, Exception e) {
        System.out.println("Ошибка TCP соедиения: " + e);
    }
}