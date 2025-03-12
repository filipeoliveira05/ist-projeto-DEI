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
      v-model="selectedWorkflowStatus"
      :items="workflowStatusOptions"
      label="Filtrar por estado do workflow"
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

      <template v-slot:[`item.workflowStatus`]="{ item }">
        <v-chip :color="workflowStatusColors[item.workflowStatus] || 'gray'" text-color="white">
          {{ workflowStatusLabels[item.workflowStatus] || 'Desconhecido' }}
        </v-chip>
      </template>
    </v-data-table>
  </v-container>
</template>

<style scoped>
.student-link {
  text-decoration: none;
  color: inherit;
}
.student-link:hover {
  text-decoration: underline;
}
</style>

  
  
  <script setup lang="ts">
  import { reactive, ref, computed } from 'vue'
  import RemoteService from '@/services/RemoteService'
  import type StudentDto from '@/models/StudentDto'
  
  const search = ref('')
  const loading = ref(true)
  const students: StudentDto[] = reactive([])
  const selectedWorkflowStatus = ref(null)
  
  const headers = [
    { title: 'Nome', key: 'name', value: 'name', sortable: true },
    { title: 'IST ID', key: 'istId', value: 'istId', sortable: true },
    { title: 'Email', key: 'email', value: 'email', sortable: true },
    { title: 'Estado do Workflow', key: 'workflowStatus', value: 'workflowStatus', sortable: true }
  ]
  
  const workflowStatusOptions = [
    { title: 'Planeamento', value: 'PLANNING' },
    { title: 'Em Desenvolvimento', value: 'IN_PROGRESS' },
    { title: 'Concluído', value: 'COMPLETED' }
  ]
  
  const workflowStatusColors: Record<string, string> = {
    PLANNING: 'blue',
    IN_PROGRESS: 'orange',
    COMPLETED: 'green'
  }
  
  const workflowStatusLabels: Record<string, string> = {
    PLANNING: 'Planeamento',
    IN_PROGRESS: 'Em Desenvolvimento',
    COMPLETED: 'Concluído'
  }
  
  async function getStudents() {
    students.splice(0, students.length)
    students.push(...(await RemoteService.getStudents()))
    loading.value = false
  }
  
  const fuzzySearch = (value: string, search: string) => {
    if (!search) return true
    let searchRegex = new RegExp(search.split('').join('.*'), 'i')
    return searchRegex.test(value)
  }
  
  // Computed para filtrar os alunos pelo estado do workflow
  const filteredStudents = computed(() => {
    if (!selectedWorkflowStatus.value) {
      return students
    }
    return students.filter(student => student.workflowStatus === selectedWorkflowStatus.value)
  })
  
  getStudents()
  </script>
  