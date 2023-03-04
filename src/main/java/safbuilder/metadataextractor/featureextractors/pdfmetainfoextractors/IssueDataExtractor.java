package safbuilder.metadataextractor.featureextractors.pdfmetainfoextractors;

import org.apache.pdfbox.pdmodel.PDDocumentInformation;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class IssueDataExtractor extends PDFMetaInfoExtractor<Date>{

	private final SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");;

	@Override
	public Date extractMetaInfo(PDDocumentInformation metaData) {
		Calendar calendar = metaData.getCreationDate();
		if(calendar == null)
			return null;
		return calendar.getTime();
	}

	@Override
	public String format(Date value) {
		if(value == null)
			return "";
		return dateFormat.format(value);
	}

}
