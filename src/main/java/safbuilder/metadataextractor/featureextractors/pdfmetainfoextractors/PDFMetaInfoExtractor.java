package safbuilder.metadataextractor.featureextractors.pdfmetainfoextractors;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDDocumentInformation;
import safbuilder.metadataextractor.featureextractors.FeatureExtractor;

import java.io.File;
import java.io.IOException;

public abstract class PDFMetaInfoExtractor<T> extends FeatureExtractor<T> {

	public abstract T extractMetaInfo(PDDocumentInformation metaData);

	@Override
	protected T extractFromFile(File file) throws IOException {

		PDDocument document = PDDocument.load(file);
		PDDocumentInformation metaData = document.getDocumentInformation();
		document.close();

		return extractMetaInfo(metaData);
	}
}
