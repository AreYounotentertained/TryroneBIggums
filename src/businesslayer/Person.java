package businesslayer;

public class Person {
	private Integer id;
	private String nickName;
	private String comment;

	public Person(Integer id, String nickName, String comment) {
		this.id = id;
		this.nickName = nickName;
		this.comment = comment;
	}

	public Person(String nickName, String comment) {
		this.nickName = nickName;
		this.comment = comment;
	}

	public Person() {
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	@Override
	public String toString() {
		return "ID:" + id +
				"\nNickName:" + nickName +
				"\nComment:" + comment;
	}
}