import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.Scanner;
import java.util.Vector;

public class Interface {

    private Scanner sc;
    private FileHandler f;
    private OrdenacaoHandler o;
    
    
    public Interface() {
        this.sc = new Scanner(System.in);
        this.f = new FileHandler();
        this.o = new OrdenacaoHandler();

    }



    public void Menu(){
        System.out.println("Bem Vindo ao Spotify Music DataBase !!!");
        System.out.println();
        this.MenuCarregar();
    }

    public void MenuCarregar(){

        while (true){
            System.out.println("Escolha uma OPCAO:");
            System.out.println("1 - Criar um novo Arquivo MAT");
            System.out.println("2 - Carregar um Arquivo MAT");
            System.out.println("3 - Carregar um novo Arquivo CSV");
            System.out.println("4 - Sair");
            int opc = Integer.parseInt(this.sc.nextLine());

            if(opc == 1){
                System.out.println("Digite o nome do Arquivo MAT a ser criado: ");
                f.CreateMAT(sc.nextLine());
            } else if(opc == 2){
                System.out.println("Digite o nome do Arquivo MAT a ser Carregado: ");
                f.LoadMAT(sc.nextLine());
            }else if(opc == 3){
                System.out.println("Digite o nome do Arquivo CSV a ser Carregado: ");
                f.LoadCSV(sc.nextLine());
            } else {
                return;
            }

            o.setPath(f.getPath());
            this.MenuPrincipal();

        }


    }

    public void MenuCriar(){

        String nome;
        String URL;
        Date data = null;
        Vector<String> cantores;
        //Musica ms = new Musica(nome, null, Musica, null)

        System.out.println("Escreva o Nome da Musica: ");
        nome = sc.nextLine();
        System.out.println("Escreva a data da Musica: (dd/mm/yyyy)");
        DateFormat sourceFormat = new SimpleDateFormat("dd/MM/yyyy");
        try {
            data = sourceFormat.parse(sc.nextLine());
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        System.out.println("Digite o URL da Musica: ");
        URL = sc.nextLine();
        System.out.println("Digite o nome dos Compositores da Musica: (Nome Sobrenome,Nome Sobrenome,...)");
        String[] aux = sc.nextLine().split(",");
        cantores = new Vector<>(Arrays.asList(aux));

        Musica ms = new Musica(nome, data, URL, cantores);

        int id = f.AdicionaMusicaFile(ms, false);

        System.out.println("Musica Adcionada com Sucesso no ID "+id);

    }

    public void MenuRecuperar (){
        System.out.println("Digite o ID a ser buscado: ");
        int id = Integer.parseInt(sc.nextLine());

        Musica ms = f.getMusicaByID(id);

        if (ms == null) {
            System.out.println("Musica Nao Encontrada !!!");
            return;
        }

        System.out.println();
        System.out.println("Nome: "+ms.getNome());
        System.out.println("Data: "+ms.getData().toString());
        System.out.println("URL: "+ms.getURL());

        Vector<String> aux = ms.getCantores();

        System.out.println("Compositores: ");
        for (int i = 0; i < aux.size(); i++) {
            System.out.println("\t"+aux.get(i));
        }

    }

    public void MenuEditar(){

        System.out.println("Digite o ID a ser Editado: ");
        int id = Integer.parseInt(sc.nextLine());

        Musica old = f.getMusicaByID(id);

        String nome;
        String URL;
        Date data = null;
        Vector<String> cantores;
        //Musica ms = new Musica(nome, null, Musica, null)

        System.out.println("Escreva o Nome da Musica ou deixe em branco para Continuar: ");
        nome = sc.nextLine();
        if(nome == ""){
            nome = old.getNome();
        }
        System.out.println("Escreva a data da Musica ou deixe em branco para Continuar: (dd/mm/yyyy)");
        String auxDate = sc.nextLine();
        if(auxDate != ""){
            DateFormat sourceFormat = new SimpleDateFormat("dd/MM/yyyy");
            try {
                data = sourceFormat.parse(auxDate);
            } catch (ParseException e) {
            // TODO Auto-generated catch block
                e.printStackTrace();
            }
        } else {
            data = old.getData();
        }
        System.out.println("Digite o URL da Musica ou deixe em branco para Continuar: ");
        URL = sc.nextLine();
        if(URL == ""){
            URL = old.getURL();
        }
        System.out.println("Digite o nome dos Compositores da Musica ou deixe em branco para Continuar: (Nome Sobrenome,Nome Sobrenome,...)");
        String auxCantores = sc.nextLine();
        if(auxCantores != ""){
            String[] aux = auxCantores.split(",");
            cantores = new Vector<>(Arrays.asList(aux));
        } else {
            cantores = old.getCantores();
        }

        Musica ms = new Musica(nome, data, URL, cantores);

        f.updateMusica(ms, id);

        System.out.println("Musica Editada com Sucesso !!");

    }

    public void MenuDeletar(){
        System.out.println("Digite o ID a ser Deletada: ");
        int id = Integer.parseInt(sc.nextLine());

        if (f.DeletaMusicaById(id)) {
            System.out.println("Musica Deletada com Sucesso !!");
        } else {
            System.out.println("Ocorreu um Erro !!!");
        }
    }

    public void MenuOrdenar(){
        while (true){
            System.out.println();
            System.out.println("--O arquivo Ordenado se Encontra na Pasta FILES com nome ArquivoFinal.mat--");
            System.out.println("Escolha uma OPCAO:");
            System.out.println("1 - Ordenacao Balanceada");
            System.out.println("2 - Ordenacao Balanceada com Segmento de Tamanho Variavel");
            System.out.println("3 - Intercalacao com Substituicao");
            System.out.println("4 - Voltar");
            System.out.println();

            int opc = Integer.parseInt(this.sc.nextLine());

            if(opc >= 4){
                return;
            }

            System.out.println("Digite o Tamanho do Array: ");
            int TamVetor = Integer.parseInt(this.sc.nextLine());
            System.out.println("Digite a Quantidade de Arquivos: ");
            int QntArq = Integer.parseInt(this.sc.nextLine());

            if (TamVetor < QntArq) {
                System.out.println("Tamanho Inválido !!!");
                continue;
            }

            if(opc == 1){
                o.Arrumar(TamVetor, QntArq);
            } else if(opc == 2){
                o.arrumarVari(TamVetor, QntArq);
            }else if(opc == 3){
                o.ArrumarSubs(TamVetor, QntArq);
            }
        } 
    }

    public void MenuPrincipal(){
        while (true){
            System.out.println();
            System.out.println("Escolha uma OPCAO:");
            System.out.println("1 - Criar uma Musica Nova");
            System.out.println("2 - Recuperar uma Musica");
            System.out.println("3 - Editar Informacoes de uma Musica");
            System.out.println("4 - Remover uma Musica");
            System.out.println("5 - Ordenar o Arquivo");
            System.out.println("6 - Voltar");
            System.out.println();
            int opc = Integer.parseInt(this.sc.nextLine());

            if(opc == 1){
                this.MenuCriar();
            } else if(opc == 2){
                this.MenuRecuperar();
            }else if(opc == 3){
                this.MenuEditar();
            } else if(opc == 4){
                this.MenuDeletar();;
            } else if(opc == 5){
                this.MenuOrdenar();
            } else {
                return;
            }
        }
    }

}
