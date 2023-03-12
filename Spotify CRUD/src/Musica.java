import java.io.*;
import java.util.Arrays;
import java.util.Date;
import java.util.Vector;

public class Musica {
    private int ID;
    private String Nome;
    private Date Data;
    private String URL;
    private Vector<String> Cantores;

    public Musica() {
    }

    public Musica(String nome, Date data, String URL, Vector<String> cantores) {
        Nome = nome;
        Data = data;
        this.URL = URL;
        Cantores = cantores;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getNome() {
        return Nome;
    }

    public void setNome(String nome) {
        Nome = nome;
    }

    public Date getData() {
        return Data;
    }

    public void setData(Date data) {
        Data = data;
    }

    public String getURL() {
        return URL;
    }

    public void setURL(String URL) {
        this.URL = URL;
    }

    public Vector<String> getCantores() {
        return Cantores;
    }

    public void setCantores(Vector<String> cantores) {
        Cantores = cantores;
    }

    public String ListaHandlerOutput(Vector<String> lista){
        String output = "";

        for (int i = 0; i < lista.size()-1; i++) {
            output += lista.get(i) + "/j";
        }
        if(lista.size() > 0){
            output += lista.get(lista.size()-1);
        }
        return output;
    }

    public Vector<String> ListaHandlerInput(String input){
        String[] aux = input.split("/j");
        Vector<String> Vinput = new Vector<String>(Arrays.asList(aux));

        return Vinput;
    }

    public byte[] DevolveBytes() throws IOException {

        ByteArrayOutputStream bout = new ByteArrayOutputStream();
        DataOutputStream data = new DataOutputStream(bout);

        data.writeInt(this.ID);
        data.writeUTF(this.Nome);
        data.writeLong(this.Data.getTime());
        data.writeUTF(this.URL);
        data.writeUTF(ListaHandlerOutput(Cantores));

        return bout.toByteArray();
    }

    public void CarregaBytes(byte[] load) throws IOException {
        ByteArrayInputStream bin = new ByteArrayInputStream(load);
        DataInputStream data = new DataInputStream(bin);

        this.ID = data.readInt();
        this.Nome = data.readUTF();
        this.Data = new Date(data.readLong());
        this.URL = data.readUTF();
        this.Cantores = ListaHandlerInput(data.readUTF());

    }
}
