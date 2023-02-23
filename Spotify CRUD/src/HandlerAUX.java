import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Objects;

public class HandlerAUX {
    private FileWriter escrever;
    private boolean cmc;
    public HandlerAUX() throws IOException {
         escrever = new FileWriter(new File("newSpotifyVER1.0.csv"));
         cmc = false;
    }

    public void CreateFile(String text) throws IOException {
        String[] aux = text.split("(,)(?=(?:[^\"]|\"[^\"]*\")*$)");
        //System.out.println(aux[3]);
        if(Objects.equals(aux[2], "2020-01-01")){
            cmc = true;
        }
        if(cmc){
            this.escrever.append(aux[0]+","+aux[2]+","+aux[3]+","+aux[4]+"\n");
            System.out.println("Escrevendo: "+aux[0]);
        }
    }

    public void close() throws IOException {
        this.escrever.close();
    }
}
