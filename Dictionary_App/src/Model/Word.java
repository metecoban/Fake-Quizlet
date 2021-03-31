package Model;

public class Word {
	private int sectionID;
	private String word1;
	private String word2;

	public Word() {
	}

	public Word(int sectionID, String word1, String word2) {
		this.sectionID = sectionID;
		this.word1 = word1;
		this.word2 = word2;
	}

	public int getSectionID() {
		return sectionID;
	}

	public void setSectionID(int sectionID) {
		this.sectionID = sectionID;
	}

	public String getWord1() {
		return word1;
	}

	public void setWord1(String word1) {
		this.word1 = word1;
	}

	public String getWord2() {
		return word2;
	}

	public void setWord2(String word2) {
		this.word2 = word2;
	}

}
