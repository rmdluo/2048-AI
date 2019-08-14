package ai2048;

public enum UserAction {
	LEFT("left"),
	RIGHT("right"),
	UP("up"),
	DOWN("down");
	
	private String move;

	private UserAction(String move) {
		this.move = move;
	}
	
	public String toString() {
		return move;
	}
}
