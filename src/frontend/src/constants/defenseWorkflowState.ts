export const defenseWorkflowStateColors: Record<string, string> = {
    NONE: 'gray',
    DEFESA_AGENDADA: 'blue',
    EM_REVISAO: 'orange',
    SUBMETIDO_AO_FENIX: 'red'
}

export const defenseWorkflowStateLabels: Record<string, string> = {
    NONE: 'Nenhum',
    DEFESA_AGENDADA: 'Defesa Agendada',
    EM_REVISAO: 'Em Revisão',
    SUBMETIDO_AO_FENIX: 'Submetido ao Fenix'
}

export const defenseWorkflowStateOptions = Object.keys(defenseWorkflowStateLabels).map(key => ({
    title: defenseWorkflowStateLabels[key],
    value: key
}))