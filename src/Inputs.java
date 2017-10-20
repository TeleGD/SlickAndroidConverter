import java.util.HashMap;
import java.util.regex.Pattern;

public class Inputs {
	private String keyFile;
	private HashMap<String,String> inputsMap = new HashMap<String,String>();

	public Inputs(String keyFile) {
		setKeyFile(keyFile);
	}

	private void setKeyFile(String keyFile) {
		this.keyFile = keyFile;		
	}

	public void addInput(String control_java, String control_android) {
		int i=1;
		String key = control_java;
		while(inputsMap.containsKey(key)){
			key = control_java+"_"+i;
			i++;
		}
		inputsMap.put(key, control_android);
	}

	public static Inputs FromString(String key,String content) {
		String[] inputSplit = content.split(Pattern.quote(";"));
		
		Inputs inputs=new Inputs(key);
		for(int j=0;j<inputSplit.length-1;j++)
		{
			if(!inputSplit[j].contains("="))continue;
			

			String control_java = inputSplit[j].substring(0, inputSplit[j].indexOf("=")).trim();
			String control_android = inputSplit[j].substring(inputSplit[j].indexOf("=")+1).trim();

			inputs.addInput(control_java,control_android);
		}
		return inputs;
		
	}

	public HashMap<String, String> getInputs() {
		return inputsMap;
	}

	public boolean hasInput(String key) {
		return inputsMap.containsKey(key);
	}

	public void setInput(String control_java, String control_android) {
		if(inputsMap.containsKey(control_java)){
			inputsMap.put(control_java, control_android);
		}else{
			addInput(control_java,control_android);
		}
		
	}

}
