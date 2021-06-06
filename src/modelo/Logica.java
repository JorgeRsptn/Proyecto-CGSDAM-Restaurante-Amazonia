package modelo;


import modelo.DAO.UsuarioDAO;


public class Logica {

	private UsuarioDAO conectDAO;



	public String validarIngreso(String user, String pass) {

		String respuesta = null;			

		conectDAO=new UsuarioDAO();
		
		boolean admitido=conectDAO.consultaUsuario(user, pass);

		if (admitido==true) {
			respuesta="admitido";
		}else if (admitido==false){
			respuesta="El usuario o contraseña no son correctos";
		};

		return respuesta ;
	}

	/**
	 * 
	 * @param userPass
	 * @return String encriptado
	 */
	public String encriptPassSHA1 (String userPass) {
		
		String encriptPass="";
		try {
			java.security.MessageDigest message = java.security.MessageDigest.getInstance("SHA1");
			
			byte [] array= message.digest(userPass.getBytes());
			userPass="";			
			StringBuffer sb = new StringBuffer();
			
			for(int i = 0; i < array.length ; i++ ) {
				
				sb.append(Integer.toHexString((array[i] & 0xFF) | 0x100).substring(1,3));
			}
			encriptPass=sb.toString();
			
			
		}catch (Exception e) {
			e.printStackTrace();
		}

		return encriptPass;
	}


}
