package safbuilder.metadataextractor.featureextractors;

import java.io.File;
import java.io.IOException;

public class StaticTransparentExtractor<T> extends FeatureExtractor<T>{

	private T value = null;

	public StaticTransparentExtractor(){

	}

	public StaticTransparentExtractor(T value){
		this.value = value;
	}

	public void setValue(T value) {
		this.value = value;
	}

	@Override
	protected T extractFromFile(File file) throws IOException {
		if(value == null)
			throw new NullPointerException("Value not set.");
		return value;
	}
}
