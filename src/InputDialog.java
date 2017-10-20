import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.io.File;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class InputDialog extends JFrame implements ActionListener {
	
	
	private File file;
	private JLabel titleLab;
	private JPanel centerPane = new JPanel(), southPanel = new JPanel();
	private JButton enregistrer =new JButton("Enregistrer");
	private JButton annuler =new JButton("Annuler");
	private JButton automatique =new JButton("Configuration par défaut");

	private Inputs inputs;
	private int nbInputs = 0;
	private int sizeFrameInitial = 160;
	
	private String[] inputList = new String[]{"ANAL LEFT","ANAL RIGHT","ANAL UP","ANAL DOWN","PRESS SCREEN LONG DOWN","PRESS SCREEN DOWN","PRESS RELEASED","ACCEL LEFT","ACCEL RIGHT","BUTTON BACK","BUTTON LEFT", "BUTTON RIGHT","BUTTON UP", "BUTTON DOWN","BUTTON 1","BUTTON 2","BUTTON 3"};

	public InputDialog(Inputs inputs,File f){
		super("Personaliser les inputs");
		this.inputs=inputs;
		this.file=f;
		
		nbInputs = 0;
		parseInputFile();
		
		this.setLayout(new BorderLayout());

		titleLab = new JLabel("Personnaliser "+f.getName());
		titleLab.setHorizontalAlignment(JLabel.CENTER);
		titleLab.setPreferredSize(new Dimension(500,60));

		centerPane.setLayout(new GridLayout(nbInputs,2));	
		centerPane.setPreferredSize(new Dimension(450,25*nbInputs));

		JPanel centerPanel = new JPanel();
		centerPanel.add(centerPane);
		centerPanel.add(automatique);
		
		annuler.addActionListener(this);
		enregistrer.addActionListener(this);
		automatique.addActionListener(this);
	
		southPanel.add(annuler);
		southPanel.add(enregistrer);
		
		this.add(titleLab,BorderLayout.NORTH);
		this.add(centerPanel,BorderLayout.CENTER);
		this.add(southPanel,BorderLayout.SOUTH);

		this.setSize(500, sizeFrameInitial+nbInputs*25);
		this.setLocationRelativeTo(null);
		this.setVisible(true);
		
		//exporter();
		
	}


	private void parseInputFile() {
		
		for(String key:inputs.getInputs().keySet()){
			
			JComboBox<String> jcb = new JComboBox<String>();
			
			for(String item:inputList){
				jcb.addItem(item);
			}
			
			centerPane.add(new JLabel(key+":"));
			centerPane.add(jcb);
			
			String val = inputs.getInputs().get(key).replace("_", " ").replace("Input.", "");
			System.out.println("key = "+key);
			jcb.setSelectedIndex(getInputIndex(val));
			nbInputs++;
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == annuler){
			this.dispatchEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING));
		}else if(e.getSource() == enregistrer){
			saveInputs();
			this.dispatchEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING));
		}else if(e.getSource() == automatique){
			inputsAuto();
		}
	}





	private void saveInputs() {

		for(int i=0;i<centerPane.getComponentCount();i+=2){
			String value = "Input.NONE";
			if(((JComboBox)centerPane.getComponent(i+1)).getSelectedItem() != null){
				value = "Input."+((JComboBox)centerPane.getComponent(i+1)).getSelectedItem().toString().replace(" ", "_");
			}
			 
			String key =  ((JLabel)centerPane.getComponent(i)).getText().toString();
			key = key.substring(0,key.length()-1);
			inputs.setInput(key,value);
		}
	}


	private void inputsAuto() {
		for(int i=0;i<centerPane.getComponentCount();i+=2){
			String lab = ((JLabel) centerPane.getComponent(i)).getText();
			int indexInput = getInputIndex(InputUtils.getDefaultAndroidInput(lab).replace("_", " "));
			((JComboBox)centerPane.getComponent(i+1)).setSelectedIndex(indexInput);

		}
	}

	private int getInputIndex(String title) {
		for(int i=0;i<inputList.length;i++)
		{
			if(inputList[i].equals(title))return i;
		}
		return -1;
	}


	public File getFile() {
		// TODO Auto-generated method stub
		return file;
	}


	public Inputs getInputs() {
		return inputs;
	}
}
