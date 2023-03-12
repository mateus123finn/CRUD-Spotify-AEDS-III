import java.nio.file.Files;
import java.nio.file.Paths;
import java.io.RandomAccessFile;

public class OrdenacaoHandler{

    private String arquivo;
    private FileHandler fl;
    private int MaiorN;

    public OrdenacaoHandler(){
        fl = new FileHandler();
    }

    public void setPath(String path){
        this.arquivo = path;
    }

    private void quicksort(Musica[] vetor){
        quicksort(0, vetor.length - 1, vetor);
    }

    private void quicksort(int esq, int dir, Musica[] vetor) {
        int i = esq, j = dir;
        int pivo = vetor[(dir+esq)/2].getID();
        while (i <= j) {
            while (vetor[i].getID() < pivo) i++;
            while (vetor[j].getID() > pivo) j--;
            if (i <= j) {
                Musica aux = vetor[i];
                vetor[i] = vetor[j];
                vetor[j] = aux;
                i++;
                j--;
            }
        }
        if (esq < j)  quicksort(esq, j, vetor);
        if (i < dir)  quicksort(i, dir, vetor);
    }


    //Chamada da Função para a Ordenação Balanceada
    public void Arrumar(int TamVetor, int QntArq){

        int arquivoFinal = -1;

        try {

            for (int i = 0; i < QntArq; i++) {
                RandomAccessFile r = new RandomAccessFile("./FILES/Auxiliar "+i,"rw");
                r.close();
            }

            RandomAccessFile aux = new RandomAccessFile(this.arquivo, "r");
            this.MaiorN = aux.readInt();
            aux.close();
        } catch (Exception e) {
            // TODO: handle exception
        }

        RandomAccessFile[] files = new RandomAccessFile[QntArq*2];
        PrimeiraParte(TamVetor, files);
        arquivoFinal = SegundaParte(files, TamVetor);
        ApagarResto(files, arquivoFinal-1);
        

    }
    //Chamada da Função para a Seleção com Substituição
    public void ArrumarSubs(int TamVetor, int QntArq){

        int arquivoFinal = -1;

        try {

            for (int i = 0; i < QntArq; i++) {
                RandomAccessFile r = new RandomAccessFile("./FILES/Auxiliar "+i,"rw");
                r.close();
            }

            RandomAccessFile aux = new RandomAccessFile(this.arquivo, "r");
            this.MaiorN = aux.readInt();
            aux.close();
        } catch (Exception e) {
            // TODO: handle exception
        }

        RandomAccessFile[] files = new RandomAccessFile[QntArq*2];
        PrimeiraParte(TamVetor, files);
        arquivoFinal = SegundaParteVari(files, TamVetor);
        ApagarResto(files, arquivoFinal-1);
        

    }
    //Chamada da Função para a Ordenação Balanceada com blocos de Tamanho Variável
    public void arrumarVari(int TamVetor, int QntArq){
        int arquivoFinal = -1;

        try {

            for (int i = 0; i < QntArq; i++) {
                RandomAccessFile r = new RandomAccessFile("./FILES/Auxiliar "+i,"rw");
                r.close();
            }

            RandomAccessFile aux = new RandomAccessFile(this.arquivo, "r");
            this.MaiorN = aux.readInt();
            aux.close();
        } catch (Exception e) {
            // TODO: handle exception
        }

        RandomAccessFile[] files = new RandomAccessFile[QntArq*2];
        PrimeiraParte(TamVetor, files);
        arquivoFinal = SegundaParteVari(files, TamVetor);
        ApagarResto(files, arquivoFinal-1);
    }

