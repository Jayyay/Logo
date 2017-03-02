package controller;

public enum LanguageType {
	CHINESE ("Chinese"),
	ENGLISH ("English"),
	FRENCH ("French"),
    GERMAN ("German"),
    ITALIAN ("Italian"),
	PORTUGUESE ("Portuguese"),
	RUSSIAN ("Russian"),
	SPANISH ("Spanish");
	
	private final String myTypeName;
	
	LanguageType(String typeName) {
		myTypeName = typeName;
	}
	
	@Override
	public String toString() {
		return myTypeName;
	}
}
