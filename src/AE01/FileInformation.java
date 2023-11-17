package AE01;

import java.io.File;
import java.util.Date;

public class FileInformation {
	private File file;
	private String nom;
	private String extensio;
	private long grandaria;
	private Date dataUltimaModificacio;

	public FileInformation(File file) {
		this.file = file;
		this.nom = file.getName();
		this.extensio = "txt";
		this.grandaria = file.length();
		this.dataUltimaModificacio = new Date(file.lastModified());
	}

	public File getFile() {
		return file;
	}

	public String getNom() {
		return nom;
	}

	public String getExtensio() {
		return extensio;
	}

	public long getGrandaria() {
		return grandaria;
	}

	public Date getDataUltimaModificacio() {
		return dataUltimaModificacio;
	}
}
