package es.florida.addae01;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;
import java.awt.Font;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.text.Collator;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Collections;
import java.util.Vector;

import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.ButtonGroup;
import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListModel;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class Principal extends JFrame {

	private static JPanel contentPane;
	private static JButton btnCargarDirectorio, btnIdiomaES, btnIdiomaVAL, btnIdiomaEN, btnOrdenar, btnBuscar,
			btnFusionar;
	private static JLabel lblDirectorio, lblOrdenar;
	private static JList<String> listArchivos, listBuscar;
	private static JComboBox<String> comboBoxOrdenar;
	private static JTextField txtTextfieldBuscar;
	private static JRadioButton rdbtnAscendente, rdbtnDescendente;
	private static final ButtonGroup buttonGroup = new ButtonGroup();
	private static String directorioTrabajo, strCoincidencias, strPopupNombreMensaje, strPopupSobrescrituraMensaje;
	private static int criterioOrdenacion = 0; //Ordenacion por defecto: nombre
	private static int orden = 0;  //Ordenacion por defecto: ascendente
	String[] listaFicheros;

	/**
	 * Método para arrancar la aplicación
	 * 
	 * @param args: no se utilizan
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Principal frame = new Principal();
					configurarIdioma("ES");
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Método para configurar el idioma de la aplicación
	 * 
	 * @param idioma: idioma seleccionado
	 */
	public static void configurarIdioma(String idioma) {
		try {
			File ficheroIdioma = new File(idioma);
			FileReader fr = new FileReader(ficheroIdioma);
			BufferedReader br = new BufferedReader(fr);
			String btnCargarDirectorioStr = br.readLine().split("=")[1];
			String lblDirectorioStr = br.readLine().split("=")[1];
			String lblOrdenarStr = br.readLine().split("=")[1];
			String comboBoxOrdenarStr = br.readLine().split("=")[1];
			String rdbtnAscendenteStr = br.readLine().split("=")[1];
			String rdbtnDescendenteStr = br.readLine().split("=")[1];
			String btnOrdenarStr = br.readLine().split("=")[1];
			String btnFusionarStr = br.readLine().split("=")[1];
			String txtTextfieldBuscarStr = br.readLine().split("=")[1];
			String btnBuscarStr = br.readLine().split("=")[1];
			strCoincidencias = br.readLine().split("=")[1];
			strPopupNombreMensaje = br.readLine().split("=")[1];
			strPopupSobrescrituraMensaje = br.readLine().split("=")[1];
			br.close();
			btnCargarDirectorio.setText(btnCargarDirectorioStr);
			lblDirectorio.setText(lblDirectorioStr);
			lblOrdenar.setText(lblOrdenarStr);
			Vector<String> comboBoxItems = new Vector<String>();
			comboBoxItems.add(comboBoxOrdenarStr.split(";")[0]);
			comboBoxItems.add(comboBoxOrdenarStr.split(";")[1]);
			comboBoxItems.add(comboBoxOrdenarStr.split(";")[2]);
			DefaultComboBoxModel<String> model = new DefaultComboBoxModel<String>(comboBoxItems);
			comboBoxOrdenar.setModel(model);
			rdbtnAscendente.setText(rdbtnAscendenteStr);
			rdbtnDescendente.setText(rdbtnDescendenteStr);
			btnOrdenar.setText(btnOrdenarStr);
			btnFusionar.setText(btnFusionarStr);
			txtTextfieldBuscar.setText(txtTextfieldBuscarStr);
			btnBuscar.setText(btnBuscarStr);
			
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, e.getMessage(), "Exception", JOptionPane.ERROR_MESSAGE);
			e.printStackTrace();
		}

	}
	
	/**
	 * Método que devuelve un array de strings con la lista de los ficheros de texto del directorio proporcionado como parámetro
	 * 
	 * @param directorioTrabajo: String con el directorio seleccionado
	 * @return Devuelve un array de strings
	 */
	public String[] listarFicheros(String directorioTrabajo) {
		File directorio = new File(directorioTrabajo);
		FiltroExtension filtro = new FiltroExtension(".txt");
		String[] contenido = directorio.list(filtro);
		return contenido;
	}
	
	/**
	 * Método que proporciona el tamaño en bytes de los ficheros proporcionados
	 * 
	 * @param listaFicheros: array de String con la lista de ficheros
	 * @return Devuelve un array de long con el tamaño de los ficheros en bytes
	 */
	public long[] obtenerTamanyoFicheros(String[] listaFicheros) {
		long[] arrayTamanyos = new long[listaFicheros.length];
		for (int i = 0; i < arrayTamanyos.length; i++) {
			File fichero = new File(directorioTrabajo + File.separator + listaFicheros[i]);
			arrayTamanyos[i] = fichero.length();
		}
		return arrayTamanyos;
	}
	
	/**
	 * Método que proporciona la fecha y hora de última modificación de los ficheros proporcionados
	 * 
	 * @param listaFicheros: array de String con la lista de ficheros
	 * @return Devuelve un array de long con el tiempo en segundos desde el inicio 1/1/1970 hasta la última fecha de modificación
	 */
	public long[] obtenerFechaFicheros(String[] listaFicheros) {
		long[] arrayFecha = new long[listaFicheros.length];
		for (int i = 0; i < arrayFecha.length; i++) {
			File fichero = new File(directorioTrabajo + File.separator + listaFicheros[i]);
			arrayFecha[i] = fichero.lastModified();
		}
		return arrayFecha;
	}
	
	/**
	 * Método para crear la lista ordenada de ficheros
	 * 
	 * @return Devuelve el objeto Model para incluirlo en el componente JList correspondiente
	 */
	public DefaultListModel<String> crearLista() {
		listaFicheros = listarFicheros(directorioTrabajo);
		if (criterioOrdenacion == 0) {  //Por nombre
			if (orden == 0) { //Ascendente
				Arrays.sort(listaFicheros, Collator.getInstance());
			} else { //Descendente
				Arrays.sort(listaFicheros, Collator.getInstance());
				Collections.reverse(Arrays.asList(listaFicheros));
			}
		}
		long[] tamanyoFicheros = obtenerTamanyoFicheros(listaFicheros);
		long[] fechaFicheros = obtenerFechaFicheros(listaFicheros);
		if (criterioOrdenacion == 1) {  //Por tamanyo
			String[] listaFicherosOld = Arrays.copyOf(listaFicheros, listaFicheros.length);
			long[] tamanyoFicherosOld = Arrays.copyOf(tamanyoFicheros, tamanyoFicheros.length);
			long[] fechaFicherosOld = Arrays.copyOf(fechaFicheros, fechaFicheros.length);
			Arrays.sort(tamanyoFicheros);
			if (orden == 1) { //Si descendente
				long[] tamanyoFicherosAux = Arrays.copyOf(tamanyoFicheros, tamanyoFicheros.length);
				for (int i = 0; i < tamanyoFicheros.length; i++) {
					tamanyoFicheros[tamanyoFicheros.length-i-1] = tamanyoFicherosAux[i];
				}
			}
			for (int i = 0; i < tamanyoFicheros.length; i++) {
				for (int j = 0; j < tamanyoFicherosOld.length; j++) {
					if (tamanyoFicheros[i] == tamanyoFicherosOld[j]) {
						listaFicheros[i] = listaFicherosOld[j];
						fechaFicheros[i] = fechaFicherosOld[j];
					}
				}
			}
		}
		if (criterioOrdenacion == 2) {  //Por fecha
			String[] listaFicherosOld = Arrays.copyOf(listaFicheros, listaFicheros.length);
			long[] tamanyoFicherosOld = Arrays.copyOf(tamanyoFicheros, tamanyoFicheros.length);
			long[] fechaFicherosOld = Arrays.copyOf(fechaFicheros, fechaFicheros.length);
			Arrays.sort(fechaFicheros);
			if (orden == 1) { //Si descendente
				long[] fechaFicherosAux = Arrays.copyOf(fechaFicheros, fechaFicheros.length);
				for (int i = 0; i < fechaFicheros.length; i++) {
					fechaFicheros[fechaFicheros.length-i-1] = fechaFicherosAux[i];
				}
			}
			for (int i = 0; i < fechaFicheros.length; i++) {
				for (int j = 0; j < fechaFicherosOld.length; j++) {
					if (fechaFicheros[i] == fechaFicherosOld[j]) {
						listaFicheros[i] = listaFicherosOld[j];
						tamanyoFicheros[i] = tamanyoFicherosOld[j];
					}
				}
			}
		}
		DefaultListModel<String> model = new DefaultListModel<String>();
		for (int i = 0; i < listaFicheros.length; i++) {
			//String strTamanyo = String.valueOf((int) Math.ceil(tamanyoFicheros[i] / 1024.0)) + " KB";
			String strTamanyo = String.valueOf(tamanyoFicheros[i]) + " B";
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String strFecha = dateFormat.format(fechaFicheros[i]);
			model.addElement(listaFicheros[i] + "  |  " + strTamanyo + "  |  " + strFecha);
		}
		return model;
	}
	
	/**
	 * Método que fusiona dos o más archivos de texto en otro fichero indicado por el usuario. Incluye protección por sobrescritura.
	 * 
	 * @param ficherosSeleccionados
	 */
	public void fusionar(int[] ficherosSeleccionados) {
		String todo = "";
		for (int i = 0; i < ficherosSeleccionados.length; i++) {
			Path path = Path.of(directorioTrabajo + File.separator + listaFicheros[ficherosSeleccionados[i]]);
			try {
				String contents = Files.readString(path);  //Lee todo el contenido del fichero en un string (apto para ficheros no muy grandes)
				todo += contents + "\n";
			} catch (IOException e) {
				JOptionPane.showMessageDialog(null, e.getMessage(), "Exception", JOptionPane.ERROR_MESSAGE);
				e.printStackTrace();
			}
		}
		String nuevoNombre = JOptionPane.showInputDialog(strPopupNombreMensaje);
		if (Arrays.asList(listaFicheros).contains(nuevoNombre)) {
			int opcion = JOptionPane.showConfirmDialog(null, strPopupSobrescrituraMensaje);
			if (opcion != 0) {  //Cancelar sobrescritura
				return;
			}
		}
		
		try {
			File nuevoFichero = new File(directorioTrabajo + File.separator + nuevoNombre);
			FileWriter fw = new FileWriter(nuevoFichero);
			BufferedWriter bw = new BufferedWriter(fw);
			bw.write(todo);
			bw.close();
			DefaultListModel<String> model = crearLista();
			listArchivos.setModel(model);
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, e.getMessage(), "Exception", JOptionPane.ERROR_MESSAGE);
			e.printStackTrace();
		}
		
	}
	
	/**
	 * Método para buscar las coincidencias del texto proporcionado
	 * 
	 * @param texto: texto a buscar
	 */
	public void buscar(String texto) {
		DefaultListModel<String> model = new DefaultListModel<String>();
		for (int i = 0; i < listaFicheros.length; i++) {
			Path path = Path.of(directorioTrabajo + File.separator + listaFicheros[i]);
			try {
				String contents = Files.readString(path);  //Lee todo el contenido del fichero en un string (apto para ficheros no muy grandes)
				int coincidencias = contents.split(texto).length - 1; //Utilizar split para ver las coincidencias (ojo si hubiera caracteres especiales, habría que escaparlos primero)
				model.addElement(listaFicheros[i] + "  ->  " + coincidencias + " " + strCoincidencias);
			} catch (IOException e) {
				JOptionPane.showMessageDialog(null, e.getMessage(), "Exception", JOptionPane.ERROR_MESSAGE);
				e.printStackTrace();
			}
		}
		listBuscar.setModel(model);
	}

	/**
	 *  Método para crear la interfaz gráfica
	 */
	public Principal() {
		setTitle("ADD - AE01");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 869, 656);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);

		btnCargarDirectorio = new JButton("btnCargarDirectorio");
		btnCargarDirectorio.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFileChooser fc = new JFileChooser();
				fc.setCurrentDirectory(new java.io.File("."));
				fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
				int returnVal = fc.showOpenDialog(null);
				if (returnVal == JFileChooser.APPROVE_OPTION) {
					File directorio = fc.getSelectedFile();
					directorioTrabajo = directorio.getAbsolutePath();
					lblDirectorio.setText(directorioTrabajo);
					DefaultListModel<String> model = crearLista();
					listArchivos.setModel(model);
				}
			}
		});
		btnCargarDirectorio.setFont(new Font("Tahoma", Font.PLAIN, 12));
		btnCargarDirectorio.setBounds(28, 28, 130, 33);
		contentPane.add(btnCargarDirectorio);

		lblDirectorio = new JLabel("lblDirectorio");
		lblDirectorio.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblDirectorio.setBounds(168, 38, 502, 13);
		contentPane.add(lblDirectorio);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(28, 71, 564, 229);
		contentPane.add(scrollPane);

		listArchivos = new JList<String>();
		listArchivos.setFont(new Font("Tahoma", Font.PLAIN, 12));
		scrollPane.setViewportView(listArchivos);

		lblOrdenar = new JLabel("lblOrdenar");
		lblOrdenar.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblOrdenar.setBounds(615, 74, 194, 13);
		contentPane.add(lblOrdenar);

		comboBoxOrdenar = new JComboBox<String>();
		comboBoxOrdenar.setFont(new Font("Tahoma", Font.PLAIN, 12));
		comboBoxOrdenar.setBounds(615, 108, 206, 21);
		contentPane.add(comboBoxOrdenar);

		rdbtnAscendente = new JRadioButton("rdbtnAscendente");
		rdbtnAscendente.setSelected(true);
		buttonGroup.add(rdbtnAscendente);
		rdbtnAscendente.setFont(new Font("Tahoma", Font.PLAIN, 12));
		rdbtnAscendente.setBounds(615, 146, 125, 21);
		contentPane.add(rdbtnAscendente);

		rdbtnDescendente = new JRadioButton("rdbtnDescendente");
		buttonGroup.add(rdbtnDescendente);
		rdbtnDescendente.setFont(new Font("Tahoma", Font.PLAIN, 12));
		rdbtnDescendente.setBounds(615, 176, 125, 21);
		contentPane.add(rdbtnDescendente);

		btnIdiomaES = new JButton("ES");
		btnIdiomaES.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				configurarIdioma("ES");
				btnIdiomaES.setFont(btnIdiomaES.getFont().deriveFont(Font.BOLD));
				btnIdiomaVAL.setFont(btnIdiomaVAL.getFont().deriveFont(Font.PLAIN));
				btnIdiomaEN.setFont(btnIdiomaEN.getFont().deriveFont(Font.PLAIN));
			}
		});
		btnIdiomaES.setFont(new Font("Tahoma", Font.BOLD, 10));
		btnIdiomaES.setBounds(676, 28, 46, 21);
		contentPane.add(btnIdiomaES);

		btnIdiomaVAL = new JButton("VAL");
		btnIdiomaVAL.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				configurarIdioma("VAL");
				btnIdiomaES.setFont(btnIdiomaES.getFont().deriveFont(Font.PLAIN));
				btnIdiomaVAL.setFont(btnIdiomaVAL.getFont().deriveFont(Font.BOLD));
				btnIdiomaEN.setFont(btnIdiomaEN.getFont().deriveFont(Font.PLAIN));
			}
		});
		btnIdiomaVAL.setFont(new Font("Tahoma", Font.PLAIN, 10));
		btnIdiomaVAL.setBounds(720, 28, 55, 21);
		contentPane.add(btnIdiomaVAL);

		btnIdiomaEN = new JButton("EN");
		btnIdiomaEN.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				configurarIdioma("EN");
				btnIdiomaES.setFont(btnIdiomaES.getFont().deriveFont(Font.PLAIN));
				btnIdiomaVAL.setFont(btnIdiomaVAL.getFont().deriveFont(Font.PLAIN));
				btnIdiomaEN.setFont(btnIdiomaEN.getFont().deriveFont(Font.BOLD));
			}
		});
		btnIdiomaEN.setFont(new Font("Tahoma", Font.PLAIN, 10));
		btnIdiomaEN.setBounds(773, 28, 47, 21);
		contentPane.add(btnIdiomaEN);

		btnOrdenar = new JButton("btnOrdenar");
		btnOrdenar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				criterioOrdenacion = comboBoxOrdenar.getSelectedIndex();
				if (rdbtnAscendente.isSelected()) {
					orden = 0;
				} else {
					orden = 1;
				}
				DefaultListModel<String> model = crearLista();
				listArchivos.setModel(model);
			}
		});
		btnOrdenar.setFont(new Font("Tahoma", Font.PLAIN, 12));
		btnOrdenar.setBounds(615, 215, 148, 33);
		contentPane.add(btnOrdenar);

		txtTextfieldBuscar = new JTextField();
		txtTextfieldBuscar.setText("textFieldBuscar");
		txtTextfieldBuscar.setFont(new Font("Tahoma", Font.PLAIN, 12));
		txtTextfieldBuscar.setBounds(28, 337, 172, 26);
		contentPane.add(txtTextfieldBuscar);
		txtTextfieldBuscar.setColumns(10);

		btnBuscar = new JButton("btnBuscar");
		btnBuscar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String texto = txtTextfieldBuscar.getText();
				buscar(texto);
			}
		});
		btnBuscar.setFont(new Font("Tahoma", Font.PLAIN, 12));
		btnBuscar.setBounds(210, 333, 148, 33);
		contentPane.add(btnBuscar);

		btnFusionar = new JButton("btnFusionar");
		btnFusionar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int[] ficherosSeleccionados = listArchivos.getSelectedIndices();
				fusionar(ficherosSeleccionados);
			}
		});
		btnFusionar.setFont(new Font("Tahoma", Font.PLAIN, 12));
		btnFusionar.setBounds(615, 258, 148, 33);
		contentPane.add(btnFusionar);

		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(28, 376, 562, 227);
		contentPane.add(scrollPane_1);

		listBuscar = new JList<String>();
		scrollPane_1.setViewportView(listBuscar);
		listBuscar.setFont(new Font("Tahoma", Font.PLAIN, 12));
	}

}
