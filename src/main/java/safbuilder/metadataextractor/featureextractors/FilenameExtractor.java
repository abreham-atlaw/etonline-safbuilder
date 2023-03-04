package safbuilder.metadataextractor.featureextractors;

import java.io.File;

public class FilenameExtractor extends FeatureExtractor<String>{

	@Override
	protected String extractFromFile(File file) {
		return file.getName();
	}

}
