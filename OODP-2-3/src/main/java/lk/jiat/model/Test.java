package lk.jiat.model;

// Client Class
public class Test {
    public static void main(String[] args) {
        // Operating Systems
        OperatingSystem windows = new WindowsOS();
        OperatingSystem linux = new LinuxOS();
        OperatingSystem mac = new MacOS();

        // Running TextEditor across OSs
        Application textEditorWin = new TextEditor(windows);
        textEditorWin.start();

        Application textEditorLinux = new TextEditor(linux);
        textEditorLinux.start();

        Application textEditorMac = new TextEditor(mac);
        textEditorMac.start();

        System.out.println("-------------");

        // Running MusicPlayer across OSs
        Application musicPlayerWin = new MusicPlayer(windows);
        musicPlayerWin.start();

        Application musicPlayerLinux = new MusicPlayer(linux);
        musicPlayerLinux.start();

        Application musicPlayerMac = new MusicPlayer(mac);
        musicPlayerMac.start();
    }
}
