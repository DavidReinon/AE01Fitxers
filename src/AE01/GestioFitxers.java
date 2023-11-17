package AE01;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/**
 * Esta clase conte totes les funcionalitats relacionades amb els fitxers
 * 
 * @author David Reinon Garcia
 */
public class GestioFitxers {

	private File directori;
	private ArrayList<FileInformation> informaciofitxers = new ArrayList<FileInformation>();

	public GestioFitxers() {
	}

	public void setDirectori(File dir) {
		directori = dir;
	}

	public ArrayList<FileInformation> getInformaciofitxers() {
		return informaciofitxers;
	}

	/**
	 * S'encarrega de llistar el fixters txt
	 */
	private void llistarFitxersTxt() {
		if (!directori.exists() || !directori.isDirectory()) {
			return;
		}
		informaciofitxers.clear();
		FiltroExtensionV1 filter = new FiltroExtensionV1("txt");
		for (File fitxer : directori.listFiles(filter)) {
			informaciofitxers.add(new FileInformation(fitxer));
		}
	}

	/**
	 * S'encarrega de ordenar tots el fitxers amb la respectiva informacio
	 * 
	 * @param ordenacio  Especifica el parametre de ordenacio (nom, grandaria o
	 *                   ultima data de modificacio)
	 * @param ascendente Especifica si s'ordena de forma ascendent, false es igual a
	 *                   descendent
	 */
	public void OmplirLlistaPerOrdre(String ordenacio, boolean ascendente) {
		llistarFitxersTxt();

		if (informaciofitxers != null) {
			Comparator<FileInformation> comparador = getComparador(ordenacio, ascendente);

			Collections.sort(informaciofitxers, comparador);
		}
	}

	/**
	 * Se encarga de buscar coincidencias de cadena de texto en los archivos
	 * 
	 * @param stringBuscado el string que hay que buscar
	 * @return El numero de coincidencias por cada archivo
	 */
	public ArrayList<String> BuscarCoincidencias(String stringBuscado) {
		ArrayList<String> resultats = new ArrayList<>();

		for (FileInformation archivo : informaciofitxers) {
			int coincidencies = 0;
			try (FileReader fr = new FileReader(archivo.getFile())) {
				BufferedReader br = new BufferedReader(fr);
				String linea;
				while ((linea = br.readLine()) != null) {
					if (linea.contains(stringBuscado))
						coincidencies++;
				}
				br.close();
				fr.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			resultats.add(archivo.getNom() + " => " + coincidencies + " coincidencia/es");
		}

		return resultats;
	}

	/**
	 * S'encarrega de fusionar varios fitxers per a crear uno nou
	 * 
	 * @param archivosSeleccionados Els fitxers seleccionats
	 * @param nomfitxerNou          Nom del nou fitxer
	 * @return Booleano confirmant si la fusio se ha realiztat corerctament
	 */
	public boolean fusionarfitxers(ArrayList<FileInformation> archivosSeleccionados, String nomfitxerNou) {
		if (!nomfitxerNou.toLowerCase().endsWith(".txt")) {
			nomfitxerNou += ".txt";
		}

		File fitxerNou = new File(directori, nomfitxerNou);

		try (BufferedWriter bw = new BufferedWriter(new FileWriter(fitxerNou))) {
			for (FileInformation archivo : archivosSeleccionados) {
				try (BufferedReader br = new BufferedReader(new FileReader(archivo.getFile()))) {
					String linea;
					while ((linea = br.readLine()) != null) {
						bw.write(linea);
						bw.newLine();
					}
				} catch (IOException e) {
					e.printStackTrace();
					return false;
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}

		return true;
	}

	/**
	 * Comprueba si un fitxer ya exiteix en el directoris
	 * 
	 * @param nomfitxerNou Nom del nou fitxer
	 * @return True si existeix, false si no.
	 */
	public boolean fitxerExistent(String nomfitxerNou) {
		File fitxerNou = new File(directori, nomfitxerNou);
		return fitxerNou.exists();
	}

	/**
	 * S'encarrega de fer la comparacio del fitxers y ordenarlos depenent del
	 * parametres
	 * 
	 * @param ordenacio  Especifica el parametre de ordenacio (nom, grandaria o
	 *                   ultima data de modificacio)
	 * @param ascendente Especifica si s'ordena de forma ascendent, false es igual a
	 *                   descendent
	 * @return
	 */
	private Comparator<FileInformation> getComparador(String ordenacio, boolean ascendente) {
		Comparator<FileInformation> comparador = Comparator.comparing(FileInformation::getNom);
		;

		if ("grandaria".equalsIgnoreCase(ordenacio)) {
			comparador = Comparator.comparingLong(FileInformation::getGrandaria);
		} else if ("data".equalsIgnoreCase(ordenacio)) {
			comparador = Comparator.comparing(FileInformation::getDataUltimaModificacio);
		}

		return ascendente ? comparador : comparador.reversed();
	}

}
