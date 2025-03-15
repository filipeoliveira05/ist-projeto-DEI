export const defenseWorkflowStateOrder: Record<string, number> = {
    NONE: 0,
    DEFESA_AGENDADA: 1,
    EM_REVISAO: 2,
    SUBMETIDO_AO_FENIX: 3
}

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