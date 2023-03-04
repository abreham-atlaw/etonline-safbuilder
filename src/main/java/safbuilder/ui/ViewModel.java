package safbuilder.ui;

import safbuilder.SAFPackage;
import safbuilder.metadataextractor.MetaDataExtractor;

import java.io.File;
import java.io.IOException;

public class ViewModel {



	public void generate(File directory, String subject, ProgressState state){
		GenerationThread thread = new GenerationThread(directory, subject, state);
		thread.start();
	}

	private static class GenerationThread extends Thread{

		private final File directory;
		private final String subject;
		private final ProgressState state;


		GenerationThread(File directory, String subject, ProgressState state){
			super();
			this.directory = directory;
			this.subject = subject;
			this.state = state;
		}

		@Override
		public void run() {
			state.setStatus(ProgressState.Status.INITIATING);
			SAFPackage safPackageInstance = new SAFPackage();
			MetaDataExtractor extractor = new MetaDataExtractor();
			try {
				state.setStatus(ProgressState.Status.EXTRACTING);
				File csvFile = extractor.extractAndSave(directory, subject);
				state.setStatus(ProgressState.Status.GENERATING);
				safPackageInstance.processMetaPack(csvFile.getPath(), true);
				state.setStatus(ProgressState.Status.DONE);
			}
			catch (IOException ex){
				state.setStatus(ProgressState.Status.FAILED);
			}
		}
	}

}
