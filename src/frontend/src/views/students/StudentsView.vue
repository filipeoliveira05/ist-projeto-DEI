<template>
  <v-container>
    <v-row align="center">
      <v-col>
        <h2 class="text-left ml-1">Listagem de Alunos</h2>
      </v-col>
    </v-row>

    <v-text-field
      v-model="search"
      label="Pesquisar"
      prepend-inner-icon="mdi-magnify"
      variant="outlined"
      hide-details
      single-line
    ></v-text-field>

    <v-select
      v-model="selectedThesisWorkflowState"
      :items="thesisWorkflowStateOptions"
      label="Filtrar por estado do workflow de tese"
      variant="outlined"
      hide-details
      clearable
    ></v-select>

    <v-select
      v-model="selectedDefenseWorkflowState"
      :items="defenseWorkflowStateOptions"
      label="Filtrar por estado do workflow de defesa"
      variant="outlined"
      hide-details
      clearable
    ></v-select>

    <v-data-table
      :headers="headers"
      :items="filteredStudents"
      :search="search"
      :loading="loading"
      :custom-filter="fuzzySearch"
      item-key="id"
      class="text-left"
      no-data-text="Sem alunos a apresentar."
    >
      <template v-slot:[`item.name`]="{ item }">
        <router-link :to="`/students/${item.id}`" class="student-link">
          {{ item.name }}
        </router-link>
      </template>

      <template v-slot:[`item.thesisWorkflowState`]="{ item }">
        <v-chip :color="thesisWorkflowStateColors[item.thesisWorkflowState] || 'gray'" text-color="white">
          {{ thesisWorkflowStateLabels[item.thesisWorkflowState] || 'Desconhecido' }}
        </v-chip>
      </template>

      <template v-slot:[`item.defenseWorkflowState`]="{ item }">
        <v-chip :color="defenseWorkflowStateColors[item.defenseWorkflowState] || 'gray'" text-color="white">
          {{ defenseWorkflowStateLabels[item.defenseWorkflowState] || 'Desconhecido' }}
        </v-chip>
      </template>

    </v-data-table>
  </v-container>

  <router-view @studentUpdated="handleStudentUpdated"></router-view>
</template>


<script setup lang="ts">
import { reactive, ref, computed } from 'vue'
import { thesisWorkflowStateOrder, thesisWorkflowStateColors, thesisWorkflowStateLabels, thesisWorkflowStateOptions } from '../../constants/thesisWorkflowState'
import { defenseWorkflowStateOrder, defenseWorkflowStateColors, defenseWorkflowStateLabels, defenseWorkflowStateOptions } from '../../constants/defenseWorkflowState'
import RemoteService from '../../services/RemoteService'
import type StudentDto from '../../models/StudentDto'

const search = ref('')
const loading = ref(true)
const students: StudentDto[] = reactive([])
const selectedThesisWorkflowState = ref(null)
const selectedDefenseWorkflowState = ref(null)

const headers = [
  { title: 'Nome', key: 'name', sortable: true },
  { title: 'IST ID', key: 'istId', sortable: true },
  { title: 'Email', key: 'email', sortable: true },
  { title: 'Estado da Tese', key: 'thesisWorkflowState', sortable: true },
  { title: 'Estado da Defesa', key: 'defenseWorkflowState', sortable: true }
]


async function getStudents() {
  students.splice(0, students.length)
  students.push(...(await RemoteService.getStudents()))
  loading.value = false
}

const filteredStudents = computed(() => {
  let result = students

  if (selectedThesisWorkflowState.value) {
    result = result.filter(student => student.thesisWorkflowState === selectedThesisWorkflowState.value)
  }

  if (selectedDefenseWorkflowState.value) {
    result = result.filter(student => student.defenseWorkflowState === selectedDefenseWorkflowState.value)
  }

  return result
})

const fuzzySearch = (value: string, search: string) => {
    if (!search) return true
    let searchRegex = new RegExp(search.split('').join('.*'), 'i')
    return searchRegex.test(value)
}

async function handleStudentUpdated({ id, newState }) {
  await getStudents()
}

getStudents()

</script>
