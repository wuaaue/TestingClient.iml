package Model;

public interface ModifyRecord {// описывает поведение окна
    boolean addRecord();//возможность добавлять записи

    boolean delRecord(int index);//возможность удалять записи

    boolean editRecord(int index);//возможность редактировать записи
}
