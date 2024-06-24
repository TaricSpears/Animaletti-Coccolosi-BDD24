package gui.RUN.COMMONS;

public enum Roles {
    PROPRIETARIO(0),
    VETERINARIO(1),
    AMMINISTRATORE(2);

    public final int value;

    private Roles(int value) {
        this.value = value;
    }
}
