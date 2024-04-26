import javax.swing.JFrame;
public class App extends JFrame {
    public App(){
        Board board=new Board();
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("SPIDER RAN");
        setSize(1500,920);
        setResizable(false);
        setLocationRelativeTo(null);
        add(board);
        setVisible(true);
    }
    public static void main(String[] args) throws Exception {
        new App();
    }
}
