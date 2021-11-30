package cl.arteValparaiso.webapp.models.service;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class UploadFileServiceImpl implements IUploadFileService {

	private final Logger log = LoggerFactory.getLogger(getClass());
	
	private final static String UPLOADS_FOLDER_GALLERY = "uploads/gallery";
	private final static String UPLOADS_FOLDER_NEWS = "uploads/news";
	private final static String UPLOADS_FOLDER_PERFIL = "uploads/perfil";

	@Override
	public Resource load(String filename, String tipo) throws MalformedURLException {

		Path pathFoto = getPath(filename, tipo);
		log.info("pathFoto: " + pathFoto);
		Resource recurso = new UrlResource(pathFoto.toUri());

		if (!recurso.exists() || !recurso.isReadable()) {
			throw new RuntimeException("Error: no se puede cargar la imagen: " + pathFoto.toString());
		}
		return recurso;
	}

	@Override
	public String copy(MultipartFile file, String tipo) throws IOException {
		String uniqueFilename = UUID.randomUUID().toString() + "_" + file.getOriginalFilename();
		Path rootPath = getPath(uniqueFilename, tipo);
		log.info("rootPath: " + rootPath);
		Files.copy(file.getInputStream(), rootPath);

		return uniqueFilename;
	}

	@Override
	public boolean delete(String filename, String tipo) {
		Path rootPath = getPath(filename, tipo);
		File archivo = rootPath.toFile();

		if (archivo.exists() && archivo.canRead()) {
			if (archivo.delete()) {
				return true;
			}
		}
		return false;
	}

	public Path getPath(String filename, String tipo) {
		Path resource = null;
		if(tipo == "obra") {
			resource = Paths.get(UPLOADS_FOLDER_GALLERY).resolve(filename).toAbsolutePath();
		}
		if(tipo == "noticia") {
			resource = Paths.get(UPLOADS_FOLDER_NEWS).resolve(filename).toAbsolutePath();
		}
		if(tipo == "perfil") {
			resource = Paths.get(UPLOADS_FOLDER_PERFIL).resolve(filename).toAbsolutePath();
		}
		return resource;
	}

}
