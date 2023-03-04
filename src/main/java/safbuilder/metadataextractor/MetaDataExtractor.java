package safbuilder.metadataextractor;

import com.csvreader.CsvWriter;
import safbuilder.metadataextractor.featureextractors.*;
import safbuilder.metadataextractor.featureextractors.pdfmetainfoextractors.AuthorExtractor;
import safbuilder.metadataextractor.featureextractors.pdfmetainfoextractors.IssueDataExtractor;
import safbuilder.metadataextractor.featureextractors.pdfmetainfoextractors.TitleExtractor;
import safbuilder.metadataextractor.featureextractors.phraseextractors.ISBNExtractor;
import safbuilder.metadataextractor.featureextractors.phraseextractors.ISSNExtractor;
import safbuilder.metadataextractor.featureextractors.phraseextractors.PublisherExtractor;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;


public class MetaDataExtractor {

	private static class COLUMNS{
		public static final String FILENAME = "filename";
		public static final String ISSN = "dc.identifier.issn";
		public static final String ISBN = "dc.identifier.isbn";
		public static final String TITLE = "dc.title";
		public static final String AUTHOR = "dc.contributor";
		public static final String PUBLISHER = "dc.publisher";
		public static final String SUBJECT = "dc.subject";
		public static final String DATE_ISSUED = "dc.date.issued";

	}

	private static final String CSV_FILENAME = "metadata.csv";
	private final List<String> columns;
	private final Map<String, FeatureExtractor<?>> featureExtractors;

	public MetaDataExtractor(){
		columns = onCreateColumns();
		featureExtractors = onCreateExtractors();

		if(columns.size() != featureExtractors.size())
			throw new RuntimeException("features extractors size and columns size dont match");
	}

	protected List<String> onCreateColumns(){
		return Arrays.asList(
				COLUMNS.FILENAME,
				COLUMNS.ISSN,
				COLUMNS.ISBN,
				COLUMNS.TITLE,
				COLUMNS.AUTHOR,
				COLUMNS.PUBLISHER,
				COLUMNS.SUBJECT,
				COLUMNS.DATE_ISSUED
		);
	}

	protected Map<String, FeatureExtractor<?>> onCreateExtractors(){
		return new HashMap<String, FeatureExtractor<?>>(){{
			put(COLUMNS.FILENAME, new FilenameExtractor());
			put(COLUMNS.ISSN, new ISSNExtractor());
			put(COLUMNS.ISBN, new ISBNExtractor());
			put(COLUMNS.TITLE, new TitleExtractor());
			put(COLUMNS.AUTHOR, new AuthorExtractor());
			put(COLUMNS.PUBLISHER, new PublisherExtractor());
			put(COLUMNS.SUBJECT, new SubjectExtractor());
			put(COLUMNS.DATE_ISSUED, new IssueDataExtractor());
		}};
	}



	private List<File> getPDFFiles(File directory){
		return (List<File>) Arrays.stream(Objects.requireNonNull(directory.listFiles())).filter(file -> { return file.getName().endsWith("pdf"); }).collect(Collectors.toList());
	}

	private void writeColumnTitles(CsvWriter writer) throws IOException {
		String[] columnsArray = new String[columns.size()];
		columns.toArray(columnsArray);
		writer.writeRecord(columnsArray);
	}

	public File extractAndSave(File directory, String subject) throws IOException {

		Path csvPath = Paths.get(directory.getPath(), CSV_FILENAME).toAbsolutePath();

		List<File> files = getPDFFiles(directory);

		OutputStream outputStream = Files.newOutputStream(csvPath);
		CsvWriter writer = new CsvWriter(outputStream, ',', Charset.defaultCharset());

		((SubjectExtractor)(featureExtractors.get("dc.subject"))).setValue(subject);

		writeColumnTitles(writer);

		for(File file: files){
			writer.writeRecord(constructRow(file));
		}
		writer.endRecord();
		writer.close();
		outputStream.close();
		return new File(csvPath.toUri());
	}

	private String[] constructRow(File file) throws IOException {
		String[] row = new String[columns.size()];
		for(int i=0; i< columns.size(); i++){
			row[i] = featureExtractors.get(columns.get(i)).extract(file);
		}
		return row;
	}

}
