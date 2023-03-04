package safbuilder.metadataextractor.featureextractors.phraseextractors;

import org.javatuples.Pair;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


public abstract class IdentifierExtractor extends CharacterCountExtractor{

	private final static Map<String, String> SUBSTITUTION_MAP = new HashMap<String, String>(){{
		put("-", " ");
	}};

	public IdentifierExtractor(String priorPhrase, int size) {
		super(priorPhrase, size);
	}

	@Override
	public String format(String value) {
		if(value == null)
			return "";
		for(String character: SUBSTITUTION_MAP.keySet()){
			value = value.replace(character, SUBSTITUTION_MAP.get(character));
		}
		return value;
	}

	protected abstract List<Pair<Integer, Integer>> getNumberIndices();

	protected abstract List<Integer> getHyphenIndices();

	private boolean isNumber(String value){
		try{
			Integer.parseInt(value);
			return true;
		}
		catch(NumberFormatException ex) {
			return false;
		}
	}

	private boolean validateNumbers(String value){
		for(Pair<Integer, Integer> indice: getNumberIndices()){
			if(!isNumber(value.substring(indice.getValue0(), indice.getValue1())))
				return false;
		}
		return true;
	}

	private boolean validateHyphens(String value){
		for(int index: getHyphenIndices()){
			if(value.charAt(index) != '-')
				return false;
		}
		return true;
	}
	@Override
	protected boolean isValid(String output) {
		if(output == null)
			return false;
		return validateHyphens(output) && validateNumbers(output);

	}
}
