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