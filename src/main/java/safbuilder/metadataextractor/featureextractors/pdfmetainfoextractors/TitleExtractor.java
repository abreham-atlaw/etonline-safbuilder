package safbuilder.metadataextractor.featureextractors.pdfmetainfoextractors;

import org.apache.pdfbox.pdmodel.PDDocumentInformation;

import java.util.HashMap;
import java.util.Map;

public class TitleExtractor extends PDFMetaInfoExtractor<String>{

	private final static Map<String, String> SUBSTITUTION_MAP = new HashMap<String, String>(){{

		put("&", "and");

		// REPLACED WITH SPACE
		put("-", " ");
		put("_", " ");
		put("(", " ");

		// REMOVED
		put(",", "");
		put(":", "");
		put(")", "");
		put("%", "");
		put("#", "");
		put("@", "");

	}};

	@Override
	public String format(String value) {
		if(value == null)
			return "";
		for(String character: SUBSTITUTION_MAP.keySet()){
			value = value.replace(character, SUBSTITUTION_MAP.get(character));
		}
		return value;
	}

	@Override
	public String extractMetaInfo(PDDocumentInformation metaData) {
		return metaData.getTitle();
	}

}
