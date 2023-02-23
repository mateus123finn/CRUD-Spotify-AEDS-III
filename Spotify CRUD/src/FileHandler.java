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
            File raw = new File(path);

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

                System.out.println("Carregando: "+((size * 1.0) / size_raw) * 100 + " %");

                
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
            RandomAccessFile test = new RandomAccessFile(path, "r");
            this.Path = path;
            this.ID = test.readInt();
            return true;
        } catch (Exception e) {
            //create.close();
            return false;
        }
    }

    public boolean CreateMAT (String path){
        if(!LoadMAT(path)){
            try {
                RandomAccessFile create = new RandomAccessFile(path, "rw");
                this.Path = path;
                return true;
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }
        } else {
            return false;
        }
    }

    public int AdicionaMusicaFile(Musica ms){

        try{

            RandomAccessFile rs = new RandomAccessFile(this.Path,"rw");
            rs.seek(rs.length());

            if(this.ID == -1){
                rs.writeInt(0);
            }

            this.ID += 1;
            ms.setID(this.ID);

            byte[] stream = ms.DevolveBytes();

            //System.out.println(stream.length);

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
                //System.out.println(PointerPos);
                int Pos = rd.readInt();
                //System.out.println(Pos);
                if(!rd.readBoolean()){
                    int ID_busca = rd.readInt();
                    //System.out.println(ID_busca);
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

    public boolean DeletaMusicaById (int id){
        long PointerPos;
        try {
            RandomAccessFile rd = new RandomAccessFile(this.Path, "rw");
            PointerPos = rd.getFilePointer() + 4;
            rd.seek(PointerPos);

            while (true){
                //System.out.println(PointerPos);
                
                int Pos = rd.readInt();
                //System.out.println(Pos);
                //System.out.println(PointerPos);
                if(!rd.readBoolean()){
                    int ID_busca = rd.readInt();
                    //System.out.println(ID_busca);
                    if(ID_busca == id){
                        //System.out.println(PointerPos);
                        rd.seek(PointerPos += 4);
                        rd.writeBoolean(true);
                        break;
                    }
                }
                PointerPos += Pos + 5;
                rd.seek(PointerPos);
            }
            
            // ERRO - Sobreescreve o último elemento do arquivo
            //rd.seek(0);
            //rd.writeInt(this.ID);
            return true;

        } catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

}
