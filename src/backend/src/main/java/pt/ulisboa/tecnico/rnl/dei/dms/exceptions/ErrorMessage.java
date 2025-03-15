package pt.ulisboa.tecnico.rnl.dei.dms.exceptions;

public enum ErrorMessage {

	NO_SUCH_PERSON("Não existe nenhuma pessoa com o ID %s", 1001),
	PERSON_ALREADY_EXISTS("Já existe uma pessoa com o ID %s", 1002),
	PERSON_NAME_NOT_VALID("O nome da pessoa especificado não é válido.", 1003),
	IST_ID_NOT_VALID("O IST ID especificado não é válido.", 1004),
	DUPLICATE_IST_ID("Já existe uma pessoa com o IST ID %s", 1005),
	PERSON_TYPE_NOT_VALID("O tipo de pessoa especificado não é válido: %s", 1006),
	EMAIL_NOT_VALID("O email especificado não é válido.", 1007),
	DUPLICATE_EMAIL("Já existe uma pessoa com o email %s", 1008),
	PHONE_NUMBER_NOT_VALID("O número de telefone especificado não é válido.", 1009),
	DUPLICATE_PHONE_NUMBER("Já existe uma pessoa com o número de telefone %s", 1010),
	INVALID_WORKFLOW_STATE("O estado do fluxo de trabalho especificado não é válido: %s", 1011),
	PERSON_NOT_A_STUDENT("A pessoa com o ID %s não é um estudante", 1012);

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
