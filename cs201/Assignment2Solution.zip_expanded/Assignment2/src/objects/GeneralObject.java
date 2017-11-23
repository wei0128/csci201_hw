package objects;

//deliverables and labs extend this class
abstract public class GeneralObject {
	protected Integer number;
	protected String title;
	protected String url;

	public Integer getNumber() {
		return number;
	}

	public String getTitle() {
		return title;
	}

	public String getUrl() {
		return url;
	}
}
