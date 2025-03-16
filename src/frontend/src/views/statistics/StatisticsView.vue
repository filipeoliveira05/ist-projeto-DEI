<template>
  <v-container>
    <v-row class="mb-6">
      <v-col cols="12">
        <h1 class="text-h3 text-center ml-1 font-weight-bold">Estatísticas do Sistema</h1>
      </v-col>
    </v-row>

    <!-- General Statistics -->
    <v-row class="mb-6" justify="center">
      <v-col cols="12" md="6" lg="4">
        <v-card class="stat-card bg-success text-white">
          <v-card-text class="text-center">
            <v-icon large class="mb-3">mdi-account-multiple</v-icon>
            <h3 class="text-h6">Número de Alunos</h3>
            <p class="text-h4 font-weight-bold">{{ numberOfStudents }}</p>
          </v-card-text>
        </v-card>
      </v-col>
      <v-col cols="12" md="6" lg="4">
        <v-card class="stat-card bg-primary text-white">
          <v-card-text class="text-center">
            <v-icon large class="mb-3">mdi-account-tie</v-icon>
            <h3 class="text-h6">Número de Professores</h3>
            <p class="text-h4 font-weight-bold">{{ numberOfProfessors }}</p>
          </v-card-text>
        </v-card>
      </v-col>
    </v-row>

    <!-- Thesis Progress Section -->
    <v-row class="mb-6" justify="center">
      <v-col cols="12">
        <h2 class="text-h4 mb-4 font-weight-bold">Workflow de Tese</h2>
      </v-col>
      <v-col
        v-for="(count, step) in thesisProgress"
        :key="step"
        cols="12"
        sm="6"
        md="4"
        lg="3"
      >
        <v-card class="stat-card">
          <v-card-text class="text-center">
            <v-icon large class="mb-3">mdi-file-document</v-icon>
            <h3 class="text-h6">{{ step }}</h3>
            <p class="text-h4 font-weight-bold">{{ count }}</p>
          </v-card-text>
        </v-card>
      </v-col>
    </v-row>

    <!-- Defense Progress Section -->
    <v-row class="mb-6" justify="center">
      <v-col cols="12">
        <h2 class="text-h4 mb-4 font-weight-bold">Workflow de Defesa</h2>
      </v-col>
      <v-col
        v-for="(count, step) in defenseProgress"
        :key="step"
        cols="12"
        sm="6"
        md="4"
        lg="3"
      >
        <v-card class="stat-card">
          <v-card-text class="text-center">
            <v-icon large class="mb-3">mdi-shield-check</v-icon>
            <h3 class="text-h6">{{ step }}</h3>
            <p class="text-h4 font-weight-bold">{{ count }}</p>
          </v-card-text>
        </v-card>
      </v-col>
    </v-row>
  </v-container>
</template>

<script setup>
import { ref, onMounted } from "vue";
import RemoteServices from "@/services/RemoteService";

const numberOfStudents = ref(0);
const numberOfProfessors = ref(0);
const thesisProgress = ref({});
const defenseProgress = ref({});

const fetchStatistics = async () => {
  try {
    const data = await RemoteServices.getStatistics();

    numberOfStudents.value = data.numStudents;
    numberOfProfessors.value = data.numTeachers;

    thesisProgress.value = {
      "Proposta de Júri Submetida": data.thesisProposalSubmitted,
      "Aprovado pelo SC": data.thesisApproved,
      "Presidente do Júri Atribuído": data.thesisPresidentAssigned,
      "Documento Assinado": data.thesisSigned,
      "Submetido ao Fenix": data.thesisSubmittedFenix,
    };

    defenseProgress.value = {
      "Defesa Agendada": data.defenseScheduled,
      "Em Revisão": data.defenseReview,
      "Submetido ao Fenix": data.defenseSubmittedFenix,
    };
  } catch (error) {
    console.error("Error fetching statistics:", error);
  }
};

onMounted(fetchStatistics);
</script>

<style scoped>
.stat-card {
  transition: transform 0.2s ease-in-out;
  border-radius: 12px;
  height: 100%;
}

.stat-card:hover {
  transform: translateY(-5px);
  box-shadow: 0 6px 12px rgba(0, 0, 0, 0.15);
}

.stat-card .v-card-text {
  padding: 16px;
}

.stat-card .v-icon {
  color: white;
}

.bg-success {
  background-color: #4CAF50 !important;
}

.bg-primary {
  background-color: #0D47A1 !important;
}

.text-h4, .text-h6 {
  font-weight: bold;
}

.text-h4 {
  font-size: 2.5rem;
}

.text-h6 {
  font-size: 1.25rem;
}

.mb-6 {
  margin-bottom: 32px;
}
</style>
