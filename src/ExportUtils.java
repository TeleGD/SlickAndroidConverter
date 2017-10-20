import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.regex.Pattern;

import javax.swing.JComboBox;
import javax.swing.JLabel;

public class ExportUtils {

	private OnExportedListener onExportedListener;
	private String nameApp;
	private String nameProject;

	public final String TEMPLATE_NAME = "Template_TGD";
	private HashMap<String, Inputs> inputsFile = new HashMap<String,Inputs>();
	public static String FROM_DIRECTORY="/Users/eveler/Google Drive/workspace/tgd_multigame";
	public static String TO_DIRECTORY="/Users/eveler/Google Drive/workspace/tgd_multigame";
	
	public ExportUtils(String nameApp){
		this.nameApp = nameApp;
		this.nameProject = nameApp.replace(" ", "_");
	}
	
	public void exportGame() {
		
		new Thread(new Runnable(){

			@Override
			public void run() {
				TO_DIRECTORY = TO_DIRECTORY +File.separator;
				
				System.out.println("Dossier Source : "+FROM_DIRECTORY);
				System.out.println("Dossier Destination : "+TO_DIRECTORY);
				System.out.println("Nom Application : "+nameApp);
				System.out.println("Nom Projet Android : "+nameProject);


				//ON COPIE LE TEMPLATE ANDROID DANS LE BON REPERTOIRE
				File fileProject = new File(TO_DIRECTORY+nameProject);
				fileProject.mkdir();
				copyFiles(false,new File(TEMPLATE_NAME),fileProject);

				//ON CHANGE LE NOM DE L'APP
				File stringsFile = new File(TO_DIRECTORY+nameProject+File.separator+"app"+File.separator+"src"+File.separator+"main"+File.separator+"res"+File.separator+"values"+File.separator+"strings.xml");
				FileUtils.replaceInfile("Template_TGD",nameApp, stringsFile);
				
				//ON CHANGE LE NOM DU PROJET
				changeProjectName(fileProject);
				
				//ON AJOUTE LES RESOURCES
				addResourcesFilesToProject();
				
				//ON AJOUTE LES SOURCES JAVA
				addSourcesFilesToProject();		
				onExportedListener.onGameExported();

			}}).start();
		
		
	}
	private void addSourcesFilesToProject() {
		String pathSources = TO_DIRECTORY+nameProject+File.separator+"app"+File.separator+"src"+File.separator+"main"+File.separator+"java";

		copyFiles(false,new File("com"+File.separator), new File(TO_DIRECTORY+File.separator+"java"+File.separator+"com"));
		copyFiles(true,new File(FROM_DIRECTORY+File.separator+"src"+File.separator), new File(pathSources+File.separator));		
	}
	
	private void addResourcesFilesToProject() {
		String pathResource = TO_DIRECTORY+nameProject+File.separator+"app"+File.separator+"src"+File.separator+"main"+File.separator+"res";
		
		File drawableDirectory=new File(pathResource+File.separator+"drawable-nodpi"+File.separator);
		drawableDirectory.mkdirs();
		
		File rawDirectory=new File(pathResource+File.separator+"raw"+File.separator);
		rawDirectory.mkdirs();
		
		File fontDirectory=new File(pathResource+File.separator+"assets"+File.separator+"fonts"+File.separator);
		fontDirectory.mkdirs();
		
		copyFilesResources(new File(FROM_DIRECTORY+File.separator+"image"), drawableDirectory,"");
		copyFilesResources(new File(FROM_DIRECTORY+File.separator+"Image"), drawableDirectory,"");
		copyFilesResources(new File(FROM_DIRECTORY+File.separator+"images"), drawableDirectory,"");
		copyFilesResources(new File(FROM_DIRECTORY+File.separator+"Images"), drawableDirectory,"");

		copyFilesResources(new File(FROM_DIRECTORY+File.separator+"music"), rawDirectory,"");
		copyFilesResources(new File(FROM_DIRECTORY+File.separator+"musics"), rawDirectory,"");
		copyFilesResources(new File(FROM_DIRECTORY+File.separator+"Music"), rawDirectory,"");
		copyFilesResources(new File(FROM_DIRECTORY+File.separator+"Musics"), rawDirectory,"");
		copyFilesResources(new File(FROM_DIRECTORY+File.separator+"Musiques"), rawDirectory,"");
		copyFilesResources(new File(FROM_DIRECTORY+File.separator+"musiques"), rawDirectory,"");
		copyFilesResources(new File(FROM_DIRECTORY+File.separator+"musique"), rawDirectory,"");
		copyFilesResources(new File(FROM_DIRECTORY+File.separator+"Musique"), rawDirectory,"");

		copyFilesResources(new File(FROM_DIRECTORY+File.separator+"sounds"), rawDirectory,"");
		copyFilesResources(new File(FROM_DIRECTORY+File.separator+"sound"), rawDirectory,"");
		copyFilesResources(new File(FROM_DIRECTORY+File.separator+"Sound"), rawDirectory,"");
		copyFilesResources(new File(FROM_DIRECTORY+File.separator+"Sounds"), rawDirectory,"");
		copyFilesResources(new File(FROM_DIRECTORY+File.separator+"Sons"), rawDirectory,"");
		copyFilesResources(new File(FROM_DIRECTORY+File.separator+"sons"), rawDirectory,"");
		copyFilesResources(new File(FROM_DIRECTORY+File.separator+"Son"), rawDirectory,"");
		copyFilesResources(new File(FROM_DIRECTORY+File.separator+"son"), rawDirectory,"");
		
		copyFilesResources(new File(FROM_DIRECTORY+File.separator+"fonts"), fontDirectory,"");
		copyFilesResources(new File(FROM_DIRECTORY+File.separator+"font"), fontDirectory,"");
		copyFilesResources(new File(FROM_DIRECTORY+File.separator+"Font"), fontDirectory,"");
		copyFilesResources(new File(FROM_DIRECTORY+File.separator+"Fonts"), fontDirectory,"");

	}
	
