package alstudio.alstudiolib.module.xmpp.core.packact;

public class Presence {

	private PresenceType type = PresenceType.available;
	private String status = null;
	private int priority = Integer.MIN_VALUE;
	private Mode mode = null;
	private String language;
	private String from;

	/**
	 * Creates a new presence update. Status, priority, and mode are left
	 * un-set.
	 * 
	 * @param type
	 *            the type.
	 */
	public Presence(PresenceType type) {
		setType(type);
	}

	/**
	 * Creates a new presence update with a specified status, priority, and
	 * mode.
	 * 
	 * @param type
	 *            the type.
	 * @param status
	 *            a text message describing the presence update.
	 * @param priority
	 *            the priority of this presence update.
	 * @param mode
	 *            the mode type for this presence update.
	 */
	public Presence(PresenceType type, String status, int priority, Mode mode) {
		setType(type);
		setStatus(status);
		setPriority(priority);
		setMode(mode);
	}

	/**
	 * Returns true if the {@link Type presence type} is available (online) and
	 * false if the user is unavailable (offline), or if this is a presence
	 * packet involved in a subscription operation. This is a convenience method
	 * equivalent to <tt>getType() == Presence.Type.available</tt>. Note that
	 * even when the user is available, their presence mode may be
	 * {@link alstudio.alstudiolib.module.xmpp.core.packact.Presence.Mode#away away}, {@link alstudio.alstudiolib.module.xmpp.core.packact.Presence.Mode#xa extended away} or {@link alstudio.alstudiolib.module.xmpp.core.packact.Presence.Mode#dnd
	 * do not disturb}. Use {@link #isAway()} to determine if the user is away.
	 * 
	 * @return true if the presence type is available.
	 */
	public boolean isAvailable() {
		return type == PresenceType.available;
	}

	/**
	 * Returns true if the presence type is {@link Type#available available} and
	 * the presence mode is {@link alstudio.alstudiolib.module.xmpp.core.packact.Presence.Mode#away away}, {@link alstudio.alstudiolib.module.xmpp.core.packact.Presence.Mode#xa extended
	 * away}, or {@link alstudio.alstudiolib.module.xmpp.core.packact.Presence.Mode#dnd do not disturb}. False will be returned when
	 * the type or mode is any other value, including when the presence type is
	 * unavailable (offline). This is a convenience method equivalent to
	 * <tt>type == Type.available && (mode == Mode.away || mode == Mode.xa || mode == Mode.dnd)</tt>
	 * .
	 * 
	 * @return true if the presence type is available and the presence mode is
	 *         away, xa, or dnd.
	 */
	public boolean isAway() {
		return type == PresenceType.available && (mode == Mode.away || // mode ==
																// Mode.xa ||
				mode == Mode.dnd);
	}

	/**
	 * Returns the type of this presence packet.
	 * 
	 * @return the type of the presence packet.
	 */
	public PresenceType getType() {
		return type;
	}

	/**
	 * Sets the type of the presence packet.
	 * 
	 * @param type
	 *            the type of the presence packet.
	 */
	public void setType(PresenceType type) {
		if (type == null) {
			throw new NullPointerException("Type cannot be null");
		}
		this.type = type;
	}

	/**
	 * Returns the status message of the presence update, or <tt>null</tt> if
	 * there is not a status. The status is free-form text describing a user's
	 * presence (i.e., "gone to lunch").
	 * 
	 * @return the status message.
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * Sets the status message of the presence update. The status is free-form
	 * text describing a user's presence (i.e., "gone to lunch").
	 * 
	 * @param status
	 *            the status message.
	 */
	public void setStatus(String status) {
		this.status = status;
	}

	/**
	 * Returns the priority of the presence, or Integer.MIN_VALUE if no priority
	 * has been set.
	 * 
	 * @return the priority.
	 */
	public int getPriority() {
		return priority;
	}

	/**
	 * Sets the priority of the presence. The valid range is -128 through 128.
	 * 
	 * @param priority
	 *            the priority of the presence.
	 * @throws IllegalArgumentException
	 *             if the priority is outside the valid range.
	 */
	public void setPriority(int priority) {
		if (priority < -128 || priority > 128) {
			throw new IllegalArgumentException("Priority value " + priority
					+ " is not valid. Valid range is -128 through 128.");
		}
		this.priority = priority;
	}

	/**
	 * Returns the mode of the presence update, or <tt>null</tt> if the mode is
	 * not set. A null presence mode value is interpreted to be the same thing
	 * as {@link alstudio.alstudiolib.module.xmpp.core.packact.Presence.Mode#available}.
	 * 
	 * @return the mode.
	 */
	public Mode getMode() {

		return mode;
	}

	/**
	 * Sets the mode of the presence update. A null presence mode value is
	 * interpreted to be the same thing as {@link alstudio.alstudiolib.module.xmpp.core.packact.Presence.Mode#available}.
	 * 
	 * @param mode
	 *            the mode.
	 */
	public void setMode(Mode mode) {
		this.mode = mode;
	}

	/**
	 * Returns the xml:lang of this Presence, or null if one has not been set.
	 * 
	 * @return the xml:lang of this Presence, or null if one has not been set.
	 * @since 3.0.2
	 */
	public String getLanguage() {
		return language;
	}

	/**
	 * Sets the xml:lang of this Presence.
	 * 
	 * @param language
	 *            the xml:lang of this Presence.
	 * @since 3.0.2
	 */
	public void setLanguage(String language) {
		this.language = language;
	}
	
	public void setFrom(String from) {
		this.from = from;
	}
	
	public String getFrom() {
		return from;
	}

	/**
	 * A enum to represent the presecence type. Not that presence type is often
	 * confused with presence mode. Generally, if a user is signed into a
	 * server, they have a presence type of {@link #available available}, even
	 * if the mode is {@link alstudio.alstudiolib.module.xmpp.core.packact.Presence.Mode#away away}, {@link alstudio.alstudiolib.module.xmpp.core.packact.Presence.Mode#dnd dnd}, etc. The
	 * presence type is only {@link #unavailable unavailable} when the user is
	 * signing out of the server.
	 */
	public enum PresenceType {

		/**
		 * The user is available to receive messages (default).
		 */
		available,

		/**
		 * The user is unavailable to receive messages.
		 */
		unavailable,

		/**
		 * Request subscription to recipient's presence.
		 */
		subscribe,

		/**
		 * Grant subscription to sender's presence.
		 */
		subscribed,

		/**
		 * Request removal of subscription to sender's presence.
		 */
		unsubscribe,

		/**
		 * Grant removal of subscription to sender's presence.
		 */
		unsubscribed,

		/**
		 * The presence packet contains an error message.
		 */
		error
	}

	/**
	 * An enum to represent the presence mode.
	 */
	public enum Mode {

		// /**
		// * Free to chat.
		// */
		// chat,
		//
		// /**
		// * Available (the default).
		// */
		// available,
		//
		// /**
		// * Away.
		// */
		// away,
		//
		// /**
		// * Away for an extended period of time.
		// */
		// xa,
		//
		// /**
		// * Do not disturb.
		// */
		// dnd

		// user-defined
		available, // default

		online, // 在线

		invisable, // 隐身

		away, // 离开

		dnd
		// 忙碌

	}
}