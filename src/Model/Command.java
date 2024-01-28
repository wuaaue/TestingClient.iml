package Model;

import java.util.List;

public interface Command<T> {
    void execute();

    T getObj();

    void setRecords(List<T> records);
}
