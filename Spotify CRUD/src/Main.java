public class Main {
    public static void main(String[] args){

        Interface i = new Interface();

        i.Menu();

        /*
        //Carrega e Inicia o PopUp de Carregamento

        LoadingScreen ld = new LoadingScreen();

        JFrame janela = new JFrame("Spotify Music DataBase");
        janela.setIconImage(new ImageIcon("IMG/Slogo.png").getImage());
        janela.add(ld);
        janela.setSize(250,150);
        janela.setUndecorated(true);
        janela.setLocationRelativeTo(null);
        janela.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        janela.setVisible(true);
        TimeUnit.SECONDS.sleep(5);
        //Após o tempo, Desconecta o JFrame para carregar o programa Principal
        janela.dispose();
        janela.remove(ld);
        janela.add(new SpotfyCrud());
        janela.setSize(716,605);
        janela.setLocationRelativeTo(null);
        janela.setUndecorated(false);
        janela.setVisible(true);
        */
    }
}