    //Recebe uma lista de RandomAccessFiles e um alvo, desliga suas instâncias e apaga seus arquivos, deixando somente o arquivo de alvo.
    private void ApagarResto(RandomAccessFile[] files, int target){

        try {

            files[target].seek(0);
            files[target].writeInt(this.MaiorN);

            for (int i = 0; i < files.length; i++) {
                files[i].close();
            }
        } catch (Exception e) {
            // TODO: handle exception
        }


        for (int i = 0; i < files.length; i++) {
            if(i != target){
                try{
                    Files.deleteIfExists(Paths.get("./FILES/Auxiliar "+i));
                } catch (Exception e){
                    e.printStackTrace();
                }
            }
        }
        try {
            Files.move(Paths.get("./FILES/Auxiliar "+target), Paths.get("./FILES/ArquivoFinal.mat"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //Recebe o Tamanho do Vetor e uma lista de RandomAccessFiles e faz a distribuição dos valores para os arquivos.
    private void PrimeiraParte (int TamVetor, RandomAccessFile[] files){

        int PontArq = 0;
        Musica[] Vetor;
        RandomAccessFile Arquivo_raw;
        Musica[] aux;

        try{
            Arquivo_raw = new RandomAccessFile(arquivo, "r");       
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }
        while (true){
            int i = 0;
            Vetor = new Musica[TamVetor];
            try{
                for (i = 0; i < TamVetor; i++) {
                    Vetor[i] = fl.getNextMusica(Arquivo_raw);
                }
            } catch (Exception e){

                if(i == 0){
                    return;
                } else {
                    aux = new Musica[i];
                    for (int j = 0; j < aux.length; j++) {
                        aux[j] = Vetor[j];
                    }
                    Vetor = aux;
                }


            }

            quicksort(Vetor);

            try {
                files[PontArq] = new RandomAccessFile("./FILES/Auxiliar "+PontArq, "rw");
                files[PontArq].seek(files[PontArq].length());
            } catch (Exception e) {
                e.printStackTrace();
            }

            for (int j = 0; j < Vetor.length; j++) {
                try {
                    fl.AdicionarMusicaFileInst(Vetor[j], files[PontArq]);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            try {
                files[PontArq].close();
            } catch (Exception e) {
                e.printStackTrace();
                return;
            }

            PontArq = (++PontArq)%(files.length/2);

        }

    }

    //Recebe um array de Música e Retorna o índice de seu menor elemento

    private int getMenor(Musica[] Vetor){

        int Nmenor = this.MaiorN+1;
        int PosMenor = -1;


        for (int i = 0; i < Vetor.length; i++) {
            if(Vetor[i] == null){
                continue;
            }
            if(Vetor[i].getID() < Nmenor){
                Nmenor = Vetor[i].getID();
                PosMenor = i;
            }
        }

        return PosMenor;
    }

    //Recebe um vetor de Música eo inicializa com NULL.
    private void inicializaVetor (Musica[] vetor){
        for (int i = 0; i < vetor.length; i++) {
            vetor[i] = null;
        }
    }
    //Recebe o Tamanho do Vetor e uma lista de RandomAccessFiles e faz a redistribuição dos valores para os arquivos, com tamanhos variádos para o blocos.
    private int SegundaParteVari (RandomAccessFile[] files, int TamVetor){
        int QntArq = (files.length/2);
        int NmrArq = 0;

        for (int i = 0; true; i = (i+1)%2) {

            

            for (int j = 0; j < QntArq; j++) {
                try{files[j] = new RandomAccessFile("./FILES/Auxiliar "+(((QntArq*((2-i)))%files.length)+j), "r");}
                catch (Exception e){
                    e.printStackTrace();
                }
            }

            try{
                for (int j = 0; j < QntArq; j++) {
                    NmrArq = (((QntArq*(1+i))%files.length)+j);
                    files [QntArq+j] = new RandomAccessFile("./FILES/Auxiliar "+NmrArq, "rw");
                    files[QntArq+j].setLength(0);
                }
            }
            catch (Exception e){e.printStackTrace();}

            int ArqUsados = 0;

            for (int k = 0; true; k = (k+1)%QntArq){

                Musica[] vetor = new Musica[QntArq];
                inicializaVetor(vetor);
                int FlagEOF = -1;

                int[] FimBloco = new int[QntArq];
                
                for (int j = 0; j < QntArq; j++) {
                    long OldPos = 0;
                    try {
                       OldPos = files[j].getFilePointer();
                    } catch (Exception e) {
                        e.getStackTrace();
                    }
                    int Valor = -1;
                    while (true){
                        try {
                            int ValorArq = fl.getNextMusica(files[j]).getID();
                            if(ValorArq >= Valor){
                                Valor = ValorArq;
                                FimBloco[j]++;
                            } else {
                                break;
                            }
                        } catch (Exception e) {
                            break;
                        }

                    }

                    try {
                        files[j].seek(OldPos);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                try {
                    for (int j = 0; j < QntArq; j++) {
                        FlagEOF = j;
                        vetor[j] = fl.getNextMusica(files[j]);
                    }
                } catch (Exception e) {
                    if(ArqUsados <= 1 && FlagEOF == 0){
                        return QntArq*(1-i)+k;
                    }
                    if(FlagEOF == 0){
                        break;
                    }
                }

                int[] ContaPos = new int[QntArq];

                while (true){

                    int pos = getMenor(vetor);

                    try {

                        if(pos == -1){
                            break;
                        }

                        fl.AdicionarMusicaFileInst(vetor[pos], files[QntArq+k]);
                        ContaPos[pos]++;

                        if(ContaPos[pos] >= FimBloco[pos]){

                            vetor[pos] = null;

                        } else {

                            vetor[pos] = fl.getNextMusica(files[pos]);

                        }
                        
                    } catch (Exception e) {

                        vetor[pos] = null;

                    }

                }

                ArqUsados ++;

            }

            try {
                for (int index = 0; index < files.length; index++) {
                    files[index].close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }
    
//Recebe o Tamanho do Vetor e uma lista de RandomAccessFiles e faz a redistribuição dos valores para os arquivos, com tamanhos "fixos" para o blocos.
    private int SegundaParte(RandomAccessFile[] files, int TamVetor){

        int QntArq = (files.length/2);
        int Estagio = TamVetor*QntArq;
        int NmrArq = 0;

        for (int i = 0; true; i = (i+1)%2) {

            

            for (int j = 0; j < QntArq; j++) {
                
                try{files[j] = new RandomAccessFile("./FILES/Auxiliar "+(((QntArq*((2-i)))%files.length)+j), "r");}
                catch (Exception e){
                    e.printStackTrace();
                }
            }

            try{
                for (int j = 0; j < QntArq; j++) {
                    NmrArq = (((QntArq*(1+i))%files.length)+j);
                    files [QntArq+j] = new RandomAccessFile("./FILES/Auxiliar "+NmrArq, "rw");
                    files[QntArq+j].setLength(0);
                }
            }
            catch (Exception e){e.printStackTrace();}

            int ArqUsados = 0;

            for (int k = 0; true; k = (k+1)%QntArq){

                Musica[] vetor = new Musica[QntArq];
                inicializaVetor(vetor);
                int FlagEOF = -1;

                try {
                    for (int j = 0; j < QntArq; j++) {
                        FlagEOF = j;
                        vetor[j] = fl.getNextMusica(files[j]);
                    }
                } catch (Exception e) {
                    if(ArqUsados <= 1 && FlagEOF == 0){
                        return QntArq*(1-i)+k;
                    }
                    if(FlagEOF == 0){
                        break;
                    }
                }

                int[] ContaPos = new int[QntArq];

                while (true){

                    int pos = getMenor(vetor);

                    try {

                        if(pos == -1){
                            break;
                        }

                        fl.AdicionarMusicaFileInst(vetor[pos], files[QntArq+k]);
                        ContaPos[pos]++;

                        if(ContaPos[pos] >= Estagio/QntArq){

                            vetor[pos] = null;

                        } else {
                            vetor[pos] = fl.getNextMusica(files[pos]);
                        }
                        
                    } catch (Exception e) {

                        vetor[pos] = null;
                    
                    }

                }

                ArqUsados ++;

            }

            try {
                for (int index = 0; index < files.length; index++) {
                    files[index].close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            Estagio = Estagio * QntArq;
        }

    }


}
