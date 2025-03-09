package pt.ulisboa.tecnico.rnl.dei.dms.exceptions;

public enum ErrorMessage {

	NO_SUCH_PERSON("Não existe nenhuma pessoa com o ID %s", 1001),
	PERSON_ALREADY_EXISTS("Já existe uma pessoa com o ID %s", 1002),
	PERSON_NAME_NOT_VALID("O nome da pessoa especificado não é válido.", 1003),
	IST_ID_NOT_VALID("O IST ID especificado não é válido.", 1004),
	DUPLICATE_IST_ID("Já existe uma pessoa com o IST ID %s", 1005),
	EMAIL_NOT_VALID("O email especificado não é válido.", 1006),
	DUPLICATE_EMAIL("Já existe uma pessoa com o email %s", 1007),
	PHONE_NUMBER_NOT_VALID("O número de telefone especificado não é válido.", 1008),
	DUPLICATE_PHONE_NUMBER("Já existe uma pessoa com o número de telefone %s", 1009);

	private final String label;
	private final int code;

	ErrorMessage(String label, int code) {
		this.label = label;
		this.code = code;
	}

	public String getLabel() {
		return this.label;
	}

	public int getCode() {
		return this.code;
	}
}
