package View;

import Model.Variant;

import java.util.List;

public class VariantTableModelOne extends VariantTableModel {
    private List<Variant> variants;

    public VariantTableModelOne(List<Variant> variants) {
        super(variants);
        this.variants = variants;
    }

    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
        for (Variant variant:variants){
            variant.setStatus(false);
        }
        if (columnIndex == 1) {
            variants.get(rowIndex).setStatus(Boolean.TRUE.equals(aValue));
        }
        fireTableDataChanged();
    }
}
