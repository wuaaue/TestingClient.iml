package Control;

public interface Subject {//паттерн обозреватель
    void registerObserver(Observer o);//метод добавления слушателя
    void removeObserver(Observer o);//метод удаления слушателя
    void notifyObservers();//метод оповещения слушателей
}
