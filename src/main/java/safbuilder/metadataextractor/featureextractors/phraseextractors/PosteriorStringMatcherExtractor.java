package safbuilder.metadataextractor.featureextractors.phraseextractors;

public class PosteriorStringMatcherExtractor extends PhraseExtractor{

	private String posteriorPhrase;

	public PosteriorStringMatcherExtractor(String priorPhrase, String posteriorPhrase) {
		super(priorPhrase);
		this.posteriorPhrase = posteriorPhrase;
	}

	@Override
	protected String cutPhrase(String phrase) {
		int index = phrase.indexOf(posteriorPhrase);
		return phrase.substring(0, index);
	}

}
