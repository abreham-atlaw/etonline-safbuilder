package safbuilder.metadataextractor.featureextractors;

import java.io.File;
import java.io.IOException;

public abstract class FeatureExtractor<T>{

	public String format(T value){
		if(value == null)
			return "";
		return value.toString();
	}

	protected abstract T extractFromFile(File file) throws IOException;

	public String extract(File file) throws IOException{
		return format(extractFromFile(file));
	}

}