	private void changeProjectName(File file) {
		if(file.isDirectory() && file.listFiles()!=null){
			
			for(File f:file.listFiles())
			{
			
				if(f.isDirectory()){
					FileUtils.replaceInfileWithNameInclude(TEMPLATE_NAME,nameProject, f);
					changeProjectName(f);
				}
				else {
					FileUtils.replaceInfileWithNameInclude(TEMPLATE_NAME,nameProject, f);
				}
			}
			
		}else{			
			FileUtils.replaceInfileWithNameInclude(TEMPLATE_NAME,nameProject, file);
		}
		
	}
	private void copyFiles(boolean b,String fileSrc, String fileDest) {
		copyFiles(b,new File(fileSrc),new File(fileDest));
	}
	
	private void copyFiles(boolean b,File fileSrc, File fileDest) {
		if(!fileSrc.exists())return;
		if(fileSrc.isDirectory() && fileSrc.listFiles()!=null){
			fileSrc.mkdirs();
			fileDest.mkdirs();
			for(File f:fileSrc.listFiles())
			{
				if(f.isDirectory())copyFiles(b,f, new File(fileDest.getAbsolutePath()+File.separator + f.getName()));
				else copyFiles(b,f, fileDest);
			}
		}else{
			try {
				File sourceFile=new File(fileDest.getAbsolutePath()+File.separator + fileSrc.getName());
				Files.copy(fileSrc.toPath(), sourceFile.toPath(),  StandardCopyOption.REPLACE_EXISTING);
				
				if(b)alterFile(sourceFile);
			} catch (IOException e) {
				e.printStackTrace();
			} 
		}
		
	}
	private void alterFile(File sourceFile) {

		String ligne="";
		BufferedReader br;
		
		Inputs inputs = null;
		
		if(inputsFile.containsKey(sourceFile.getName())) inputs = inputsFile.get(sourceFile.getName());
		
		try {
			String chaine="";
			boolean hasSlick=false;
			
			br = new BufferedReader(new FileReader(sourceFile));
			boolean writeAllowed=false;
			while((ligne=br.readLine())!=null){
				writeAllowed=true;
				if(ligne.startsWith("package")){
					//ligne=ligne.replace("package ", "package com.tgd."+nameProject+".");
				}
				if(ligne.startsWith("import")){
					if(ligne.contains("slick")){
						hasSlick=true;
						writeAllowed=false;
					}
				}

				if(writeAllowed){
					chaine+=ligne+"\n";
				}
			}
			
			if(inputs !=null){
				for(String control_java:inputs.getInputs().keySet()){
					chaine = chaine.replace(control_java, inputs.getInputs().get(control_java));
				}
			}
			
			
			if(hasSlick){
				int indexDebutCode =  chaine.indexOf("package ")+chaine.substring(chaine.indexOf("package ")).indexOf("\n");
				chaine=chaine.substring(0,indexDebutCode)+"\nimport com.tgd.slick_mobile.*;import com.tgd.slick_mobile.listener.*;\n"+chaine.substring(indexDebutCode+1);
			}
			
			chaine = chaine.replace("import java.awt.Font;", "");
			chaine = chaine.replace("import java.awt.FontFormatException;", "");
			chaine = chaine.replace("java.awt.Font", "Font");
			chaine = removeBloc(chaine,"public static void main");
			
			br.close();
			
			BufferedWriter bw=new BufferedWriter(new FileWriter(sourceFile));
			bw.write(chaine);
			bw.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
	
	private String removeBloc(String chaineInitial, String s) {
		int indexInitial = chaineInitial.indexOf(s);
		if(indexInitial == -1) return chaineInitial;
		
		String chaine = chaineInitial.substring(0,indexInitial);
		int i = indexInitial + chaineInitial.substring(indexInitial).indexOf("{")+1;
		int count = 1;
		while(count >0){
			if(chaineInitial.substring(i,i+1).equals("{")) count+=1;
			if(chaineInitial.substring(i,i+1).equals("}")) count-=1;
			i++;
		}
		
		chaine += chaineInitial.substring(i);
		return chaine;
	}
	
	private void copyFilesResources(File fileSrc, File fileDest,String name) {
		if(!fileSrc.exists())return;

		fileDest.mkdirs();
		if(fileSrc.isDirectory() && fileSrc.listFiles()!=null){
			for(File f:fileSrc.listFiles())
			{
				copyFilesResources(f, fileDest,name+f.getName()+"_");
			}
		}else{
			try {
				if(name.lastIndexOf("_")==name.length()-1)name=name.substring(0,name.length()-1);
				name=name.replace(" ", "_");
				if(!name.startsWith(".") && !name.contains("ds_store") && !name.endsWith(".db"))
					Files.copy(fileSrc.toPath(), new File(fileDest.getAbsolutePath()+File.separator +toAndroidResourceName(name)).toPath(),  StandardCopyOption.REPLACE_EXISTING);
			} catch (IOException e) {
				e.printStackTrace();
			} 
		}
	}
	
	private String toAndroidResourceName(String name) {
		name=name.toLowerCase();
		name=name.replace("-", "_");
		return name;
	}
	
	public void setOnExportedListener(OnExportedListener onExportedListener) {
		this.onExportedListener = onExportedListener;
	}

	public void setInputsFile(HashMap<String, Inputs> inputsFile) {
		this.inputsFile = inputsFile;
	}
	

}
