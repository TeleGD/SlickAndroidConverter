
public class InputUtils {
	
	public static String getDefaultAndroidInput(String key){
		String indexInput = "Input.";
		
		if(key.contains("KEY_DOWN")) indexInput += "BUTTON_DOWN";
		else if(key.contains("KEY_UP")) indexInput += "BUTTON_UP";
		else if(key.contains("KEY_LEFT"))indexInput += "BUTTON_LEFT";
		else if(key.contains("KEY_RIGHT"))indexInput += "BUTTON_RIGHT";
		else if(key.contains("KEY_ESCAPE"))indexInput += "BUTTON_BACK";
		else if(key.contains("KEY_BACK"))indexInput += "BUTTON_BACK";
		else if(key.contains("KEY_SPACE"))indexInput += "PRESS_SCREEN_DOWN";
		else if(key.contains("KEY_ENTER"))indexInput += "PRESS_SCREEN_DOWN";
		else if(key.contains("KEY_S"))indexInput += "BUTTON_1";	
		else indexInput += "NONE";
		return indexInput;
	}
}
