package Model;

import java.io.Serializable;

public class VariantWithInputAnswer implements ModifyRecord, Serializable {
    private Variant variant;

    public VariantWithInputAnswer() {
        this.variant = new Variant("",true);
    }

    public VariantWithInputAnswer(String answer) {
        this.variant = new Variant(answer,true);
    }

    public Variant getVariant() {
        return variant;
    }

    public void setVariant(Variant variant) {
        this.variant = variant;
    }

    @Override
    public boolean addRecord() {

        return false;
    }

    @Override
    public boolean delRecord(int indexDel) {

        return true;

    }

    @Override
    public boolean editRecord(int indexEdit) {
        return false;
    }

    @Override
    public String toString() {
        return "VariantWithInputAnswer{" +
                "variant=" + variant +
                '}';
    }
}
