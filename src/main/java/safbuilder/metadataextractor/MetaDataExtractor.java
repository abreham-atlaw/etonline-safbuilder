package safbuilder.metadataextractor;

import com.csvreader.CsvWriter;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDDocumentInformation;
import org.apache.pdfbox.text.PDFTextStripper;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;


public class MetaDataExtractor {

	private static final String CSV_FILENAME = "metadata.csv";
	private final SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
	private PDFTextStripper textStripper = null;

	private List<File> getPDFFiles(File directory){
		return (List<File>) Arrays.stream(Objects.requireNonNull(directory.listFiles())).filter(file -> { return file.getName().endsWith("pdf"); }).collect(Collectors.toList());

	}

	private PDFTextStripper getTextStripper() throws IOException{
		if(textStripper == null)
			textStripper = new PDFTextStripper();
		return textStripper;
	}

	public File extractAndSave(File directory, String subject) throws IOException {

		Path csvPath = Paths.get(directory.getPath(), CSV_FILENAME).toAbsolutePath();

		List<File> files = getPDFFiles(directory);

		OutputStream outputStream = Files.newOutputStream(csvPath);
		CsvWriter writer = new CsvWriter(outputStream, ',', Charset.defaultCharset());
		writer.writeRecord(new String[]{
				"filename",
				"dc.identifier",
				"dc.title",
				"dc.contributor",
				"dc.subject",
				"dc.date.issued"
		});

		for(File file: files){
			writer.writeRecord(constructRow(file, subject));
		}
		writer.endRecord();
		writer.close();
		outputStream.close();
		return new File(csvPath.toUri());
	}

	private String extractDate(Calendar calendar){
		if(calendar == null)
			return "";
		return dateFormat.format(calendar.getTime());
	}

	private String extractISBN(File file) throws IOException{
//		PDDocument document = PDDocument.load(file);
//
//		String content = getTextStripper().getText(document);
//		int index = content.indexOf("ISSN");
//
//		document.close();
		return "";

	}


	private String[] constructRow(File file, String subject) throws IOException {
		PDDocumentInformation metaData = extractMetaData(file);

//		{
//			"filename",
//					"dc.identifier",
//					"dc.title",
//					"dc.contributor",
//					"dc.subject",
//					"dc.date.issued"
//		}

		return new String[]{
				file.getName(),
				extractISBN(file),
				metaData.getTitle(),
				metaData.getAuthor(),
				subject,
				extractDate(metaData.getCreationDate()),
		};
	}

	private PDDocumentInformation extractMetaData(File file) throws IOException {
		PDDocument document = PDDocument.load(file);
		PDDocumentInformation metaData = document.getDocumentInformation();
		document.close();
		return metaData;
	}

}
