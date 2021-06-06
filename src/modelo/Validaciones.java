package modelo;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Validaciones {

	public String entradasLogin(String user, String pass) {
		
		String respuesta=null;
		
		 Pattern pat = Pattern.compile("^[a-zA-Z]{1,20}$");
	     Matcher mat = pat.matcher(user);                                                                           
	     if (mat.matches()) {
	         pat = Pattern.compile("^[a-zA-Z0-9]{1,50}$");
	         mat = pat.matcher(pass);
	         if(mat.matches()) {
	        	 respuesta="valido";
	         }
	     } else {
	         //Cambiar por LOG
	    	 System.out.println("Password o usuario no válidos");
	    	 respuesta="Password o usuario contienen caracteres no permitidos";
	     }		
		return respuesta;
	}
	
	
	public static int isNumPos(String param) {
		int respuesta = -1;
		
		try {
				
		respuesta = Integer.parseInt(param);
		if(respuesta>0) {
			System.out.println(respuesta);
		}else {
			respuesta=-1;
		}
		
		}catch (NumberFormatException e){
			e.printStackTrace();
			respuesta=-1;
		}	
	return respuesta;
	}

}
