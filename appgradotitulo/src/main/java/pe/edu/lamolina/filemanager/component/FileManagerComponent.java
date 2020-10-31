package pe.edu.lamolina.filemanager.component;


import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import pe.edu.lamolina.logging.UNALMLogger;


@Component
public class FileManagerComponent {
	
	private final static Logger log = Logger.getLogger(FileManagerComponent.class);
	
	@Value("${services.ftp.serverhost}")
    private String serverhost;
	
	@Value("${services.ftp.port}")
	private Integer port;
	
	@Value("${services.ftp.user}")
	private String user;
	
	@Value("${services.ftp.password}")
	private String password;
	
	public void write(byte[] file, String filePath) throws IOException{
		final String method = "write";
		final String params = "byte[] file, String filePath";
		final Object[] data = new Object[] { file, filePath};
		UNALMLogger.entry(log, method, params, data);
		FTPClient ftp = new FTPClient();
		try {
			UNALMLogger.trace(log, method, "this.getServerhost(): "+this.getServerhost());
			UNALMLogger.trace(log, method, "this.getPort(): "+this.getPort());
			UNALMLogger.trace(log, method, "this.getUser(): "+this.getUser());
	
			UNALMLogger.trace(log, method, "this.getPassword(): "+this.getPassword());
			ftp.connect(this.getServerhost(), this.getPort());
			ftp.login(this.getUser(), this.getPassword());
			ftp.enterLocalPassiveMode();
			ftp.setFileType(FTP.BINARY_FILE_TYPE);
			InputStream sourceInputStream = new ByteArrayInputStream(file);
			String[] filePathData=filePath.split("/");
			String fileName="";
			boolean dirExists = true;
			String currentWorkspacePath="";
			for (int i = 0; i < filePathData.length; i++) {
				String folderPathItem = filePathData[i];
				if (i == (filePathData.length - 1)) {
					fileName = folderPathItem;
				} else {
					String dir = folderPathItem;
					currentWorkspacePath+=folderPathItem+"/";
					
					if (!dir.isEmpty()) {
						if (dirExists) {
							UNALMLogger.trace(log, method, "accediendo a "+currentWorkspacePath);
							dirExists = ftp.changeWorkingDirectory(dir);
						}
						if (!dirExists) {
							UNALMLogger.trace(log, method, "Directorio no existe");
							UNALMLogger.trace(log, method, "Creando directorio");
							if (!ftp.makeDirectory(dir)) {
								throw new IOException("Unable to create remote directory '" + currentWorkspacePath + "'.  error='"
										+ ftp.getReplyString() + "'");
							}
							UNALMLogger.trace(log, method, "reaccediendo a "+currentWorkspacePath);
							if (!ftp.changeWorkingDirectory(dir)) {
								throw new IOException("Unable to change into newly created remote directory '" + currentWorkspacePath
										+ "'.  error='" + ftp.getReplyString() + "'");
							}
						}
					}
					
				}
			}
			UNALMLogger.trace(log, method, "fileName: "+fileName);
			boolean done = ftp.storeFile(fileName, sourceInputStream);
			sourceInputStream.close();
			UNALMLogger.trace(log, method, filePath+"[done:"+done+"]");
		} catch (IOException e) {
			UNALMLogger.error(log, method, "Ocurrio un error al sincronizar el archivo", e);
			throw e;
		}
		UNALMLogger.exit(log, method);
		
	}
	
	public void delete(String filePath) throws IOException{
		final String method = "delete";
		final String params = "String filePath";
		final Object[] data = new Object[] {filePath};
		UNALMLogger.entry(log, method, params, data);
		FTPClient ftp = new FTPClient();
		try {
			UNALMLogger.trace(log, method, "this.getServerhost(): "+this.getServerhost());
			UNALMLogger.trace(log, method, "this.getPort(): "+this.getPort());
			UNALMLogger.trace(log, method, "this.getUser(): "+this.getUser());
	
			UNALMLogger.trace(log, method, "this.getPassword(): "+this.getPassword());
			ftp.connect(this.getServerhost(), this.getPort());
			ftp.login(this.getUser(), this.getPassword());
			ftp.enterLocalPassiveMode();
			ftp.setFileType(FTP.BINARY_FILE_TYPE);
			boolean done = ftp.deleteFile(filePath);
			UNALMLogger.trace(log, method,"[done:"+done+"]");
		} catch (IOException e) {
			UNALMLogger.error(log, method, "Ocurrio un error al sincronizar el archivo", e);
			throw e;
		}
		UNALMLogger.exit(log, method);
		
	}
	public String getServerhost() {
		return serverhost;
	}

	public void setServerhost(String serverhost) {
		this.serverhost = serverhost;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Integer getPort() {
		return port;
	}

	public void setPort(Integer port) {
		this.port = port;
	}
	
}