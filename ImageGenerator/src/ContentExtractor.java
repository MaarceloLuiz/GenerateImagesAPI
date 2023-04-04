import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ContentExtractor {
	
	public List<Content> contentExtractor (String json){
		
		JsonParser parser = new JsonParser();
		List<Map<String, String>> atributeList = parser.parse(json);
			
		List<Content> contents = new ArrayList<>();
		
		for (Map<String, String> attributes : atributeList) {
			String title = attributes.get("title");
			String imageURL = attributes.get("image").replaceAll("(@+)(.*).jpg$", "$1.jpg");
			
			var content = new Content(title, imageURL);
			
			contents.add(content);
		}
		
		return contents;
	}
}
