import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Objects;

public class HandlerAUX {
    private FileWriter escrever;
    private int Qnt;
    //private boolean cmc;
    public HandlerAUX() throws IOException {
         escrever = new FileWriter(new File("1.0.csv"));
         int Qnt = 0;
         //cmc = false;
    }

    public void CreateFile(String text) throws IOException {
        String[] aux = text.split("(,)(?=(?:[^\"]|\"[^\"]*\")*$)");
        //System.out.println(aux[3]);
        this.escrever.append(aux[0]+","+aux[2]+","+aux[3]+","+aux[4]+"\n");
        System.out.println("Escrevendo: "+aux[0]);
        Qnt ++;
        if(Qnt >= 1645){
            this.close();
            return;
        }
    }

    public void close() throws IOException {
        this.escrever.close();
    }
}
