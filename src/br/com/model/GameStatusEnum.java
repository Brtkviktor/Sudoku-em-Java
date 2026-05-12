package br.com.model;

public enum GameStatusEnum {
    NON_STARTED("iniciado"),
    INCOMPLETE("incompleto"),
    COMPLETE("completo");

    private final String label;

   GameStatusEnum(final String label){
        this.label = label;
    }

    public String getLabel() {
        return label;
    }
}
