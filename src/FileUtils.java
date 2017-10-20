import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import javax.activation.MimetypesFileTypeMap;

public class FileUtils {

	
	public static boolean isImage(File f){
		if(f.getName().endsWith(".png") || f.getName().endsWith(".jpeg"))return true;

		String mimetype= new MimetypesFileTypeMap().getContentType(f);
        String type = mimetype.split("/")[0];
		//System.out.println("f.getName = "+f.getName()+" mimetype = "+mimetype);

        return type.equals("image");
	}
	public static void replaceInfile(String search, String substitute, File f) {
		if(isImage(f))return;
		
	    String ligne="";
		BufferedReader br;
		try {
			String chaine="";			
			br = new BufferedReader(new FileReader(f));
			while((ligne=br.readLine())!=null){
				chaine+=ligne+"\n";
			}
			
			br.close();
			
			chaine = chaine.replace(search, substitute);
			
			BufferedWriter bw=new BufferedWriter(new FileWriter(f));
			bw.write(chaine);
			bw.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
				
	}

	public static void replaceInfileWithNameInclude(String search, String substitute, File file) {
		if(file.isFile())FileUtils.replaceInfile(search,substitute, file);		
		
		if(file.getName().toLowerCase().contains(search.toLowerCase())){
			System.out.println("name File = "+file.getName());

			String name = file.getName().replace(search, substitute);
			name = name.replace(search.toLowerCase(), substitute.toLowerCase());
			name = name.replace(search.toUpperCase(), substitute.toUpperCase());
			
			renameFile(file,name);
		}
	}

	public static boolean renameFile(File file, String name) {
		File fileRenamed = new File(file.getParentFile().getAbsolutePath()+File.separator+name);

		System.out.println("renommer le fichier "+file.getAbsolutePath()+"  en  "+fileRenamed.getAbsolutePath());
		boolean b = file.renameTo( fileRenamed);
		if(b==false)System.out.println("impossible de renommer le fichier "+file.getAbsolutePath());
		return b;
	}
	public static String getAllContent(File f) {
		if(!f.exists())return null;
		 String ligne="";
			BufferedReader br;
			try {
				String chaine="";			
				br = new BufferedReader(new FileReader(f));
				while((ligne=br.readLine())!=null){
					chaine+=ligne+"\n";
				}
				
				br.close();
				
				return chaine;
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			return null;
	}
	public static void saveAllContent(File file, String chaine) {
		BufferedWriter bw;
		try {
			bw = new BufferedWriter(new FileWriter(file));
			bw.write(chaine);
			bw.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
				
	}

}
