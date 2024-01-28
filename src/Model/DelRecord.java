package Model;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.ListIterator;

public class DelRecord<T> implements Command, Serializable {
    private T obj;
    private List<T> records;

    public DelRecord(T obj) {
        this.obj = obj;
    }

    @Override
    public void setRecords(List records) {
        this.records = records;
    }

    @Override
    public T getObj() {
        return obj;
    }

    @Override
    public void execute() {
        Long id;
        Long idRecord;

        for (ListIterator<T> list = records.listIterator(); list.hasNext(); ) {
            T record = list.next();
            try {
                id = Long.parseLong(obj.getClass().getMethod("getId").invoke(obj).toString());
                idRecord = Long.parseLong(record.getClass().getMethod("getId").invoke(record).toString());
                if (id.equals(idRecord)){
                    list.remove();
                }
            } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            }
        }
    }
}
