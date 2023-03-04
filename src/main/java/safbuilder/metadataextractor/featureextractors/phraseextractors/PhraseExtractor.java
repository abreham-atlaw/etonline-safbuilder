package safbuilder.metadataextractor.featureextractors.phraseextractors;

import com.google.inject.Inject;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import safbuilder.metadataextractor.featureextractors.FeatureExtractor;

import java.io.File;
import java.io.IOException;

public abstract class PhraseExtractor extends FeatureExtractor<String> {

	private PDFTextStripper textStripper;

	private final String priorPhrase;

	public PhraseExtractor(String priorPhrase){
		this.priorPhrase = priorPhrase;
	}

	private PDFTextStripper getTextStripper() throws IOException {
		if(textStripper == null)
			textStripper = new PDFTextStripper();
		return textStripper;
	}

	private String extractFromString(String content){
		int index = content.indexOf(priorPhrase);
		if(index == -1)
			return null;
		return cutPhrase(content.substring(index+priorPhrase.length()));
	}

	protected abstract String cutPhrase(String phrase);

	@Override
	protected String extractFromFile(File file) throws IOException {
		PDDocument document = PDDocument.load(file);
		String content = getTextStripper().getText(document);
		document.close();

		String output = extractFromString(content);
		if(isValid(output))
			return output;
		return null;
	}

	protected boolean isValid(String output){
		return true;
	}

}
