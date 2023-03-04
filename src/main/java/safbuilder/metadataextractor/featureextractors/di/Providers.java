package safbuilder.metadataextractor.featureextractors.di;

import com.google.inject.Provides;
import com.google.inject.Singleton;
import org.apache.pdfbox.text.PDFTextStripper;

import java.io.IOException;

public class Providers {

	@Provides
	@Singleton
	public static PDFTextStripper pdfTextStripper() throws IOException {
		return new PDFTextStripper();
	}


}
