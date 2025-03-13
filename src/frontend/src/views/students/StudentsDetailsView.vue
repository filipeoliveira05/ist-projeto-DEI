<template>
  <v-container v-if="student">
    <h2>{{ student.name }}</h2>
    <br>
    <p><strong>IST ID:</strong> {{ student.istId }}</p>
    <p><strong>Email:</strong> {{ student.email }}</p>
    <p><strong>Telemóvel:</strong> {{ student.phoneNumber }}</p>

    <br><br>
     
    <!-- THESIS WORKFLOW -->
    <p><strong>Estado da Tese:</strong>
      <v-chip v-if="student.thesisWorkflowState" :color="thesisWorkflowStateColors[student.thesisWorkflowState] || 'gray'">
        {{ thesisWorkflowStateLabels[student.thesisWorkflowState] || 'Desconhecido' }}
      </v-chip>
      <span v-else>Desconhecido</span>
    </p>

    <v-select
      v-model="selectedThesisWorkflowState"
      :items="thesisWorkflowStateOptions"
      label="Alterar Estado da Tese"
    ></v-select>

    <v-btn @click="updateThesisWorkflowState" color="primary">
      Atualizar Estado da Tese
    </v-btn>

    <br><br>
    <br><br>

    <!-- DEFENSE WORKFLOW -->
    <p><strong>Estado da Defesa:</strong>
      <v-chip v-if="student.defenseWorkflowState" :color="defenseWorkflowStateColors[student.defenseWorkflowState] || 'gray'">
        {{ defenseWorkflowStateLabels[student.defenseWorkflowState] || 'Desconhecido' }}
      </v-chip>
      <span v-else>Desconhecido</span>
    </p>

    <v-select
      v-model="selectedDefenseWorkflowState"
      :items="defenseWorkflowStateOptions"
      label="Alterar Estado da Defesa"
    ></v-select>

    <v-btn @click="updateDefenseWorkflowState" color="primary">
      Atualizar Estado da Defesa
    </v-btn>


    <br><br>
    <v-btn @click="goBack" variant="outlined" color="primary">
      ⬅ Voltar para a lista de alunos
    </v-btn>
  </v-container>

  <v-container v-else class="text-center">
    <v-progress-circular indeterminate></v-progress-circular>
    <p>Carregando informações do aluno...</p>
  </v-container>
</template>


<script setup lang="ts">
import { ref, onMounted, defineEmits } from 'vue'
import { useRoute, useRouter } from 'vue-router'
import { thesisWorkflowStateColors, thesisWorkflowStateLabels, thesisWorkflowStateOptions } from '../../constants/thesisWorkflowState'
import { defenseWorkflowStateColors, defenseWorkflowStateLabels, defenseWorkflowStateOptions } from '../../constants/defenseWorkflowState'
import RemoteService from '../../services/RemoteService'
import type StudentDto from '../../models/StudentDto'

const route = useRoute()
const router = useRouter()
const student = ref<StudentDto | null>(null)
const selectedThesisWorkflowState = ref('')
const selectedDefenseWorkflowState = ref('')
const emit = defineEmits(['studentUpdated'])


onMounted(async () => {
  try {
    const studentId = route.params.id as string
    student.value = await RemoteService.getStudentById(studentId)
    selectedThesisWorkflowState.value = student.value?.thesisWorkflowState || 'NONE'
    selectedDefenseWorkflowState.value = student.value?.defenseWorkflowState || 'NONE'
  } catch (error) {
    console.error('Error searching student:', error)
  }
})

async function updateThesisWorkflowState() {
  if (!student.value) return

  try {
    await RemoteService.updateThesisWorkflowState(student.value.id, selectedThesisWorkflowState.value)
    student.value.thesisWorkflowState = selectedThesisWorkflowState.value

    emit('studentUpdated', { id: student.value.id, newState: selectedThesisWorkflowState.value })
  } catch (error) {
    console.error('Error updating thesis workflow status:', error)
  }
}

async function updateDefenseWorkflowState() {
  if (!student.value) return

  try {
    await RemoteService.updateDefenseWorkflowState(student.value.id, selectedDefenseWorkflowState.value)
    student.value.defenseWorkflowState = selectedDefenseWorkflowState.value

    emit('studentUpdated', { id: student.value.id, newState: selectedDefenseWorkflowState.value })
  } catch (error) {
    console.error('Error updating defense workflow status:', error)
  }
}

function goBack() {
  router.push('/students')
}

</script>
