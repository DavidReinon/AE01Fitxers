package AE01;

import java.awt.EventQueue;

import javax.swing.table.DefaultTableModel;
import javax.swing.ButtonGroup;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTextArea;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;
import java.awt.event.ActionEvent;
import java.awt.TextField;
import java.awt.Button;
import javax.swing.JRadioButton;
import javax.swing.JTable;
import java.awt.Font;
import java.awt.Color;
import javax.swing.ListSelectionModel;
import javax.swing.JButton;

/**
 * Esta clase gestiona tota la interfaz grafica
 * 
 * @author David Reinon Garcia
 */
public class FrmPrincipal extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTable tblInfo;
	private DefaultTableModel tableModel;
	private JRadioButton rdbtnNom;
	private JRadioButton rdbtnGrandaria;
	private JRadioButton rdbtnDataModificacio;
	GestioFitxers gestioFitxers = new GestioFitxers();

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					FrmPrincipal frame = new FrmPrincipal();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public FrmPrincipal() {

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 900, 640);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		tableModel = new DefaultTableModel();
		tableModel.addColumn("NOM");
		tableModel.addColumn("EXTENSIO");
		tableModel.addColumn("GRANDARIA");
		tableModel.addColumn("ULTIMA MODIFICACIO");

		tblInfo = new JTable(tableModel);
		tblInfo.setColumnSelectionAllowed(true);
		tblInfo.setCellSelectionEnabled(true);
		tblInfo.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
		tblInfo.setBounds(328, 16, 530, 396);
		contentPane.add(tblInfo);

		JTextArea txtaDirectori = new JTextArea();
		txtaDirectori.setEditable(false);
		txtaDirectori.setBounds(10, 11, 96, 27);
		txtaDirectori.setText("Directori:");
		contentPane.add(txtaDirectori);

		TextField txtfRutaDirectori = new TextField();
		txtfRutaDirectori.setBounds(10, 44, 197, 27);
		contentPane.add(txtfRutaDirectori);

		Button btnSeleccionar = new Button("Seleccionar");
		btnSeleccionar.setBounds(137, 11, 70, 22);
		contentPane.add(btnSeleccionar);

		JPanel pnlOrdre1 = new JPanel();
		pnlOrdre1.setBounds(10, 123, 130, 115);
		contentPane.add(pnlOrdre1);
		pnlOrdre1.setLayout(null);

		JRadioButton rdbtnAscendent = new JRadioButton("Ascendent");
		rdbtnAscendent.setBounds(15, 27, 109, 23);
		pnlOrdre1.add(rdbtnAscendent);
		rdbtnAscendent.setSelected(true);

		JRadioButton rdbtnDescendent = new JRadioButton("Descendent");
		rdbtnDescendent.setBounds(15, 53, 109, 23);
		pnlOrdre1.add(rdbtnDescendent);

		ButtonGroup grupBoto1 = new ButtonGroup();
		grupBoto1.add(rdbtnAscendent);
		grupBoto1.add(rdbtnDescendent);

		JTextArea txtaOrdrenarPer = new JTextArea();
		txtaOrdrenarPer.setEditable(false);
		txtaOrdrenarPer.setBounds(85, 100, 117, 22);
		txtaOrdrenarPer.setText("Ordrenar per:");
		contentPane.add(txtaOrdrenarPer);

		JPanel pnlOrdre2 = new JPanel();
		pnlOrdre2.setBounds(150, 123, 142, 115);
		contentPane.add(pnlOrdre2);
		pnlOrdre2.setLayout(null);

		rdbtnGrandaria = new JRadioButton("Grandaria");
		rdbtnGrandaria.setBounds(4, 41, 109, 23);
		pnlOrdre2.add(rdbtnGrandaria);

		rdbtnNom = new JRadioButton("Nom");
		rdbtnNom.setBounds(4, 15, 109, 23);
		pnlOrdre2.add(rdbtnNom);
		rdbtnNom.setSelected(true);

		rdbtnDataModificacio = new JRadioButton("Data Modificacio");
		rdbtnDataModificacio.setBounds(4, 67, 132, 23);
		pnlOrdre2.add(rdbtnDataModificacio);

		ButtonGroup grupBoto2 = new ButtonGroup();
		grupBoto2.add(rdbtnGrandaria);
		grupBoto2.add(rdbtnNom);
		grupBoto2.add(rdbtnDataModificacio);

		JTextArea txtaTitleCoincidencies = new JTextArea();
		txtaTitleCoincidencies.setEditable(false);
		txtaTitleCoincidencies.setBounds(90, 279, 117, 22);
		txtaTitleCoincidencies.setText("Coincidencies:");
		contentPane.add(txtaTitleCoincidencies);

		TextField txtfCoincidencies = new TextField();
		txtfCoincidencies.setBounds(10, 309, 197, 27);
		txtfCoincidencies.setText("String(ejemplo)");
		contentPane.add(txtfCoincidencies);

		JTextArea textaMostrarCoincidencies = new JTextArea();
		textaMostrarCoincidencies.setBounds(10, 342, 282, 180);
		textaMostrarCoincidencies.setEditable(false);
		contentPane.add(textaMostrarCoincidencies);

		JTextArea txtaFusionarFitxers = new JTextArea();
		txtaFusionarFitxers.setEditable(false);
		txtaFusionarFitxers.setText("Fusionar Fitxers:");
		txtaFusionarFitxers.setBounds(519, 444, 181, 27);
		contentPane.add(txtaFusionarFitxers);

		JTextArea txtaInformacionFusionar = new JTextArea();
		txtaInformacionFusionar.setEditable(false);
		txtaInformacionFusionar.setBackground(Color.WHITE);
		txtaInformacionFusionar.setFont(new Font("Microsoft PhagsPa", Font.PLAIN, 14));
		txtaInformacionFusionar
				.setText("Selecciona els arxius de la taula que vulgues fusionar i pitja el botó de confirmació.");
		txtaInformacionFusionar.setBounds(362, 482, 512, 27);
		contentPane.add(txtaInformacionFusionar);

		JButton btnAceptar = new JButton("Aceptar");
		btnAceptar.setBounds(211, 44, 81, 27);
		contentPane.add(btnAceptar);

		JButton btnBuscar = new JButton("Buscar");
		btnBuscar.setBounds(213, 309, 81, 27);
		contentPane.add(btnBuscar);

		JTextArea txtaTituloNomFusio = new JTextArea();
		txtaTituloNomFusio.setText("Nom del fitxer :");
		txtaTituloNomFusio.setEditable(false);
		txtaTituloNomFusio.setBounds(338, 528, 142, 27);
		contentPane.add(txtaTituloNomFusio);

		TextField txtfNomFusio = new TextField();
		txtfNomFusio.setBounds(486, 528, 197, 27);
		contentPane.add(txtfNomFusio);

		JButton btnConfirmarFusio = new JButton("Confirmar");
		btnConfirmarFusio.setBounds(710, 524, 96, 32);
		contentPane.add(btnConfirmarFusio);

		btnConfirmarFusio.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ArrayList<FileInformation> fitxersSeleccionats = obtenirfitxersSeleccionats();

				if (fitxersSeleccionats.isEmpty() || fitxersSeleccionats.size() == 1) {
					JOptionPane.showConfirmDialog(null, "Has seleccionat un o cap fitxer.", "Error",
							JOptionPane.OK_OPTION);
					return;
				}

				String nomfitxerNou = txtfNomFusio.getText().trim();
				if (nomfitxerNou.isEmpty()) {
					JOptionPane.showConfirmDialog(null, "El nom del nou fitxer no pot estar en blanc.", "Error",
							JOptionPane.OK_OPTION);
					return;
				}
				nomfitxerNou += ".txt";
				if (gestioFitxers.fitxerExistent(nomfitxerNou)) {
					int opcio = JOptionPane.showConfirmDialog(null,
							"Ya existeix un fitxer amb el mateix nom\n Desitja sobreescribiro ?.", "Advertencia",
							JOptionPane.YES_NO_OPTION);
					if (opcio == JOptionPane.NO_OPTION) {
						return;
					}
				}

				boolean fusioExitosa = gestioFitxers.fusionarfitxers(fitxersSeleccionats, nomfitxerNou);
				if (fusioExitosa) {
					JOptionPane.showConfirmDialog(null, "Fusio realitzada amb exit.", "Correcte",
							JOptionPane.OK_OPTION);

					gestioFitxers.OmplirLlistaPerOrdre("nom", true);
					carregarfitxersTxtEnTaula();

					rdbtnAscendent.setSelected(true);
					rdbtnNom.setSelected(true);
					return;
				}
				JOptionPane.showConfirmDialog(null, "No se ha pogut realitzar la fusio.", "Error",
						JOptionPane.OK_OPTION);
				return;
			}

		});

		btnBuscar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String textoBuscado = txtfCoincidencies.getText();
				ArrayList<String> resultados = gestioFitxers.BuscarCoincidencias(textoBuscado);

				StringBuilder sb = new StringBuilder();
				for (String resultado : resultados) {
					sb.append(resultado);
					sb.append('\n');
				}
				textaMostrarCoincidencies.setText(sb.toString());
			}
		});

		btnAceptar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String rutaString = txtfRutaDirectori.getText();
				File directoriSeleccionat = new File(rutaString);
				gestioFitxers.setDirectori(directoriSeleccionat);

				gestioFitxers.OmplirLlistaPerOrdre("nom", true);
				carregarfitxersTxtEnTaula();

				rdbtnAscendent.setSelected(true);
				rdbtnNom.setSelected(true);

			}
		});

		btnSeleccionar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				JFileChooser seleccionaFicher = new JFileChooser();
				seleccionaFicher.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);

				int resultat = seleccionaFicher.showOpenDialog(FrmPrincipal.this);

				if (resultat == JFileChooser.APPROVE_OPTION) {
					File directoriSeleccionat = seleccionaFicher.getSelectedFile();
					gestioFitxers.setDirectori(directoriSeleccionat);

					gestioFitxers.OmplirLlistaPerOrdre("nom", true);
					carregarfitxersTxtEnTaula();

					String rutaDirectori = directoriSeleccionat.getAbsolutePath();
					txtfRutaDirectori.setText(rutaDirectori);

					rdbtnAscendent.setSelected(true);
					rdbtnNom.setSelected(true);
				}
			}
		});

		rdbtnNom.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (rdbtnNom.isSelected()) {
					String ordenacio = getOrdreSeleccionat();
					boolean ascendente = rdbtnAscendent.isSelected();
					gestioFitxers.OmplirLlistaPerOrdre(ordenacio, ascendente);
					carregarfitxersTxtEnTaula();
				}
			}
		});

		rdbtnGrandaria.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (rdbtnGrandaria.isSelected()) {
					String ordenacio = getOrdreSeleccionat();
					boolean ascendente = rdbtnAscendent.isSelected();
					gestioFitxers.OmplirLlistaPerOrdre(ordenacio, ascendente);
					carregarfitxersTxtEnTaula();
				}
			}
		});

		rdbtnDataModificacio.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (rdbtnDataModificacio.isSelected()) {
					String ordenacio = getOrdreSeleccionat();
					boolean ascendente = rdbtnAscendent.isSelected();
					gestioFitxers.OmplirLlistaPerOrdre(ordenacio, ascendente);
					carregarfitxersTxtEnTaula();
				}
			}
		});

		rdbtnAscendent.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String ordenacio = getOrdreSeleccionat();
				boolean ascendente = rdbtnAscendent.isSelected();
				gestioFitxers.OmplirLlistaPerOrdre(ordenacio, ascendente);
				carregarfitxersTxtEnTaula();
			}
		});

		rdbtnDescendent.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String ordenacio = getOrdreSeleccionat();
				boolean ascendente = rdbtnAscendent.isSelected();
				gestioFitxers.OmplirLlistaPerOrdre(ordenacio, ascendente);
				carregarfitxersTxtEnTaula();
			}
		});

	}

	/**
	 * Carrega el fitxers amb les dades en la taula
	 */
	private void carregarfitxersTxtEnTaula() {
		tableModel.setRowCount(0);
		tableModel.addRow(new Object[] { "NOM", "EXTENSIO", "GRANDARIA", "ULTIMA MODIFICACIO" });

		for (FileInformation fitxer : gestioFitxers.getInformaciofitxers()) {
			tableModel.addRow(new Object[] { fitxer.getNom(), fitxer.getExtensio(), fitxer.getGrandaria(),
					fitxer.getDataUltimaModificacio() });
		}
	}

	/**
	 * Guarda els fitxers seleccionants de la taula
	 * 
	 * @return fitxers seleccionats
	 */
	private ArrayList<FileInformation> obtenirfitxersSeleccionats() {
		ArrayList<FileInformation> arxiusSeleccionats = new ArrayList<>();
		int[] filesSeleccionades = tblInfo.getSelectedRows();
		for (int numeroFila : filesSeleccionades) {
			arxiusSeleccionats.add(gestioFitxers.getInformaciofitxers().get(numeroFila - 1)); // -1 Ya que la primera
																								// row es el header
		}
		return arxiusSeleccionats;
	}

	/**
	 * Comrpueba que radiobuton esta seleccionat per la ordenacio
	 * 
	 * @return string
	 */
	private String getOrdreSeleccionat() {
		if (rdbtnNom.isSelected()) {
			return "nom";
		}
		if (rdbtnGrandaria.isSelected()) {
			return "grandaria";
		}
		if (rdbtnDataModificacio.isSelected()) {
			return "data";
		}
		return "";
	}
}
