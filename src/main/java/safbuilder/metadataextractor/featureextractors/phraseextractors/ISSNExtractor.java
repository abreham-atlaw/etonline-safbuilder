package safbuilder.metadataextractor.featureextractors.phraseextractors;

import org.javatuples.Pair;

import java.text.ParseException;
import java.util.Arrays;
import java.util.List;

public class ISSNExtractor extends IdentifierExtractor{


	public ISSNExtractor() {
		super("ISSN: ", 9);
	}

	@Override
	protected List<Pair<Integer, Integer>> getNumberIndices() {
		return Arrays.asList(
				Pair.with(0, 4),
				Pair.with(5, 9)
		);
	}

	@Override
	protected List<Integer> getHyphenIndices() {
		return Arrays.asList(
				4
		);
	}
}
