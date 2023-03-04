package safbuilder.metadataextractor.featureextractors.phraseextractors;

public class CharacterCountExtractor extends PhraseExtractor{

	private final int size;

	public CharacterCountExtractor(String priorPhrase, int size){
		super(priorPhrase);
		this.size = size;
	}

	@Override
	protected String cutPhrase(String phrase) {
		return phrase.substring(0, size);
	}


}
