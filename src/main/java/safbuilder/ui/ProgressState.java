package safbuilder.ui;

import javafx.beans.value.ObservableValue;

public class ProgressState {

	private Status status;
	public StateListener listener = null;

	public ProgressState(){
		this(Status.None);
	}

	public ProgressState(Status status){
		this.status = status;
	}

	public void setListener(StateListener listener) {
		this.listener = listener;
	}

	public void setStatus(Status status) {
		this.status = status;
		if(listener != null)
			listener.onStatus(status);
	}

	public enum Status{
		None,
		INITIATING,
		EXTRACTING,
		GENERATING,

		DONE,
		FAILED
	}

	public interface StateListener{

		public void onStatus(Status status);

	}
}
