export const thesisWorkflowStateOrder: Record<string, number> = {
  NONE: 0,
  PROPOSTA_JURI_SUBMETIDA: 1,
  APROVADO_PELO_SC: 2,
  PRESIDENTE_JURI_ATRIBUIDO: 3,
  DOCUMENTO_ASSINADO: 4,
  SUBMETIDO_AO_FENIX: 5
}

export const thesisWorkflowStateColors: Record<string, string> = {
  NONE: 'gray',
  PROPOSTA_JURI_SUBMETIDA: 'blue',
  APROVADO_PELO_SC: 'green',
  PRESIDENTE_JURI_ATRIBUIDO: 'orange',
  DOCUMENTO_ASSINADO: 'purple',
  SUBMETIDO_AO_FENIX: 'red'
}

export const thesisWorkflowStateLabels: Record<string, string> = {
  NONE: 'Nenhum',
  PROPOSTA_JURI_SUBMETIDA: 'Proposta de Júri Submetida',
  APROVADO_PELO_SC: 'Aprovado pelo SC',
  PRESIDENTE_JURI_ATRIBUIDO: 'Presidente do Júri Atribuído',
  DOCUMENTO_ASSINADO: 'Documento Assinado',
  SUBMETIDO_AO_FENIX: 'Submetido ao Fenix'
}

export const thesisWorkflowStateOptions = Object.keys(thesisWorkflowStateLabels).map(key => ({
  title: thesisWorkflowStateLabels[key],
  value: key
}))