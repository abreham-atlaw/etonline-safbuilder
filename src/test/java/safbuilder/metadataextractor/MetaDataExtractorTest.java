package safbuilder.metadataextractor;

import junit.framework.TestCase;

import java.io.File;
import java.io.IOException;

public class MetaDataExtractorTest extends TestCase {

	public MetaDataExtractorTest(String testName){
		super(testName);
	}

	public void testFunctionality() throws IOException {
		MetaDataExtractor extractor = new MetaDataExtractor();

		extractor.extractAndSave(new File("/home/abreham/Projects/ETCollege-Projects/DSpace/etonline-safbuilder/tmp/sample-data/1/"), "Computer Science");
	}


}
