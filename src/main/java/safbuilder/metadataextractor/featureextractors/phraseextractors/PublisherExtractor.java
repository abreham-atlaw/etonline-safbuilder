package safbuilder.metadataextractor.featureextractors.phraseextractors;

import java.io.File;
import java.io.IOException;

public class PublisherExtractor extends PosteriorStringMatcherExtractor{

	public PublisherExtractor() {
		super("Published by", "\n");
	}

}
