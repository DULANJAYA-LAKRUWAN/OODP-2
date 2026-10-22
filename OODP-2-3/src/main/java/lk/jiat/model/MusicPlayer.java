package lk.jiat.model;

// Refined Abstraction 2
public class MusicPlayer extends Application {
    public MusicPlayer(OperatingSystem os) {
        super(os);
    }

    @Override
    public void start() {
        os.execute("MusicPlayer");
    }
}

