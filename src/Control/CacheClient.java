package Control;

import Model.Question;
import Model.Test;
import Model.User;

import java.util.ArrayList;
import java.util.List;

public class CacheClient implements Subject {
    private final List<User> users;
    private final List<Test> tests;
    private final List<Question> questions;
    private final List<Observer> observers;
    private static CacheClient cacheClient;//набор коллекций пользователей, тестов, вопросов, наблюдателей

    private CacheClient() {
        users = new ArrayList<>();
        tests = new ArrayList<>();
        questions = new ArrayList<>();
        observers = new ArrayList<>();
    }

    public static synchronized CacheClient getInstance() {
        if (cacheClient == null) {
            cacheClient = new CacheClient();
        }
        return cacheClient;
    }


    public List<User> getUsers() {
        return users;
    }

    public List<Test> getTests() {
        return tests;
    }

    public List<Question> getQuestions() {
        return questions;
    }

    @Override
    public void registerObserver(Observer o) {
        observers.add(o);
    }

    @Override
    public void removeObserver(Observer o) {
        int index = observers.indexOf(o);
        if (index >= 0) {
            observers.remove(index);
        }

    }

    @Override
    public void notifyObservers() {
        for (Observer observer : observers) {
            observer.update();
        }
    }

    public void casheChanged() {
        notifyObservers();
    }
}
