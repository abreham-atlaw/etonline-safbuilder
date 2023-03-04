package safbuilder.metadataextractor.featureextractors.pdfmetainfoextractors;

import org.apache.pdfbox.pdmodel.PDDocumentInformation;

public class AuthorExtractor extends PDFMetaInfoExtractor<String>{

	@Override
	public String extractMetaInfo(PDDocumentInformation metaData) {
		return metaData.getAuthor();
	}
}
