package Model;

import View.DialogVariantScreen;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.Serializable;
import java.util.ArrayList;

public class VariantWithOneAnswerTrue implements ModifyRecord, Serializable {
    private ArrayList<Variant> variants;


    public VariantWithOneAnswerTrue() {
       this.variants = new ArrayList<>();

    }

    public ArrayList<Variant> getVariants() {
        return variants;
    }
    @Override
    public boolean addRecord() {
        DialogVariantScreen addVS = new DialogVariantScreen("Добавить запись", new Variant());
        Variant variant = addVS.getVariant();
        if (addVS.isChange()) {
            variants.add(variant);
            return true;
        }
        return false;
    }

    @Override
    public boolean delRecord(int indexDel) {
        variants.remove(indexDel);
        return true;
    }

    @Override
    public boolean editRecord(int indexEdit) {
        Variant variant = variants.get(indexEdit);
        DialogVariantScreen addVS = new DialogVariantScreen("Изменить запись", variant);
        variant = addVS.getVariant();
        if (addVS.isChange()) {
            variants.set(indexEdit, variant);
            return true;
        }
        return false;
    }

    @Override
    public String toString() {
        return "VariantWithOneAnswerTrue{" +
                "variants=" + variants +
                '}';
    }
}
