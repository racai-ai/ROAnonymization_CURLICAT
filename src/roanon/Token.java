package roanon;

public class Token {

	private String sep;
	private String tok;
	
	// A token is represented by a separator string (previous to the token) and the actual token string
	public Token(String sep,String tok) {
		this.sep=sep;
		this.tok=tok;
	}

	/**
	 * @return the sep
	 */
	public String getSep() {
		return sep;
	}

	/**
	 * @param sep the sep to set
	 */
	public void setSep(String sep) {
		this.sep = sep;
	}

	/**
	 * @return the tok
	 */
	public String getTok() {
		return tok;
	}

	/**
	 * @param tok the tok to set
	 */
	public void setTok(String tok) {
		this.tok = tok;
	}
	
}
