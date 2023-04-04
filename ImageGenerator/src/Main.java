import java.io.InputStream;
import java.io.File;
import java.net.URL;
import java.util.List;

public class Main {

	public static void main(String[] args) throws Exception {
		//String url = "https://generating-images.fly.dev/generating"; // previous created API
		String url = "https://imdb-api.com/en/API/Top250Movies/k_h5fo1lzy"; // IMDB API
		ContentExtractor extrator = new ContentExtractor();
		
		var http = new ClienteHttp();	
		String json = http.buscaDados(url);
			
		List<Content> contents = extrator.contentExtractor(json);
		
		var generator = new ImageGenerator();
		
		for(int i = 0; i<10; i++) {
			
			Content content = contents.get(i);
			InputStream inputStream = new URL(content.getImageURL()).openStream();
			
			String fileName = content.getTitle() + ".png";
			
			generator.create(inputStream, fileName, content.getTitle());
			
			System.out.println(content.getTitle());
			System.out.println();
		}
		
	}

}
