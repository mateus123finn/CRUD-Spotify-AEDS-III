import java.io.EOFException;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Scanner;
import java.util.Vector;

public class FileHandler {
    private String Path;
    private int ID;

    public FileHandler() {
        this.Path = null;
        this.ID = -1;
    }

    public String getPath() {
        return this.Path;
    }

    public String parseTexto(String texto){
        String resp = "";
        for(int i = 0; i < texto.length(); i++){
            if(texto.charAt(i) != '"'){
                resp += texto.charAt(i);
            }
        }

        return resp;

    }
    public Vector<String> removeEspaco(String[] texto){
        Vector<String> resp = new Vector<String>();

        for(int i = 0; i < texto.length; i++){
            if(!texto[i].equals("") && !texto[i].equals(" ")){
                resp.add(texto[i]);
            }
        }
        return resp;

    }
    public Vector<String> RetornaLista(String texto){
        String[] aux = texto.split("[', \\[\\]|\"\\n\\r]",-1);
        return removeEspaco(aux);
    }

    public boolean LoadCSV (String path) {
        boolean status;
        try {

            System.out.println("Carregando Arquivo "+ path+" ...");

            File raw = new File("./BD/"+path+".CSV");

            this.Path = "./FILES/"+ raw.getName().split("\\.")[0]+".mat";

            RandomAccessFile rd = new RandomAccessFile(this.Path,"rw");
            DateFormat df = new SimpleDateFormat("yyyy-MM-dd");

            rd.writeInt(0);

            long size_raw = raw.length();
            long size = 0;

            Scanner sc = new Scanner(raw);
            while (sc.hasNext()) {
                String[] aux = sc.nextLine().split("(,)(?=(?:[^\"]|\"[^\"]*\")*$)");
                Musica ms = new Musica();

                ID += 1;

                ms.setID(ID);
                ms.setNome(parseTexto(aux[0]));
                ms.setData(df.parse(aux[1]));
                ms.setURL(aux[3]);
                ms.setCantores(RetornaLista(aux[2]));

                byte[] aux2 = ms.DevolveBytes();
                size += aux2.length-Integer.SIZE;

                rd.writeInt(aux2.length);
                rd.writeBoolean(false);
                rd.write(aux2);
                
            }
            rd.seek(0);
            rd.writeInt(this.ID);
            status = true;
        } catch (Exception e){
            status = false;
            e.printStackTrace();
        }

        return status;
    }

    public boolean LoadMAT (String path){
        try {
            RandomAccessFile test = new RandomAccessFile("./FILES/"+path+".mat", "r");
            this.Path = "./FILES/"+path+".mat";
            this.ID = test.readInt();
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean CreateMAT (String path){
        if(!LoadMAT(path)){
            try {
                RandomAccessFile create = new RandomAccessFile("./FILES/"+path+".mat", "rw");
                this.Path = "./FILES/"+path+".mat";
                return true;
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }
        } else {
            return false;
        }
    }

    public Musica getNextMusica(RandomAccessFile r) throws IOException, EOFException{

            long PointerPos = r.getFilePointer();

            if(PointerPos == 0){
                PointerPos += 4;
                r.seek(PointerPos);
            }

            while (true){
                int Pos = r.readInt();
                if(!r.readBoolean()){
                    byte[] data = new byte[Pos];
                    r.read(data);
                    Musica ms = new Musica();
                    ms.CarregaBytes(data);
                    return ms;
                }
                PointerPos += Pos + 5;
                r.seek(PointerPos);
            }
    }

    public void AdicionarMusicaFileInst(Musica ms, RandomAccessFile r){
        
        try{
            r.seek(r.length());

            if(r.getFilePointer() == 0){
                r.writeInt(0);
            }

            byte[] stream = ms.DevolveBytes();

            r.writeInt(stream.length);
            r.writeBoolean(false);
            r.write(stream);

        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public int AdicionaMusicaFile(Musica ms, boolean opc){

        try{

            RandomAccessFile rs = new RandomAccessFile(this.Path,"rw");
            rs.seek(rs.length());

            if(this.ID == -1){
                rs.writeInt(0);
            }

            if(!opc){
                this.ID += 1;
                ms.setID(this.ID);
            }

            byte[] stream = ms.DevolveBytes();

            rs.writeInt(stream.length);
            rs.writeBoolean(false);
            rs.write(stream);

            rs.seek(0);
            rs.writeInt(this.ID);
            
        } catch (Exception e){
            e.printStackTrace();
        }

        return this.ID;
    }

    public Musica getMusicaByID(int ID){
        Musica ms;
        long PointerPos;
        try {
            RandomAccessFile rd = new RandomAccessFile(this.Path, "r");
            PointerPos = rd.getFilePointer() + 4;
            rd.seek(PointerPos);

            while (true){
                int Pos = rd.readInt();
                if(!rd.readBoolean()){
                    int ID_busca = rd.readInt();
                    if(ID_busca == ID){
                        rd.seek(PointerPos += 5);
                        byte[] data = new byte[Pos];
                        rd.read(data);
                        ms = new Musica();
                        ms.CarregaBytes(data);
                        break;
                    }
                }
                PointerPos += Pos + 5;
                rd.seek(PointerPos);
            }

        } catch (Exception e){
            e.printStackTrace();
            return null;
        }
        return ms;
    }

    public boolean getPonteiroMusicaFile(int id, RandomAccessFile rd){
        
        try{
            rd.seek(0);
            long PointerPos = rd.getFilePointer() + 4;
            rd.seek(PointerPos);

                while (true){
                    int Pos = rd.readInt();
                    if(!rd.readBoolean()){
                        int ID_busca = rd.readInt();
                        if(ID_busca == id){
                            rd.seek(PointerPos);
                            return true;
                        }
                    }
                    PointerPos += Pos + 5;
                    rd.seek(PointerPos);
                }
        } catch (Exception e){
            return false;
        }
    }

    public boolean updateMusica (Musica ms, int id){

        Musica musica_old = this.getMusicaByID(id);

        if(musica_old != null){

            try {

                RandomAccessFile rd = new RandomAccessFile(this.Path, "rw");

                int tamanho_old;
                ms.setID(id);
                byte[] raw_data = ms.DevolveBytes();

                if (this.getPonteiroMusicaFile(id, rd)) {
                    tamanho_old = rd.readInt();
                } else {
                    return false;
                }

                if (tamanho_old >= raw_data.length) {
                    rd.readBoolean();                       
                    rd.write(raw_data);

                } else {

                    this.DeletaMusicaById(id);
                    this.AdicionaMusicaFile(ms, true);

                }

                rd.close();
                return true;


            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }

        } else {
            return false;
        }

    }

    public boolean DeletaMusicaById (int id){
        long PointerPos;
        try {
            RandomAccessFile rd = new RandomAccessFile(this.Path, "rw");
            PointerPos = rd.getFilePointer() + 4;
            rd.seek(PointerPos);

            while (true){
                
                int Pos = rd.readInt();
                if(!rd.readBoolean()){
                    int ID_busca = rd.readInt();
                    if(ID_busca == id){
                        rd.seek(PointerPos += 4);
                        rd.writeBoolean(true);
                        break;
                    }
                }
                PointerPos += Pos + 5;
                rd.seek(PointerPos);
            }
            return true;

        } catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

}
