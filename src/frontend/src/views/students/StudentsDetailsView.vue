<template>
    <v-container v-if="student">
      <h2>{{ student.name }}</h2>
      <br>
      <p><strong>IST ID:</strong> {{ student.istId }}</p>
      <p><strong>Email:</strong> {{ student.email }}</p>
      <p><strong>Telemóvel: </strong>{{ student.phoneNumber }}</p>
      <p><strong>Estado da Tese: </strong>
        <v-chip v-if="student.thesisWorkflowState" :color="workflowStatusColors[student.thesisWorkflowState] || 'gray'">
          {{ workflowStatusLabels[student.thesisWorkflowState] || 'Desconhecido' }}
        </v-chip>
        <span v-else>Desconhecido</span>
      </p>
      <br>
      <br>
      <v-btn @click="$router.push('/students')" variant="outlined" color="primary">
        ⬅ Voltar para a lista de alunos
      </v-btn>
    </v-container>

    
  
    <v-container v-else class="text-center">
      <v-progress-circular indeterminate></v-progress-circular>
      <p>Carregando informações do aluno...</p>
    </v-container>
  </template>
  
  <script setup lang="ts">
  import { ref, onMounted } from 'vue'
  import { useRoute } from 'vue-router'
  import RemoteService from '@/services/RemoteService'
  import type StudentDto from '@/models/StudentDto'
  
  const route = useRoute()
  const student = ref<StudentDto | null>(null)
  
  const workflowStatusColors: Record<string, string> = {
    PLANNING: 'blue',
    IN_PROGRESS: 'orange',
    COMPLETED: 'green'
  }
  
  const workflowStatusLabels: Record<string, string> = {
    PLANNING: 'Planejamento',
    IN_PROGRESS: 'Em Desenvolvimento',
    COMPLETED: 'Concluído'
  }
  
  onMounted(async () => {
    try {
      const studentId = route.params.id
      student.value = await RemoteService.getStudentById(studentId)
    } catch (error) {
      console.error('Erro ao buscar aluno:', error)
    }
  })
  </script>
  