package lk.jiat.model;

// Refined Abstraction 1
public class TextEditor extends Application {
    public TextEditor(OperatingSystem os) {
        super(os);
    }

    @Override
    public void start() {
        os.execute("TextEditor");
    }
}
