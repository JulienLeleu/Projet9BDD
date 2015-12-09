package mvc;

public class Message {

	private int i;

	public Message() {}
	
	public String toString() {
		return String.format("C'est le %dème Hello World", ++i);
	}

}
