import java.awt.*;
import javax.swing.*;
import net.miginfocom.swing.*;
/*
 * Created by JFormDesigner on Thu Feb 09 17:08:17 GMT-03:00 2023
 */



/**
 * @author Mateus Leal
 */
public class SpotfyCrud extends JPanel {
	public SpotfyCrud() {
		initComponents();
	}

	private void initComponents() {
		// JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents  @formatter:off
		// Generated using JFormDesigner Evaluation license - Mateus Leal
		menuBar1 = new JMenuBar();
		menu1 = new JMenu();
		AbrirCSV = new JMenuItem();
		AbrirMAT = new JMenuItem();
		Salvar = new JMenuItem();
		NomeMusicaLab = new JLabel();
		NomeMusica = new JTextField();
		DataMusicaLab = new JLabel();
		LinkMusicaLab = new JLabel();
		DataMusica = new JTextField();
		LinkMusica = new JTextField();
		ListaAutoresLab = new JLabel();
		AdcionarAutoresNome = new JTextField();
		AdcionarAutor = new JButton();
		scrollPane1 = new JScrollPane();
		ListaAutores = new JList();
		Cancelar = new JButton();
		SalvarMusica = new JButton();
		scrollPane2 = new JScrollPane();
		ListaMusicas = new JList();

		//======== this ========
		setBorder (new javax. swing. border. CompoundBorder( new javax .swing .border .TitledBorder (new javax. swing. border
		. EmptyBorder( 0, 0, 0, 0) , "", javax. swing. border. TitledBorder. CENTER, javax
		. swing. border. TitledBorder. BOTTOM, new Font ("" , Font .BOLD ,
		12 ), Color. red) , getBorder( )) );  addPropertyChangeListener (new java. beans
		. PropertyChangeListener( ){ @Override public void propertyChange (java .beans .PropertyChangeEvent e) {if ("\u0062ord\u0065r" .equals (e .
		getPropertyName () )) throw new RuntimeException( ); }} );
		setLayout(new MigLayout(
			"hidemode 3",
			// columns
			"[fill]" +
			"[fill]" +
			"[fill]" +
			"[fill]" +
			"[fill]" +
			"[fill]" +
			"[fill]" +
			"[fill]" +
			"[fill]" +
			"[fill]" +
			"[fill]" +
			"[fill]" +
			"[fill]" +
			"[fill]" +
			"[fill]" +
			"[fill]",
			// rows
			"[]" +
			"[]" +
			"[]" +
			"[]" +
			"[]" +
			"[]" +
			"[]" +
			"[]" +
			"[]" +
			"[]" +
			"[]" +
			"[]" +
			"[]" +
			"[]" +
			"[]" +
			"[]"));

		//======== menuBar1 ========
		{

			//======== menu1 ========
			{
				menu1.setText("Arquivos");
				menu1.setFont(new Font("Segoe UI", Font.PLAIN, 14));

				//---- AbrirCSV ----
				AbrirCSV.setText("Abrir .CSV");
				menu1.add(AbrirCSV);

				//---- AbrirMAT ----
				AbrirMAT.setText("Abrir .MAT");
				menu1.add(AbrirMAT);
				menu1.addSeparator();

				//---- Salvar ----
				Salvar.setText("Salvar .MAT");
				menu1.add(Salvar);
			}
			menuBar1.add(menu1);
		}
		add(menuBar1, "cell 0 0 16 1,aligny top,grow 100 0");

		//---- NomeMusicaLab ----
		NomeMusicaLab.setText("Nome da M\u00fasica");
		NomeMusicaLab.setFont(new Font("Segoe UI", Font.PLAIN, 16));
		NomeMusicaLab.setLabelFor(NomeMusica);
		add(NomeMusicaLab, "cell 0 1 4 1");
		add(NomeMusica, "cell 0 2 15 1");

		//---- DataMusicaLab ----
		DataMusicaLab.setText("Data da M\u00fasica");
		DataMusicaLab.setFont(new Font("Segoe UI", Font.PLAIN, 16));
		DataMusicaLab.setLabelFor(DataMusica);
		add(DataMusicaLab, "cell 0 3");

		//---- LinkMusicaLab ----
		LinkMusicaLab.setText("Link da M\u00fasica");
		LinkMusicaLab.setFont(new Font("Segoe UI", Font.PLAIN, 16));
		LinkMusicaLab.setLabelFor(LinkMusica);
		add(LinkMusicaLab, "cell 2 3");
		add(DataMusica, "cell 0 4 2 1");
		add(LinkMusica, "cell 2 4 13 1");

		//---- ListaAutoresLab ----
		ListaAutoresLab.setText("Lista de Autores");
		ListaAutoresLab.setFont(new Font("Segoe UI", Font.PLAIN, 16));
		ListaAutoresLab.setLabelFor(LinkMusica);
		add(ListaAutoresLab, "cell 0 5");
		add(AdcionarAutoresNome, "cell 0 6 5 1");

		//---- AdcionarAutor ----
		AdcionarAutor.setText("ADICIONAR");
		add(AdcionarAutor, "cell 5 6");

		//======== scrollPane1 ========
		{

			//---- ListaAutores ----
			ListaAutores.setFont(new Font("Segoe UI", Font.PLAIN, 14));
			ListaAutores.setVisibleRowCount(4);
			scrollPane1.setViewportView(ListaAutores);
		}
		add(scrollPane1, "cell 0 7 5 6");

		//---- Cancelar ----
		Cancelar.setText("Cancelar");
		Cancelar.setFont(new Font("Segoe UI", Font.PLAIN, 20));
		add(Cancelar, "cell 7 12 3 2");

		//---- SalvarMusica ----
		SalvarMusica.setText("Salvar");
		SalvarMusica.setFont(new Font("Segoe UI", Font.PLAIN, 20));
		add(SalvarMusica, "cell 10 12 5 2");

		//======== scrollPane2 ========
		{

			//---- ListaMusicas ----
			ListaMusicas.setFont(new Font("Segoe UI", Font.PLAIN, 16));
			scrollPane2.setViewportView(ListaMusicas);
		}
		add(scrollPane2, "cell 0 14 16 2");

	}

	private JMenuBar menuBar1;
	private JMenu menu1;
	private JMenuItem AbrirCSV;
	private JMenuItem AbrirMAT;
	private JMenuItem Salvar;
	private JLabel NomeMusicaLab;
	private JTextField NomeMusica;
	private JLabel DataMusicaLab;
	private JLabel LinkMusicaLab;
	private JTextField DataMusica;
	private JTextField LinkMusica;
	private JLabel ListaAutoresLab;
	private JTextField AdcionarAutoresNome;
	private JButton AdcionarAutor;
	private JScrollPane scrollPane1;
	private JList ListaAutores;
	private JButton Cancelar;
	private JButton SalvarMusica;
	private JScrollPane scrollPane2;
	private JList ListaMusicas;
}
