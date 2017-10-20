import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class Main extends JFrame implements ActionListener{
	private static String FILE_NAME_CONTROLS = ".controls.txt";

	private HashMap<String,Inputs> inputsFile = new HashMap<String,Inputs>();
	
	private ArrayList<File> files = new ArrayList<File>();
	private ArrayList<JButton> customizeButtons = new ArrayList<JButton>();

	private JPanel contentPane=new JPanel();
	private JPanel southPane=new JPanel();
	private JPanel inputPane = new JPanel();
	private JPanel inputListPane = new JPanel();
	
	private JLabel fromLabel=new JLabel("Chemin du projet JAVA : ");
	private JLabel toLabel=new JLabel("Chemin destination du projet ANDROID : ");
	private JLabel nomLabel=new JLabel("Nom de l'application ANDROID : ");
	private JLabel infosLabel = new JLabel();
	private JLabel northLabel = new JLabel("EXPORTER UN JEU TGD SUR ANDROID");
	private JLabel lab1=new JLabel("");
	private JLabel lab2=new JLabel("");
	
	private JTextField jtfNom = new JTextField();
	private JButton bou1=new JButton("Parcourir ... ");
	private JButton bou2=new JButton("Parcourir ... ");
	
	private JButton ok =new JButton("exporter");
	private JButton saveControls=new JButton("enregistrer les controles");

	private int nbBasicGameState;
	private int sizeFrameInitial = 230;
	
	public Main(){
		this.setTitle("Slick Desktop to Android");
		this.setSize(800, sizeFrameInitial);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);


		northLabel.setHorizontalAlignment(JLabel.CENTER);
		northLabel.setPreferredSize(new Dimension(500,60));
		
		contentPane.add(fromLabel);
		contentPane.add(lab1);
		contentPane.add(bou1);
		contentPane.add(toLabel);
		contentPane.add(lab2);
		contentPane.add(bou2);
		
		contentPane.add(nomLabel);
		contentPane.add(jtfNom);
		
		jtfNom.setText("TGD GAME");

		
		contentPane.setLayout(new GridLayout(3,3));
		contentPane.setPreferredSize(new Dimension(750,80));
		

		bou1.addActionListener(this);
		bou2.addActionListener(this);
		ok.addActionListener(this);
		saveControls.addActionListener(this);
		
		southPane.add(ok);

		inputPane.add(inputListPane);
		inputPane.add(saveControls);
		inputPane.setVisible(false);
		
		JPanel centerPanel = new JPanel();
		centerPanel.add(contentPane);
		centerPanel.add(inputPane);
		centerPanel.add(infosLabel);
		
		this.setLayout(new BorderLayout());
		this.add(northLabel,BorderLayout.NORTH);
		this.add(centerPanel,BorderLayout.CENTER);
		this.add(southPane,BorderLayout.SOUTH);
		
		this.setVisible(true);
		//exporter();
	}
	public static void main(String[] args) {

		new Main();
		
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		
		int index=-1;
		for(int i=0;i<customizeButtons.size();i++){
			if(customizeButtons.get(i)==e.getSource()){
				index = i;
			}
		}
		
		if(index != -1){

			final int indexChoosed= index;
						
			final InputDialog dialog = new InputDialog(inputsFile.get(files.get(indexChoosed).getName()),files.get(indexChoosed));
			dialog.addWindowListener(new WindowListener(){
				
				public void windowActivated(WindowEvent arg0) {}
				public void windowClosing(WindowEvent e) {
					File f = files.get(indexChoosed);
					inputsFile.put(f.getName(), dialog.getInputs());
					updateStatusInputAt(indexChoosed);
				}
				public void windowDeactivated(WindowEvent e) {}
				public void windowDeiconified(WindowEvent e) {}
				public void windowIconified(WindowEvent e) {}
				public void windowOpened(WindowEvent e) {}
				public void windowClosed(WindowEvent e) {}
				
			});
		}else if(e.getSource()==bou1){
			choisirFichier(0);
		}else if(e.getSource()==bou2){
			choisirFichier(1);
		}else if(e.getSource()==ok){
			exportControlsFile();
			

			ExportUtils exportUtils = new ExportUtils(jtfNom.getText());
			exportUtils.setInputsFile(inputsFile);
			exportUtils.setOnExportedListener(new OnExportedListener(){
				public void onGameExported(){
					infosLabel.setText("Export terminé");
					ok.setEnabled(true);

				}
			});
			exportUtils.exportGame();
			
			infosLabel.setText("Export en cours ...");

			ok.setEnabled(false);

		}else if(e.getSource()==saveControls){
			
			exportControlsFile();
		}
	}
	
	
	protected void updateStatusInputAt(int indexChoosed) {
		JLabel lab = (JLabel) inputListPane.getComponent(indexChoosed*3+1);
		if(inputsFile.containsKey(files.get(indexChoosed).getName())){
			lab.setText("paramétré");
			lab.setForeground(new Color(0,200,0));
		}else{
			lab.setText("non paramétré");
			lab.setForeground(Color.BLACK);
		}
	}
	private void choisirFichier(int i) {
		JFileChooser chooser = new JFileChooser();
	    chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);

	    int returnVal = chooser.showOpenDialog(contentPane);
	    if(returnVal == JFileChooser.APPROVE_OPTION) {
	    	
	    	if(i==0)ExportUtils.FROM_DIRECTORY=chooser.getSelectedFile().getAbsolutePath();
	    	else ExportUtils.TO_DIRECTORY=chooser.getSelectedFile().getAbsolutePath();
	    	
	    	lab1.setText(ExportUtils.FROM_DIRECTORY);
	    	lab2.setText(ExportUtils.TO_DIRECTORY);
	    		    	
	    	parseControlsFile();
	    	fillinputListPane();
			System.out.println("inputsFile apres parsage  : "+inputsFile.toString());

	    }
	}
	
	private void parseControlsFile() {
		
		File controlsFile = new File(ExportUtils.FROM_DIRECTORY+File.separator+FILE_NAME_CONTROLS);
		if(controlsFile.exists()){
			//Il existe un fichier de controle
			String chaine = FileUtils.getAllContent(controlsFile);
			String[] split = chaine.split(Pattern.quote("["));

			for(int i=1;i<split.length;i++){
				String key = split[i].substring(0,split[i].indexOf("]")).trim();
				System.out.println("input de puis fichier :"+key);
				Inputs inputs = Inputs.FromString(key,split[i].substring(split[i].indexOf("]")+1));
				inputsFile.put(key, inputs);
			}
			
			System.out.println("inputsFile dans le fichier : "+inputsFile.toString());
			
		}else{
			//Il n'existe pas de fichier de controle
		}
		
		
	}
	private void exportControlsFile() {
		File controlsFile = new File(ExportUtils.FROM_DIRECTORY+File.separator+FILE_NAME_CONTROLS);
		
		String chaine = "";
		
		for(String keyFileName:inputsFile.keySet()){
			chaine += "["+keyFileName+"]\n";

			HashMap<String,String> inputs = inputsFile.get(keyFileName).getInputs();
			
			for(String key:inputs.keySet()){
				String input = inputs.get(key);
				chaine += key+"="+input+";\n";
			}
		}
		
		System.out.println("chaine =  "+chaine);
		FileUtils.saveAllContent(controlsFile, chaine);
		
	}
	
	private void fillinputListPane() {
    	inputListPane.removeAll();
    	nbBasicGameState = 0;
    	    	
		parseFiles(new File(ExportUtils.FROM_DIRECTORY+File.separator+"src"+File.separator));	
		
		GridLayout gl = new GridLayout(nbBasicGameState,3);
    	inputListPane.setLayout(gl);	
		inputListPane.setPreferredSize(new Dimension(750,25*nbBasicGameState));

		inputPane.setPreferredSize(new Dimension(750,50+25*nbBasicGameState));
		if(nbBasicGameState>0)inputPane.setVisible(true);
		this.setSize(this.getWidth(),sizeFrameInitial +50+25*nbBasicGameState);
		this.setLocationRelativeTo(null);
	}
	private void parseFiles(File file) {
		if(!file.exists())return;
		if(file.isDirectory() && file.listFiles()!=null){
			
			for(File f:file.listFiles())
			{
				if(f.isDirectory())parseFiles(f);
				else parseFile(f);
			}
		}else{
			parseFile(file);
		}
	}
	private void parseFile(File f) {
		String chaine = FileUtils.getAllContent(f);

	    Pattern pattern = Pattern.compile("Input\\.KEY[a-z_A-Z0-9]*\\:*\\)*");
	    Matcher matcher = pattern.matcher(chaine);
	    
	    
	    
		if(matcher.find()){
			
			Inputs inputs = new Inputs(f.getName());
			
		    if(inputsFile.containsKey(f.getName())){
		    	inputs = inputsFile.get(f.getName());
			}
			System.out.println("fichier d'input :"+f.getName());

			JButton bout = new JButton("personnaliser les inputs");
			bout.addActionListener(this);
			files.add(f);
			
			customizeButtons.add(bout);
			
			JLabel param = new JLabel("non paramétré");
				
			inputListPane.add(new JLabel(f.getName()));
			inputListPane.add(param);
			inputListPane.add(bout);
			
			updateStatusInputAt(files.size()-1);
		
			nbBasicGameState++;
			
			do{
				String key = chaine.substring(matcher.start(), matcher.end()-1).trim();
				
				if(!inputs.hasInput(key)){
					System.out.println("n'a pas l'input :"+key);
					String indexInput = InputUtils.getDefaultAndroidInput(key);
					inputs.addInput(key, indexInput);
				}
			}while(matcher.find());
			
			inputsFile.put(f.getName(), inputs);
		}
		
		
		
	}
	
	
	

}
