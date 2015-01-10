package alstudio.alstudiolib.module.xmpp.core.packact;

public class ALIQ {

	private ALIQType type;
	private String id;
	private String from;
	private String to;
	private String ns;

	public ALIQ() {
	}

	public void setType(ALIQType type) {
		this.type = type;
	}

	public ALIQType getType() {
		return this.type;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getId() {
		return this.id;
	}

	public void setFrom(String from) {
		this.from = from;
	}

	public String getFrom() {
		return this.from;
	}

	public void setTo(String to) {
		this.to = to;
	}

	public String getTo() {
		return this.to;
	}

	public void setNameSpace(String ns) {
		this.ns = ns;
	}

	public String getNameSpace() {
		return this.ns;
	}

}
