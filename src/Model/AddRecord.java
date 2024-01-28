package Model;

import java.io.Serializable;
import java.util.List;

public class AddRecord<T> implements Command, Serializable {
    private T obj;
    private List<T> records;

    public AddRecord(T obj) {
        this.obj = obj;
    }


    @Override
    public T getObj() {
        return obj;
    }


    @Override
    public void setRecords(List records) {
        this.records = records;
    }

    @Override
    public void execute() {
        records.add(obj);
    }

}
