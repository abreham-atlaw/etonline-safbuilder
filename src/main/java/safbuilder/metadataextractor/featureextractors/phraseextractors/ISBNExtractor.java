package safbuilder.metadataextractor.featureextractors.phraseextractors;

import org.javatuples.Pair;

import java.util.Arrays;
import java.util.List;

public class ISBNExtractor extends IdentifierExtractor{

	public ISBNExtractor() {
		super("ISBN: ", 13);
	}

	@Override
	protected List<Pair<Integer, Integer>> getNumberIndices() {
		return Arrays.asList(
				Pair.with(0, 1),
				Pair.with(2, 5),
				Pair.with(6, 11),
				Pair.with(12, 13)
		);
	}

	@Override
	protected List<Integer> getHyphenIndices() {
		return Arrays.asList(
				1, 5, 11
		);
	}
}
